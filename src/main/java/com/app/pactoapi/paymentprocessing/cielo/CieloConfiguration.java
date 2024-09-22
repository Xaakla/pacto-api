package com.app.pactoapi.paymentprocessing.cielo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CieloConfiguration {

    @Value("${app.cielo.merchant.id}")
    private String merchantId;

    @Value("${app.cielo.merchant.key}")
    private String merchantKey;

    @Value("${app.cielo.api.url}")
    private String apiUrl;

    public String getMerchantId() {
        return merchantId;
    }

    public String getMerchantKey() {
        return merchantKey;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public String getCreatePaymentUrl() {
        return getApiUrl() + "/1/sales";
    }

    public String getCancelPaymentUrl(String transactionId, Long amount) {
        return getApiUrl() + "/1/sales/" + transactionId + "/void?amount=" + amount;
    }

    public String getValidationUrl() {
        return getApiUrl() + "/1/zeroauth";
    }
}
