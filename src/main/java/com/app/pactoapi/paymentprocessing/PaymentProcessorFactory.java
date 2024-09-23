package com.app.pactoapi.paymentprocessing;

import com.app.pactoapi.enums.PaymentProcessorType;
import com.app.pactoapi.paymentprocessing.cielo.CieloPaymentProcessorService;
import org.springframework.stereotype.Service;

import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * Classe para fornecer o processador de pagamento adequado com base em critérios definidos,
 * como a moeda ou o tipo de processador de pagamento.
 *
 * Atualmente, esta classe integra apenas com o serviço de pagamento da Cielo, mas foi projetada
 * para facilitar a integração com outros processadores no futuro, como Stripe ou PayPal,
 * dependendo da moeda ou de outros fatores.
 */
@Service
public class PaymentProcessorFactory {

    private final CieloPaymentProcessorService cieloService;

    /**
     * Construtor para injetar o serviço de pagamento da Cielo.
     *
     * @param cieloService o serviço de pagamento da Cielo.
     */
    public PaymentProcessorFactory(CieloPaymentProcessorService cieloService) {
        this.cieloService = cieloService;
    }

    /**
     * Retorna o processador de pagamento com base na moeda informada.
     *
     * Se a moeda for "BRL", o serviço da Cielo é retornado. Esta lógica pode ser estendida
     * para utilizar outros processadores, como o Stripe, para moedas diferentes no futuro.
     *
     * @param currency a moeda na qual o pagamento será processado (ex.: "BRL").
     * @return o serviço de processador de pagamento {@link IPaymentProcessorService}.
     */
    public IPaymentProcessorService getByCurrency(String currency) {
        if (currency.equals("BRL"))
            return cieloService;

        // Lógica para processadores de outras moedas pode ser implementada aqui.
        // return StripeService; // Exemplo para integração futura
        return cieloService; // Retorna Cielo como padrão no projeto atual
    }

    /**
     * Retorna o processador de pagamento com base no tipo de processador definido.
     *
     * Atualmente, o único processador configurado é o da Cielo, mas esta lógica foi
     * projetada para ser facilmente escalável, permitindo a adição de outros processadores
     * no futuro, mapeando o tipo de processador para o serviço correspondente.
     *
     * @param paymentProcessorType o tipo de processador de pagamento {@link PaymentProcessorType}.
     * @return o serviço de processador de pagamento correspondente ao tipo informado,
     *         ou {@code null} se o tipo não for encontrado.
     */
    public IPaymentProcessorService getByPaymentProcessorType(PaymentProcessorType paymentProcessorType) {
        final var processorTypeToServiceMap = Map.of(PaymentProcessorType.CIELO, cieloService);
        return processorTypeToServiceMap.get(paymentProcessorType);
    }
}

