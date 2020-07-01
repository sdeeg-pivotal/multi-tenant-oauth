package com.vmware.multitenantresourceserver.configuration;

import com.vmware.multitenantresourceserver.security.JwtIssuerAuthenticationManagerResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoderJwkSupport;

import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer.uris}")
    private String issuerUris;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        JwtIssuerAuthenticationManagerResolver authenticationManagerResolver =
                new JwtIssuerAuthenticationManagerResolver(issuerUris.split("\\s*,\\s*"));

        List<String> issuerList = Arrays.asList(issuerUris.split("\\s*,\\s*"));
        JwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation(issuerList.get(0));

        // @formatter:off
        http
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .oauth2ResourceServer(oauth2ResourceServer ->
                    oauth2ResourceServer.authenticationManagerResolver(authenticationManagerResolver)
            );
        // @formatter:on
    }
}
