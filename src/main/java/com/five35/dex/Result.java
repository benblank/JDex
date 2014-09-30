package com.five35.dex;

import com.google.common.collect.Multiset;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

/**
 * Represents the result of executing a Dex expression.
 */
@Immutable
public abstract class Result {

	/**
	 * @return The result as a floating-point number.
	 */
	@Nonnull
	@Deprecated
	public abstract float asScalar();

	/**
	 * @param type The type as which to interpret this result.
	 * @return This result, cast to the specified class.
	 * @throws ResultCastException When this result cannot be cast to the
	 *         destination type.
	 */
	@Nonnull
	public <T extends Result> T cast(final Class<T> type) throws ResultCastException {
		if (type.isInstance(this)) {
			return type.cast(this);
		}

		throw new ResultCastException(this.getClass(), type);
	}
}
