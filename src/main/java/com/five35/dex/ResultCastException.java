package com.five35.dex;

/**
 * An exception caused by trying to cast a result to an unsupported type.
 */
public class ResultCastException extends ExecutionException {
	private static final long serialVersionUID = -1675522274527361379L;

	private final Class<?> initialType;
	private final Class<?> castType;

	@SuppressWarnings("javadoc")
	public ResultCastException(final Class<?> initialType, final Class<?> castType) {
		super(String.format("Cannot cast a result from %s to %s.", initialType.getName(), castType.getName()));

		this.initialType = initialType;
		this.castType = castType;
	}

	/**
	 * @return The class to which the result was to be cast.
	 */
	public Class<?> getCastType() {
		return this.castType;
	}

	/**
	 * @return The class of the result which was to be cast.
	 */
	public Class<?> getInitialType() {
		return this.initialType;
	}
}
