package com.poc.facade.pocfacade.web;

import reactor.core.publisher.Mono;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poc.facade.pocfacade.user.User;
import com.poc.facade.pocfacade.utils.RequestParamsUtils;

import jakarta.validation.Valid;

@RestController
public class UserController {

	@GetMapping("user")
	public Mono<User> getUser(@RequestParam(required = false) final Map<String, String> requestParams, @AuthenticationPrincipal OidcUser oidcUser) {
		var user = new User(
				oidcUser.getPreferredUsername(),
				oidcUser.getGivenName(),
				oidcUser.getFamilyName(),
				oidcUser.getClaimAsStringList("roles")
		);
		
		return RequestParamsUtils.checkInputsParams(requestParams).then(Mono.just(user));
		
		
		//return Mono.just(user);
	}
	
//	@GetMapping("/user")
//	public Mono<User> getUser() {
//		return ReactiveSecurityContextHolder.getContext().map(SecurityContext::getAuthentication)
//				.map(authentication -> (OidcUser) authentication.getPrincipal())
//				.map(oidcUser -> new User(oidcUser.getPreferredUsername(), oidcUser.getGivenName(),
//						oidcUser.getFamilyName(), List.of("employee", "customer")));
//	}

//	public Mono<OrderDto> createCdsOrder(@RequestBody @Valid final Order order, @RequestParam(required = false) final Map<String, String> requestParams) {
//		return RequestParamsUtils.checkNoInputParams(requestParams)
//				.then(Mono.defer(() -> Mono.just(order).map(o -> {
//					SanitizerUtils.sanitize(o::getIstructions, o::setInstructions);
//					return o;
//				}).flatMap(cdsOrderWebClient::createOrder)));
//	}
}
