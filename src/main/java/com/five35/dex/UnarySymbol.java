package com.five35.dex;

import javax.annotation.Nonnull;

interface UnarySymbol {
	@Nonnull
	Result<?> unary(final Result<?> operand) throws ExecutionException;
}
