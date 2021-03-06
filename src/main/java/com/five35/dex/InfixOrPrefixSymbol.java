package com.five35.dex;

import com.google.common.base.Preconditions;

abstract class InfixOrPrefixSymbol extends Symbol implements BinarySymbol, UnarySymbol {
	private final BindingPower rightBindingPower;

	InfixOrPrefixSymbol(final String name, final String characters, final BindingPower leftBindingPower, final BindingPower rightBindingPower) {
		super(Preconditions.checkNotNull(name), Preconditions.checkNotNull(characters), leftBindingPower);

		this.rightBindingPower = rightBindingPower;
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
		Preconditions.checkNotNull(parser);

		final Expression right = parser.getExpression(this.rightBindingPower);

		return new UnaryExpression(this, right);
	}
}
