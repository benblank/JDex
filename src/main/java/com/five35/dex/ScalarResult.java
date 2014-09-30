package com.five35.dex;


/**
 * A result which is a single number value.
 */
public class ScalarResult extends Result<Float> {
	ScalarResult(final float value) {
		super(value);
	}

	@Override
	public float asScalar() {
		return this.getValue();
	}
}
