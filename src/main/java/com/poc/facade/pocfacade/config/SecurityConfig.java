package com.poc.facade.pocfacade.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.server.WebSessionServerOAuth2AuthorizedClientRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.XorServerCsrfTokenRequestAttributeHandler;

@EnableWebFluxSecurity
public class SecurityConfig {
	@Bean
	SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		return http.authorizeExchange(exchange -> exchange.anyExchange().authenticated())
				.oauth2Client(Customizer.withDefaults())
				.csrf(csrf -> csrf
						.csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse())
						.csrfTokenRequestHandler(new XorServerCsrfTokenRequestAttributeHandler()::handle))
				.build();
	}
	
	/**
	 * Defines a repository to store Access Tokens in the web session
	 * @return
	 */
	@Bean
	 ServerOAuth2AuthorizedClientRepository authorizedClientRepository() {
	 return new WebSessionServerOAuth2AuthorizedClientRepository();
	 }
}
