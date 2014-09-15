package com.five35.dex;

import com.google.common.collect.Multiset;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

/**
 * Represents the result of executing a Dex expression.
 */
@Immutable
public interface Result {
	/**
	 * @return The result as a list.
	 */
	@Nonnull
	Multiset<? extends Result> asSet();

	/**
	 * @return The result as a floating-point number.
	 */
	@Nonnull
	float asScalar();
}
