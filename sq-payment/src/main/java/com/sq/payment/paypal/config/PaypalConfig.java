package com.sq.payment.paypal.config;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Paypal configuration class
 * credit: https://blog.csdn.net/change_on/article/details/73881791
 */
@Configuration
public class PaypalConfig {
    @Value("${paypal.client.app}")
    private String clientId;
    @Value("${paypal.client.secret}")
    private String clientSecret;
    @Value("${paypal.client.mode}")
    private String mode;

    @Bean
    public Map<String, String> paypalSdkConfig() {
        Map<String, String> map = new HashMap<>();
        map.put("mode", mode);
        return map;
    }


    @Bean
    public OAuthTokenCredential getAuthTokenCredential() {
        return new OAuthTokenCredential(clientId, clientSecret, paypalSdkConfig());
    }

    @Bean
    public APIContext getApiContext() throws PayPalRESTException {
        APIContext apiContext = new APIContext(getAuthTokenCredential().getAccessToken());
        apiContext.setConfigurationMap(paypalSdkConfig());

        return apiContext;
    }
}
