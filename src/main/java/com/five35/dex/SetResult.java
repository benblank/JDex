package com.five35.dex;

import java.util.Collection;

/**
 * A result which consists of an unordered collection of values.
 * 
 * "Set", here, refers to the common tabletop gaming term, as in
 * "a set of dice", rather than to the Java class of the same name.
 */
public class SetResult extends Result<Collection<? extends Result<?>>> {
	SetResult(final Collection<? extends Result<?>> value) {
		super(value);
	}

	@Override
	public <U extends Result<?>> U cast(final Class<U> type) throws ResultCastException {
		if (type == ScalarResult.class) {
			float sum = 0;

			for (final Result<?> value : this.getValue()) {
				sum += value.cast(ScalarResult.class).getValue();
			}

			return type.cast(new ScalarResult(sum));
		}

		return super.cast(type);
	}
}
