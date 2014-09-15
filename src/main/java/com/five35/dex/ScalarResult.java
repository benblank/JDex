package com.five35.dex;

import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;

/**
 * A result which is a single number value.
 */
public class ScalarResult implements Result {
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
}
