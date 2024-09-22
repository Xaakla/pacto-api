package com.app.pactoapi.paymentprocessing;

import com.app.pactoapi.enums.PaymentProcessorType;
import com.app.pactoapi.paymentprocessing.dtos.CardValidationResponseDto;
import com.app.pactoapi.paymentprocessing.dtos.CreditCardDto;
import com.app.pactoapi.paymentprocessing.dtos.CreditCardPaymentResponseDto;
import com.app.pactoapi.paymentprocessing.dtos.PaymentCancelationResponseDto;

public interface IPaymentProcessorService {

    PaymentProcessorType getType();

    CardValidationResponseDto validateCard(CreditCardDto creditCardDto);

    CreditCardPaymentResponseDto payWithCreditCard(CreditCardDto creditCarDto, String identifier, Long amount);

    PaymentCancelationResponseDto cancelPayment(String transactionId, Long amount);
}
