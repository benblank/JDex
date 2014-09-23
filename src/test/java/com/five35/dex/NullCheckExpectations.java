package com.five35.dex;

import com.google.common.base.Preconditions;
import mockit.Expectations;

final class NullCheckExpectations extends Expectations {
	NullCheckExpectations(final Object... objectsToBeChecked) {
		super(Preconditions.class);

		for (final Object object : objectsToBeChecked) {
			Preconditions.checkNotNull(object);
			this.result = object;
		}
	}
}
