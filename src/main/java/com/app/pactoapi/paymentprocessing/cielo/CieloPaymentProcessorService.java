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

@Service
public class CieloPaymentProcessorService implements IPaymentProcessorService {

    private final CieloConfiguration cieloConfiguration;

    private static final int PAYMENT_CANCELLED_CODE = 10;

    public CieloPaymentProcessorService(CieloConfiguration cieloConfiguration) {
        this.cieloConfiguration = cieloConfiguration;
    }

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

            return Optional.ofNullable(response.getBody()).map(CieloSalesResponseDto::getPayment)
                    .map(it -> new CreditCardPaymentResponseModel(null, it.getStatus().paymentStatus, it.getPaymentId(), it.getAmount()))
                    .orElseThrow(() -> new RuntimeException("paymentProcessingFailedException"));
        } catch (Exception e) {
            throw new RuntimeException("paymentProcessingFailedException");
        }
    }

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
            final var response = new RestTemplate().postForEntity(
                    cieloConfiguration.getValidationUrl(), entity, CieloCardValidationResponseDto.class);

            return Optional.ofNullable(response.getBody()).map(it -> new CardValidationResponseModel(it.isValid()))
                    .orElseGet(() -> new CardValidationResponseModel(false));
        } catch (Exception e) {
            return new CardValidationResponseModel(false);
        }
    }

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

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("MerchantId", cieloConfiguration.getMerchantId());
        headers.set("MerchantKey", cieloConfiguration.getMerchantKey());
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

    public PaymentProcessorType getType() {
        return PaymentProcessorType.CIELO;
    }
}
