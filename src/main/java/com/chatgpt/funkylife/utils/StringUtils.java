package com.chatgpt.funkylife.utils;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.Normalizer;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

public class StringUtils {
	
	private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
	private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
	private static final Pattern EDGESDHASHES = Pattern.compile("(^-|-$)");
	
	public static String trim(String str) {
		return str.trim();
	}
	
	public static String removeCharactersAndLowerCase(String str, String regex, String replaceWith, boolean toLowercase) {
		String sanitizedString = str.replaceAll(regex, replaceWith);
		
		if(toLowercase)
			sanitizedString = sanitizedString.toLowerCase();
		
		return sanitizedString;
	}
	
	
	public static String generateRandomString(int lengthOfString, int min, int max) {
		int leftLimit = min; 
	    int rightLimit = max;
	    int targetStringLength = lengthOfString;
	    Random random = new Random();

	    String generatedString = random.ints(leftLimit, rightLimit + 1)
								       .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
								       .limit(targetStringLength)
								       .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
								       .toString();
	    
	    return generatedString;
	}
	
	public static String getSubDomain(String domain) {
		return domain.split("\\.")[0];
	}
	
	public static String getSlug(String input) {
		
		String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
		String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
		String slug = NONLATIN.matcher(normalized).replaceAll("");
		slug = EDGESDHASHES.matcher(slug).replaceAll("");
		slug = slug.toLowerCase(Locale.ENGLISH);
		String randomString = generateRandomString(8, 48, 122);
		
		return slug + "-" + randomString.toLowerCase();
	}
	
	public static boolean isStringNotNullEmpty(String input) {
		return ( input != null && !input.isEmpty() );
	}
	
	public static String getDomain(String host) {
		String[] arr = host.split("//");
		if(arr.length > 1)
			return arr[1];
		return arr[0];
	}

	public static boolean isValidURL(String url) throws MalformedURLException, URISyntaxException {
		try {
			new URL(url).toURI();
			return true;
		} catch (MalformedURLException e) {
			return false;
		} catch (URISyntaxException e) {
			return false;
		}
	}
}
