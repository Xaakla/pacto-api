package com.app.pactoapi.paymentprocessing;

import com.app.pactoapi.enums.PaymentProcessorType;
import com.app.pactoapi.paymentprocessing.models.CardValidationResponseModel;
import com.app.pactoapi.paymentprocessing.models.CreditCardModel;
import com.app.pactoapi.paymentprocessing.models.CreditCardPaymentResponseModel;
import com.app.pactoapi.paymentprocessing.models.PaymentCancelationResponseModel;

/**
 * Interface para padronizar a forma de processamento de pagamentos com cartão de crédito.
 * <p>
 * Essa interface define os métodos para validar cartões, efetuar pagamentos,
 * cancelar pagamentos, e identificar o tipo de processador de pagamento utilizado.
 */
public interface IPaymentProcessorService {

    /**
     * Retorna o tipo de processador de pagamento.
     *
     * @return o tipo de processador de pagamento {@link PaymentProcessorType}.
     */
    PaymentProcessorType getType();

    /**
     * Valida um cartão de crédito.
     *
     * @param creditCardModel o modelo de cartão de crédito a ser validado.
     * @return a resposta da validação do cartão {@link CardValidationResponseModel}.
     */
    CardValidationResponseModel validateCard(CreditCardModel creditCardModel);

    /**
     * Realiza o pagamento com cartão de crédito.
     *
     * @param creditCardModel o modelo de cartão de crédito a ser utilizado no pagamento.
     * @param identifier      um identificador único da transação.
     * @param amount          o valor a ser pago, em centavos.
     * @return a resposta do pagamento com cartão de crédito {@link CreditCardPaymentResponseModel}.
     */
    CreditCardPaymentResponseModel payWithCreditCard(CreditCardModel creditCardModel, String identifier, Long amount);

    /**
     * Cancela um pagamento realizado com cartão de crédito.
     *
     * @param transactionId o identificador único da transação a ser cancelada.
     * @param amount        o valor a ser cancelado, em centavos.
     * @return a resposta do cancelamento de pagamento {@link PaymentCancelationResponseModel}.
     */
    PaymentCancelationResponseModel cancelPayment(String transactionId, Long amount);
}

