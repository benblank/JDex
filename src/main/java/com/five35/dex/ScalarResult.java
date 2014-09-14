package com.five35.dex;

import com.google.common.collect.ImmutableList;
import java.util.List;

/**
 * A result which is a single number value.
 */
public class ScalarResult implements Result {
	private final float value;

	ScalarResult(final float value) {
		this.value = value;
	}

	@Override
	public float asFloat() {
		return this.value;
	}

	@Override
	public List<Float> asList() {
		return ImmutableList.of(this.value);
	}
}
