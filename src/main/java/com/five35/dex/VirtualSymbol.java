package com.five35.dex;

class VirtualSymbol extends Symbol {
	VirtualSymbol(final String name, final String characters, final int bindingPower) {
		super(name, characters, bindingPower);
	}

	@Override
	Expression getLeftDenotation(final Parser parser, final Expression left) throws ParserException {
		throw new SyntaxException(parser.getCurrentToken());
	}

	@Override
	Expression getNullDenotation(final Parser parser) throws ParserException {
		throw new SyntaxException(parser.getCurrentToken());
	}
}
