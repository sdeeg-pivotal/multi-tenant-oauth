package com.vmware.multitenantclientcredentials.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${RESOURCE_URL}")
    private String resourceServerUrl;

    @Bean(name = "webClient")
    public WebClient webClient(ClientRegistrationRepository clientRegistrationRepository,
                               OAuth2AuthorizedClientRepository authorizedClientRepository) {
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(
                        clientRegistrationRepository,
                        authorizedClientRepository);
        oauth2.setDefaultClientRegistrationId("sso");
        System.out.println("Creating webClient with URL "+resourceServerUrl);
        return WebClient.builder()
                .baseUrl(resourceServerUrl)
                .apply(oauth2.oauth2Configuration())
                .build();
    }

    @Value("${FAILOVER_RESOURCE_URL}")
    private String failoverResourceServerUrl;

    @Bean(name = "webClientFailover")
    public WebClient webClientFailover(ClientRegistrationRepository clientRegistrationRepository,
                               OAuth2AuthorizedClientRepository authorizedClientRepository) {
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(
                        clientRegistrationRepository,
                        authorizedClientRepository);
        oauth2.setDefaultClientRegistrationId("sso");
        return WebClient.builder()
                .baseUrl(failoverResourceServerUrl)
                .apply(oauth2.oauth2Configuration())
                .build();
    }
}
