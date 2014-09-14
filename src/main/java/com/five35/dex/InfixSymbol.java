package com.five35.dex;

abstract class InfixSymbol extends Symbol {
	InfixSymbol(final String name, final String characters, final int leftBindingPower) {
		super(name, characters, leftBindingPower);
	}

	abstract Result binary(final Result left, final Result right);

	@Override
	Expression denoteLeft(final Parser parser, final Expression left) throws ParserException {
		final Expression right = parser.getExpression();

		return new BinaryExpression(this, left, right);
	}

	@Override
	Expression denoteNull(final Parser parser) throws ParserException {
		throw new SyntaxException(parser.getToken());
	}
}
