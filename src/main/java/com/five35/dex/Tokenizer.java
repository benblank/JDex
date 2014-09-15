package com.five35.dex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;

final class Tokenizer {
	/**
	 * A regular expression describing what is recognized as a token.
	 * 
	 * A token may be:
	 * 
	 * <ul>
	 * <li>a sequence of one or more digits
	 * <li>a sequence of one or more letters or underscores
	 * <li>a single non-digit, non-letter, non-underscore, non-whitespace
	 * character
	 * </ul>
	 * 
	 * Whitespace in Dex is optional and ignored except where necessary to
	 * separate two tokens (e.g. between a word operator and a variable, such as
	 * `count d 6`. All of the following are valid and tokenize identically:
	 * 
	 * <ul>
	 * <li>4 d 6 drop - 1
	 * <li>4d6 drop -1
	 * <li>4d6drop-1
	 * </ul>
	 * 
	 * Unicode digits and letters are recognized.
	 */
	private static final Pattern TOKEN_PATTERN = Pattern.compile("\\p{Digit}+|[\\p{Alpha}_]+|[^\\p{Alnum}_\\s]", Pattern.UNICODE_CHARACTER_CLASS);

	private Tokenizer() {
		throw new UnsupportedOperationException("Cannot instantiate a utility class");
	}

	@Nonnull
	static List<Token> tokenize(final String source) {
		final Matcher matcher = Tokenizer.TOKEN_PATTERN.matcher(source);
		final List<Token> tokens = new ArrayList<>();

		while (matcher.find()) {
			// add 1 so that the first character isn't "character 0"
			tokens.add(new Token(matcher.start() + 1, matcher.group()));
		}

		tokens.add(new Token(source.length(), "(end)"));

		return tokens;
	}
}
