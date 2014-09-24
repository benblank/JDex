package com.five35.dex;

import com.google.common.base.Preconditions;

abstract class InfixSymbol extends Symbol implements BinarySymbol {
	InfixSymbol(final String name, final String characters, final int bindingPower) {
		super(Preconditions.checkNotNull(name), Preconditions.checkNotNull(characters), bindingPower);
	}

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
