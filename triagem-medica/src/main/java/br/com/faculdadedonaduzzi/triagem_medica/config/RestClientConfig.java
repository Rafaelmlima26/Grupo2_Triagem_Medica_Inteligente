package br.com.faculdadedonaduzzi.triagem_medica.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

// configuracao do RestClient pra fazer chamadas HTTP pro servico de ML
@Configuration
public class RestClientConfig {

    @Value("${ml-service.url}")
    private String mlServiceUrl;

    @Bean
    public RestClient mlRestClient() {
        return RestClient.builder()
                .baseUrl(mlServiceUrl)
                .build();
    }
}
