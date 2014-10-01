package com.five35.dex;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import java.util.Map;

class BinaryExpression implements Expression {
	private final BinarySymbol symbol;
	private final Expression left;
	private final Expression right;

	BinaryExpression(final BinarySymbol symbol, final Expression left, final Expression right) {
		this.symbol = Preconditions.checkNotNull(symbol);
		this.left = Preconditions.checkNotNull(left);
		this.right = Preconditions.checkNotNull(right);
	}

	@Override
	public Result<?> execute(final Optional<Map<String, Expression>> variables) throws ExecutionException {
		Preconditions.checkNotNull(variables);

		return this.symbol.binary(this.left.execute(variables), this.right.execute(variables));
	}
}
