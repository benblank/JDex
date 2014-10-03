package com.five35.dex;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import java.util.Map;

class ReferenceSymbol extends Symbol {
	ReferenceSymbol(final String name) {
		super(Preconditions.checkNotNull(name), BindingPower.NONE);
	}

	@Override
	Expression getNullDenotation(final Parser parser) throws ParserException {
		final String characters = Preconditions.checkNotNull(parser).getCurrentToken().toString();

		return new Expression() {
			final String name = characters;

			@Override
			public Result<?> execute(final Optional<Map<String, Expression>> variables) throws ExecutionException {
				if (variables.isPresent() && variables.get().containsKey(this.name)) {
					return variables.get().get(characters).execute(variables);
				}

				throw new UndefinedVariableException(this.name);
			}
		};
	}
}
