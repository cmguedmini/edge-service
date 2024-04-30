package com.poc.facade.pocfacade.utils;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.jsoup.Jsoup;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Safelist;

public final class SanitizerUtils {
	public static void sanitize(Supplier<String> getFct, Consumer<String> setFct) {
		final String stringToClean = getFct.get();
		if (stringToClean != null) {
			setFct.accept(clean(stringToClean));
		}
	}
	
	public static <T> T sanitize(Supplier<String> getFct, Consumer<String> setFct, T returnValue) {
		sanitize(getFct, setFct);
		return returnValue;
	}
	
	public String sanitize(String string) {
		return string == null ? null : clean(string) ;
	}

	private static String clean(final String inputString) {
		final var cleaner = new Cleaner(Safelist.none());
		return cleaner.clean(Jsoup.parseBodyFragment(inputString)).text();
	}
}
