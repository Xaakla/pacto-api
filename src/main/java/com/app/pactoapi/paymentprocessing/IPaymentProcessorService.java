package com.app.pactoapi.paymentprocessing;

import com.app.pactoapi.enums.PaymentProcessorType;
import com.app.pactoapi.paymentprocessing.models.CardValidationResponseModel;
import com.app.pactoapi.paymentprocessing.models.CreditCardModel;
import com.app.pactoapi.paymentprocessing.models.CreditCardPaymentResponseModel;
import com.app.pactoapi.paymentprocessing.models.PaymentCancelationResponseModel;

public interface IPaymentProcessorService {

    PaymentProcessorType getType();

    CardValidationResponseModel validateCard(CreditCardModel creditCardModel);

    CreditCardPaymentResponseModel payWithCreditCard(CreditCardModel creditCarDto, String identifier, Long amount);

    PaymentCancelationResponseModel cancelPayment(String transactionId, Long amount);
}
