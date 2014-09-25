package com.five35.dex;

import com.google.common.base.Optional;
import com.google.common.collect.PeekingIterator;
import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.junit.Test;

@SuppressWarnings({ "javadoc", "static-method", "unchecked", "unused" })
public class ParserTest {
	// TODO: make tests more hermetic

	@Test
	public void advance_advances() throws Exception {
		final Parser parser = Deencapsulation.newInstance(Parser.class, "1");
		final PeekingIterator<?> tokens = Deencapsulation.getField(parser, PeekingIterator.class);

		new Expectations(tokens) {
			{
				tokens.next();
				this.result = new Token(1, "1");
			}
		};

		parser.advance(Optional.<Symbol>absent());
	}

	@Test
	public void advance_checksForNullArguments(@Mocked final Optional<? extends Symbol> expected) throws Exception {
		final Parser parser = Deencapsulation.newInstance(Parser.class, "1");

		new NullCheckExpectations(expected);

		new NonStrictExpectations() {
			{
				expected.isPresent();
				this.result = false;
			}
		};

		parser.advance(expected);
	}

	@Test
	public void advance_succeedsOnExpectedSymbol() throws Exception {
		final Parser parser = Deencapsulation.newInstance(Parser.class, "1 1");

		parser.advance(Optional.of(Symbol.LITERAL_SCALAR));
	}

	@Test(expected = MissingSymbolException.class)
	public void advance_throwsOnEndOfStream() throws Exception {
		final Parser parser = Deencapsulation.newInstance(Parser.class, "");

		// Artificially advance to the "(end)" token.
		Deencapsulation.getField(parser, PeekingIterator.class).next();

		parser.advance(Optional.<Symbol>absent());
	}

	@Test(expected = MissingSymbolException.class)
	public void advance_throwsOnUnexpectedSymbol() throws Exception {
		final Parser parser = Deencapsulation.newInstance(Parser.class, "1 1");

		parser.advance(Optional.of(new InfixSymbolTest.DummySymbol()));
	}

	@Test
	public void ctor_checksForNullArguments() {
		new NullCheckExpectations("1");

		Deencapsulation.newInstance(Parser.class, "1");
	}

	// TODO: test getExpression

	@Test
	public void parse_checksForNullArguments() throws Exception {
		new NullCheckExpectations("<");

		new NonStrictExpectations(Parser.class) {
			{
				final Parser parser = Deencapsulation.newInstance(Parser.class, this.anyString);

				parser.getExpression(this.anyInt);
				parser.advance((Optional<? extends Symbol>) this.any);
			}
		};

		Parser.parse("<");
	}

	@Test(expected = MissingSymbolException.class)
	public void previewNextToken_throwsOnEndOfStream() throws Exception {
		final Parser parser = Deencapsulation.newInstance(Parser.class, "");

		// Artificially advance to the "(end)" token.
		Deencapsulation.getField(parser, PeekingIterator.class).next();

		parser.previewNextToken();
	}
}
