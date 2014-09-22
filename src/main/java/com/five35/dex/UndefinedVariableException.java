package com.five35.dex;

import com.google.common.base.Preconditions;
import javax.annotation.concurrent.Immutable;

/**
 * An exception which occurs when attempting to reference and undefined
 * variable.
 */
@Immutable
public class UndefinedVariableException extends ExecutionException {
	private static final long serialVersionUID = 2177389954956467035L;

	private final String name;

	@SuppressWarnings("javadoc")
	public UndefinedVariableException(final String name) {
		super(Preconditions.checkNotNull(String.format("No variable named '%s'.", name)));

		this.name = name;
	}

	/**
	 * @return The name which was not defined.
	 */
	public String getName() {
		return this.name;
	}
}
