package com.five35.dex;

import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import javax.annotation.Nonnull;

/**
 * A result which is a single number value.
 */
public class ScalarResult extends Result {
	private final float value;

	ScalarResult(final float value) {
		this.value = value;
	}

	@Override
	public float asScalar() {
		return this.value;
	}

	@Override
	public Multiset<? extends Result> asSet() {
		return ImmutableMultiset.of(this);
	}

	/**
	 * Indicates whether some other object is "equal to" this one.
	 * 
	 * This implementation considers result type, so as to be suitable for use
	 * by e.g. sets and maps. To determine whether two results are *numerically*
	 * equal, use `a.asScalar() == b.asScalar()`.
	 * 
	 * @param other The object with which to compare.
	 * @return `true` if this object is equal to the other; `false` otherwise.
	 */
	@Override
	public boolean equals(final Object other) {
		return other instanceof ScalarResult && this.value == ((ScalarResult) other).value;
	}

	@Override
	public int hashCode() {
		return 31 + Float.floatToIntBits(this.value);
	}

	@Override
	@Nonnull
	public String toString() {
		return Float.toString(this.value);
	}
}
