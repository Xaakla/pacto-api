package com.app.pactoapi.paymentprocessing.cielo;

import com.app.pactoapi.enums.PaymentProcessorType;
import com.app.pactoapi.exceptions.errors.BadRequestException;
import com.app.pactoapi.paymentprocessing.IPaymentProcessorService;
import com.app.pactoapi.paymentprocessing.cielo.dtos.request.CieloSalesCreditCardRequestDto;
import com.app.pactoapi.paymentprocessing.cielo.dtos.request.CieloSalesCustomerRequestDto;
import com.app.pactoapi.paymentprocessing.cielo.dtos.request.CieloSalesPaymentRequestDto;
import com.app.pactoapi.paymentprocessing.cielo.dtos.request.CieloSalesRequestDto;
import com.app.pactoapi.paymentprocessing.cielo.dtos.response.CieloCardValidationResponseDto;
import com.app.pactoapi.paymentprocessing.cielo.dtos.response.CieloPaymentCancelationResponseDto;
import com.app.pactoapi.paymentprocessing.cielo.dtos.response.CieloSalesResponseDto;
import com.app.pactoapi.paymentprocessing.dtos.CardValidationResponseDto;
import com.app.pactoapi.paymentprocessing.dtos.CreditCardDto;
import com.app.pactoapi.paymentprocessing.dtos.CreditCardPaymentResponseDto;
import com.app.pactoapi.paymentprocessing.dtos.PaymentCancelationResponseDto;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Service
public class CieloPaymentProcessorService implements IPaymentProcessorService {

    private final CieloConfiguration cieloConfiguration;

    private static final int PAYMENT_CANCELLED_CODE = 10;

    public CieloPaymentProcessorService(CieloConfiguration cieloConfiguration) {
        this.cieloConfiguration = cieloConfiguration;
    }

    public CreditCardPaymentResponseDto payWithCreditCard(CreditCardDto creditCardDto, String identifier, Long amount) {
        final var requestBody = new CieloSalesRequestDto(
                identifier,
                new CieloSalesCustomerRequestDto(creditCardDto.getHolder()),
                new CieloSalesPaymentRequestDto(
                        amount,
                        new CieloSalesCreditCardRequestDto(
                                creditCardDto.getCardNumber(),
                                creditCardDto.getHolder(),
                                creditCardDto.getExpirationDate(),
                                creditCardDto.getSecurityCode(),
                                creditCardDto.getBrand()
                        )
                )

        );

        try {
            final var response = new RestTemplate()
                    .postForEntity(cieloConfiguration.getCreatePaymentUrl(), new HttpEntity<>(requestBody, getHeaders()), CieloSalesResponseDto.class);

            return Optional.ofNullable(response.getBody()).map(CieloSalesResponseDto::getPayment)
                    .map(it -> new CreditCardPaymentResponseDto(null, it.getStatus().paymentStatus, it.getPaymentId(), it.getAmount()))
                    .orElseThrow(() -> new RuntimeException("paymentProcessingFailedException"));
        } catch (Exception e) {
            throw new RuntimeException("paymentProcessingFailedException");
        }
    }

    public CardValidationResponseDto validateCard(CreditCardDto creditCardDto) {

        if (!creditCardDto.getCardType().equalsIgnoreCase(("CreditCard"))){
            throw new BadRequestException("cardType is incorrect");
        }

        final var entity = new HttpEntity<>(creditCardDto, getHeaders());

        try {
            final var response = new RestTemplate().postForEntity(
                    cieloConfiguration.getValidationUrl(), entity, CieloCardValidationResponseDto.class);

            return Optional.ofNullable(response.getBody()).map(it -> new CardValidationResponseDto(it.isValid()))
                    .orElseGet(() -> new CardValidationResponseDto(false));
        } catch (Exception e) {
            return new CardValidationResponseDto(false);
        }
    }

    public PaymentCancelationResponseDto cancelPayment(String transactionId, Long amount) {
        HttpEntity<CreditCardDto> entity = new HttpEntity<>(null, getHeaders());
        try {
            final var response = new RestTemplate()
                    .exchange(cieloConfiguration.getCancelPaymentUrl(transactionId, amount), HttpMethod.PUT, entity, CieloPaymentCancelationResponseDto.class);

            return Optional.ofNullable(response.getBody())
                    .map(it -> new PaymentCancelationResponseDto(it.getStatus() == PAYMENT_CANCELLED_CODE))
                    .orElseGet(() -> new PaymentCancelationResponseDto(false));
        } catch (Exception e) {
            return new PaymentCancelationResponseDto(false);
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
