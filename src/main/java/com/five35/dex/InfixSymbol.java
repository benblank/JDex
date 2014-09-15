package com.five35.dex;

abstract class InfixSymbol extends Symbol {
	InfixSymbol(final String name, final String characters, final int bindingPower) {
		super(name, characters, bindingPower);
	}

	abstract Result binary(final Result left, final Result right);

	@Override
	Expression getLeftDenotation(final Parser parser, final Expression left) throws ParserException {
		final Expression right = parser.getExpression(this.getBindingPower());

		return new BinaryExpression(this, left, right);
	}

	@Override
	Expression getNullDenotation(final Parser parser) throws ParserException {
		throw new SyntaxException(parser.getCurrentToken());
	}
}
