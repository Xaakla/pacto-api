package com.app.pactoapi.paymentprocessing;

import com.app.pactoapi.enums.PaymentProcessorType;
import com.app.pactoapi.paymentprocessing.cielo.CieloPaymentProcessorService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaymentProcessorFactory {

    private final CieloPaymentProcessorService cieloService;

    public PaymentProcessorFactory(CieloPaymentProcessorService cieloService) {
        this.cieloService = cieloService;
    }

    // Logica para utilizar outros processadores de pagamento caso necessário no futuro.
    // Por exemplo, utilizar a API do Cielo para pagamentos em BRL e o Stripe para pagamentos em outras moedas
    // Qualquer lógica pode ser colocada aqui dependendo do critério utilizado
    // para definir o processador de pagamento usado.
    //este projeto, como estamos apenas integrando com o Cielo, retorna o servico do Cielo sempre
    public IPaymentProcessorService getByCurrency(String currency) {
        if (currency.equals("BRL"))
            return cieloService;

        // Outras moedas
        //return StripeService;
        return cieloService;
    }

    public IPaymentProcessorService getByPaymentProcessorType(PaymentProcessorType paymentProcessorType) {
        final var processorTypeToServiceMap = Map.of(PaymentProcessorType.CIELO, cieloService);
        return processorTypeToServiceMap.get(paymentProcessorType);
    }
}
