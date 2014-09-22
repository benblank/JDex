package com.five35.dex;

import javax.annotation.concurrent.Immutable;

/**
 * An exception which occurs while executing a Dex expression.
 */
@Immutable
public class ExecutionException extends Exception {
	private static final long serialVersionUID = 2177389954956467035L;

	@SuppressWarnings("javadoc")
	public ExecutionException(final String message) {
		super(message);
	}
}
