package com.five35.dex;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Map;
import java.util.Random;

class RollExpression implements Expression {
	private final Random random = new Random();
	private final Expression right;

	RollExpression(final Expression right) {
		this.right = right;
	}

	@Override
	public Result<?> execute(final Optional<Map<String, Expression>> variables) throws ExecutionException {
		final Result<?> die = this.right.execute(variables);

		if (die instanceof SetResult) {
			final List<? extends Result<?>> set = ImmutableList.copyOf(((SetResult) die).getValue());
			final int face = this.random.nextInt(set.size());

			return set.get(face);
		}

		return new ScalarResult(this.random.nextInt(die.cast(ScalarResult.class).getValue().intValue()));
	}
}
