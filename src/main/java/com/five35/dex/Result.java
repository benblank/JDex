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
	 * @return The result as a list.
	 */
	@Nonnull
	public abstract Multiset<? extends Result> asSet();

	/**
	 * @return The result as a floating-point number.
	 */
	@Nonnull
	public abstract float asScalar();
}
