package com.five35.dex;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

/**
 * Represents the result of executing a Dex expression.
 * 
 * @param <T> The type represented by a subclass.
 */
@Immutable
public abstract class Result<T> {
	private final T value;

	@SuppressWarnings("javadoc")
	protected Result(final T value) {
		this.value = value;
	}

	/**
	 * @param type The type as which to interpret this result.
	 * @return This result, cast to the specified class.
	 * @throws ResultCastException When this result cannot be cast to the
	 *         destination type.
	 */
	@Nonnull
	public <U extends Result<?>> U cast(final Class<U> type) throws ResultCastException {
		if (type.isInstance(this)) {
			return type.cast(this);
		}

		throw new ResultCastException(this.getClass(), type);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Result) {
			return Objects.equals(this.getValue(), ((Result<?>) obj).getValue());
		}

		return false;
	}

	/**
	 * @return The value of this result.
	 */
	@Nonnull
	public T getValue() {
		return this.value;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.value);
	}

	@Override
	public String toString() {
		return this.value.toString();
	}
}
