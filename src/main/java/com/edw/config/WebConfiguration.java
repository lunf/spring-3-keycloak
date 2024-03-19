package com.edw.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebConfiguration {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // Do any additional configuration here
        return builder.build();
    }

    @Bean
    public LdapContextSource contextSource() {

        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl("ldap://localhost:389");
        contextSource.setUserDn("admin_username");
        contextSource.setPassword("password");
        contextSource.setBase("DC=company,DC=com");
        contextSource.setPooled(true);


        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(contextSource());
    }
}
