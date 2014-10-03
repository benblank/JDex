package com.five35.dex;

import com.google.common.base.Optional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

class RepeatExpression implements Expression {
	private final Expression left;
	private final Expression right;

	RepeatExpression(final Expression left, final Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public Result<?> execute(final Optional<Map<String, Expression>> variables) throws ExecutionException {
		final Collection<Result<?>> results = new ArrayList<>();
		final int count = this.left.execute(variables).cast(ScalarResult.class).getValue().intValue();

		for (int i = 0; i < count; i++) {
			results.add(this.right.execute(variables));
		}

		return new SetResult(results);
	}
}
