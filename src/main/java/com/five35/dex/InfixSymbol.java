package com.five35.dex;

import com.google.common.base.Preconditions;
import javax.annotation.Nonnull;

abstract class InfixSymbol extends Symbol {
	InfixSymbol(final String name, final String characters, final int bindingPower) {
		super(Preconditions.checkNotNull(name), Preconditions.checkNotNull(characters), bindingPower);
	}

	@Nonnull
	abstract Result binary(final Result left, final Result right);

	@Override
	Expression getLeftDenotation(final Parser parser, final Expression left) throws ParserException {
		Preconditions.checkNotNull(parser);
		Preconditions.checkNotNull(left);

		final Expression right = parser.getExpression(this.getBindingPower());

		return new BinaryExpression(this, left, right);
	}

	@Override
	Expression getNullDenotation(final Parser parser) throws ParserException {
		throw new SyntaxException(parser.getCurrentToken());
	}
}
