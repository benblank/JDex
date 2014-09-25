package com.five35.dex;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import java.util.Map;

class UnaryExpression implements Expression {
	private final UnarySymbol symbol;
	private final Expression operand;

	UnaryExpression(final UnarySymbol symbol, final Expression operand) {
		this.symbol = Preconditions.checkNotNull(symbol);
		this.operand = Preconditions.checkNotNull(operand);
	}

	@Override
	public Result execute(final Optional<Map<String, Expression>> variables) throws ExecutionException {
		Preconditions.checkNotNull(variables);

		return this.symbol.unary(this.operand.execute(variables));
	}
}
