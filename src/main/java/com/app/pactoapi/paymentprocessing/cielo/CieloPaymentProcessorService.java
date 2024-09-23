package com.app.pactoapi.paymentprocessing.cielo;

import com.app.pactoapi.enums.PaymentProcessorType;
import com.app.pactoapi.paymentprocessing.IPaymentProcessorService;
import com.app.pactoapi.paymentprocessing.cielo.dtos.CieloCardType;
import com.app.pactoapi.paymentprocessing.cielo.dtos.request.*;
import com.app.pactoapi.paymentprocessing.cielo.dtos.response.CieloCardValidationResponseDto;
import com.app.pactoapi.paymentprocessing.cielo.dtos.response.CieloPaymentCancelationResponseDto;
import com.app.pactoapi.paymentprocessing.cielo.dtos.response.CieloSalesResponseDto;
import com.app.pactoapi.paymentprocessing.models.CardValidationResponseModel;
import com.app.pactoapi.paymentprocessing.models.CreditCardModel;
import com.app.pactoapi.paymentprocessing.models.CreditCardPaymentResponseModel;
import com.app.pactoapi.paymentprocessing.models.PaymentCancelationResponseModel;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Optional;

/**
 * Implementação do serviço de processamento de pagamentos com a API da Cielo.
 * Essa classe oferece métodos para validar cartões de crédito, realizar pagamentos e cancelar transações
 * através da integração com a Cielo.
 */
@Service
public class CieloPaymentProcessorService implements IPaymentProcessorService {

    private final CieloConfiguration cieloConfiguration;
    private static final int PAYMENT_CANCELLED_CODE = 10;

    /**
     * Construtor da classe para injeção de dependências.
     *
     * @param cieloConfiguration objeto de configuração da Cielo que contém as URLs, MerchantId e MerchantKey.
     */
    public CieloPaymentProcessorService(CieloConfiguration cieloConfiguration) {
        this.cieloConfiguration = cieloConfiguration;
    }

    /**
     * Realiza o pagamento com cartão de crédito usando a API da Cielo.
     *
     * @param creditCardModel modelo contendo as informações do cartão de crédito.
     * @param identifier identificador único para a transação.
     * @param amount valor do pagamento em centavos.
     * @return o resultado do pagamento com cartão de crédito {@link CreditCardPaymentResponseModel}.
     * @throws RuntimeException se houver falha no processamento do pagamento.
     */
    public CreditCardPaymentResponseModel payWithCreditCard(CreditCardModel creditCardModel, String identifier, Long amount) {
        final var requestBody = new CieloSalesRequestDto(
                identifier,
                new CieloSalesCustomerRequestDto(creditCardModel.getHolder()),
                new CieloSalesPaymentRequestDto(
                        amount,
                        new CieloSalesCreditCardRequestDto(
                                creditCardModel.getCardNumber(),
                                creditCardModel.getHolder(),
                                creditCardModel.getExpirationDate(),
                                creditCardModel.getSecurityCode(),
                                creditCardModel.getBrand()
                        )
                )
        );

        try {
            final var response = new RestTemplate()
                    .postForEntity(cieloConfiguration.getCreatePaymentUrl(), new HttpEntity<>(requestBody, getHeaders()), CieloSalesResponseDto.class);

            return Optional.ofNullable(response.getBody())
                    .map(CieloSalesResponseDto::getPayment)
                    .map(it -> new CreditCardPaymentResponseModel(null, it.getStatus().paymentStatus, it.getPaymentId(), it.getAmount()))
                    .orElseThrow(() -> new RuntimeException("paymentProcessingFailedException"));
        } catch (Exception e) {
            throw new RuntimeException("paymentProcessingFailedException");
        }
    }

    /**
     * Valida as informações do cartão de crédito usando a API da Cielo.
     *
     * @param creditCardModel modelo contendo as informações do cartão de crédito.
     * @return o resultado da validação do cartão {@link CardValidationResponseModel}.
     */
    public CardValidationResponseModel validateCard(CreditCardModel creditCardModel) {
        final var entity = new HttpEntity<>(new CieloCardValidationRequestDto(
                creditCardModel.getCardNumber(),
                creditCardModel.getHolder(),
                creditCardModel.getExpirationDate(),
                creditCardModel.getSecurityCode(),
                creditCardModel.getBrand(),
                CieloCardType.fromModel(creditCardModel.getCardType())
        ), getHeaders());

        try {
            final var response = new RestTemplate()
                    .postForEntity(cieloConfiguration.getValidationUrl(), entity, CieloCardValidationResponseDto.class);

            return Optional.ofNullable(response.getBody())
                    .map(it -> new CardValidationResponseModel(it.isValid()))
                    .orElseGet(() -> new CardValidationResponseModel(false));
        } catch (Exception e) {
            return new CardValidationResponseModel(false);
        }
    }

    /**
     * Cancela um pagamento previamente realizado com cartão de crédito.
     *
     * @param transactionId identificador único da transação a ser cancelada.
     * @param amount valor a ser cancelado em centavos.
     * @return o resultado do cancelamento do pagamento {@link PaymentCancelationResponseModel}.
     */
    public PaymentCancelationResponseModel cancelPayment(String transactionId, Long amount) {
        HttpEntity<CreditCardModel> entity = new HttpEntity<>(null, getHeaders());
        try {
            final var response = new RestTemplate()
                    .exchange(cieloConfiguration.getCancelPaymentUrl(transactionId, amount), HttpMethod.PUT, entity, CieloPaymentCancelationResponseDto.class);

            return Optional.ofNullable(response.getBody())
                    .map(it -> new PaymentCancelationResponseModel(it.getStatus() == PAYMENT_CANCELLED_CODE))
                    .orElseGet(() -> new PaymentCancelationResponseModel(false));
        } catch (Exception e) {
            return new PaymentCancelationResponseModel(false);
        }
    }

    /**
     * Retorna os cabeçalhos HTTP necessários para as requisições à API da Cielo.
     *
     * @return {@link HttpHeaders} contendo as informações de autenticação e tipo de conteúdo.
     */
    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("MerchantId", cieloConfiguration.getMerchantId());
        headers.set("MerchantKey", cieloConfiguration.getMerchantKey());
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

    /**
     * Retorna o tipo de processador de pagamento utilizado.
     *
     * @return o tipo de processador de pagamento {@link PaymentProcessorType}.
     */
    public PaymentProcessorType getType() {
        return PaymentProcessorType.CIELO;
    }
}
