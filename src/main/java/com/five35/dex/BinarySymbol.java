package com.five35.dex;

import javax.annotation.Nonnull;

interface BinarySymbol {
	@Nonnull
	Result binary(final Result left, final Result right) throws ExecutionException;
}
