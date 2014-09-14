package com.five35.dex;

import com.google.common.base.Optional;
import java.util.Map;

class BinaryExpression implements Expression {
	private final InfixSymbol infix;
	private final Expression left;
	private final Expression right;

	BinaryExpression(final InfixSymbol infix, final Expression left, final Expression right) {
		this.infix = infix;
		this.left = left;
		this.right = right;
	}

	@Override
	public Result execute(final Optional<Map<String, Expression>> variables) {
		return this.infix.binary(this.left.execute(variables), this.right.execute(variables));
	}
}
