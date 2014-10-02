package com.five35.dex;

import com.google.common.base.Optional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

class SetExpression implements Expression {
	private final Collection<Expression> elements;

	SetExpression(final Collection<Expression> elements) {
		this.elements = elements;
	}

	@Override
	public Result<?> execute(final Optional<Map<String, Expression>> variables) throws ExecutionException {
		final List<Result<?>> values = new ArrayList<>();

		for (final Expression element : this.elements) {
			values.add(element.execute(variables));
		}

		return new SetResult(values);
	}
}
