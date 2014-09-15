package com.five35.dex;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings({ "javadoc", "static-method" })
public class TokenizerTest {
	private static final Function<Token, String> STRINGS_FROM_TOKENS = new Function<Token, String>() {
		@Override
		public String apply(final Token token) {
			return token.toString();
		}
	};

	private static void assertTokens(final String source, final String... strings) {
		final List<Token> tokens = Tokenizer.tokenize(source);
		final List<String> actual = Lists.transform(tokens, TokenizerTest.STRINGS_FROM_TOKENS);
		final List<String> expected = new ArrayList<>(Arrays.asList(strings));

		// Add the terminal token implicitly, so it doesn't have to be listed on each test.
		expected.add("(end)");

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void tokenize_splitsBetweenLettersAndNumbers() {
		TokenizerTest.assertTokens("abc123", "abc", "123");
	}

	@Test
	public void tokenize_splitsBetweenLettersAndSymbols() {
		TokenizerTest.assertTokens("abc+", "abc", "+");
	}

	@Test
	public void tokenize_splitsBetweenNumbersAndLetters() {
		TokenizerTest.assertTokens("123abc", "123", "abc");
	}

	@Test
	public void tokenize_splitsBetweenNumbersAndSymbols() {
		TokenizerTest.assertTokens("123+", "123", "+");
	}

	@Test
	public void tokenize_splitsBetweenSymbolsAndLetters() {
		TokenizerTest.assertTokens("+abc", "+", "abc");
	}

	@Test
	public void tokenize_splitsBetweenSymbolsAndNumbers() {
		TokenizerTest.assertTokens("+123", "+", "123");
	}

	@Test
	public void tokenize_splitsBetweenSymbols() {
		TokenizerTest.assertTokens("++", "+", "+");
	}

	@Test
	public void tokenize_underscoresAreLetters() {
		TokenizerTest.assertTokens("abc_123", "abc_", "123");
	}

	@Test
	public void tokenize_ignoresLeadingWhitespace() {
		TokenizerTest.assertTokens(" abc", "abc");
	}

	@Test
	public void tokenize_ignoresTrailingWhitespace() {
		TokenizerTest.assertTokens("abc ", "abc");
	}

	@Test
	public void tokenize_ignoresWhitespaceBetweenTokens() {
		TokenizerTest.assertTokens("abc 123", "abc", "123");
	}

	@Test
	public void tokenize_emptyStringProducesNoTokens() {
		TokenizerTest.assertTokens("");
	}

	@Test
	public void tokenize_whitespaceStringProducesNoTokens() {
		TokenizerTest.assertTokens(" ");
	}

	@Test
	public void tokenize_addsCorrectIndexes() {
		final List<Token> tokens = Tokenizer.tokenize("abc123");

		Assert.assertEquals(1, tokens.get(0).getIndex());
		Assert.assertEquals(4, tokens.get(1).getIndex());
	}

	@Test
	public void tokenize_indexesCountWhitespace() {
		final List<Token> tokens = Tokenizer.tokenize("abc 123");

		Assert.assertEquals(1, tokens.get(0).getIndex());
		Assert.assertEquals(5, tokens.get(1).getIndex());
	}
}
