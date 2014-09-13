package com.five35.dex;

import com.google.common.base.Preconditions;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

/**
 * A token read from a string of Dex source.
 */
@Immutable
public class Token {
	private final int index;
	private final String value;

	Token(final int index, final String value) {
		this.index = index;
		this.value = Preconditions.checkNotNull(value);
	}

	/**
	 * @return The index within the source at which this token occurred.
	 */
	public int getIndex() {
		return this.index;
	}

	@Override
	@Nonnull
	public String toString() {
		return this.value;
	}
}
