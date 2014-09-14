package com.five35.dex;

import java.util.List;
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
	List<Float> asList();

	/**
	 * @return The result as a floating-point number.
	 */
	@Nonnull
	float asFloat();
}
