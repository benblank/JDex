package com.five35.dex;

enum BindingPower {
	NONE,
	ADDITION_SUBTRACTION,
	MULTIPLICATION_DIVISION,
	PREFIX_SUBEXPRESSION;

	public boolean isWeakerThan(final BindingPower other) {
		return this.compareTo(other) < 0;
	}
}
