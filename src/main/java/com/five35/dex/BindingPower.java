package com.five35.dex;

enum BindingPower {
	NONE,
	REPEAT,
	ADDITION_SUBTRACTION,
	MULTIPLICATION_DIVISION,
	PREFIX_SUBEXPRESSION;

	public boolean isWeakerThan(final BindingPower other) {
		return this.compareTo(other) < 0;
	}
}
