package com.poc.facade.pocfacade.utils;

import java.util.Map;

import com.poc.facade.pocfacade.exception.BadRequestException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RequestParamsUtils {
	private RequestParamsUtils() {};

	public static Mono<Void> checkInputsParams(final Map<String, String> requestParams, String... allowedValues) {
		final var moreRequestParamsThanExpected = Mono.just(requestParams)
				.filter(map -> map.size() > allowedValues.length).flatMap(v -> Mono.error(new BadRequestException()))
				.then();

		final var allowedFlux = Flux.just(allowedValues).collectMap(s -> s).cache();
		final var checkUnknownParam = Flux.fromIterable(requestParams.keySet())
				.flatMap(s -> allowedFlux.filter(map -> !map.containsKey(s)))
				.flatMap(v -> Mono.error(new BadRequestException())).then();
		return moreRequestParamsThanExpected.then(checkUnknownParam);

	}

	public static Mono<Void> checkNoInputParams(final Map<String, String> requestParams) {
		return Mono.just(requestParams).filter(map -> !map.isEmpty())
				.flatMap(v -> Mono.error(new BadRequestException()));
	}
}
