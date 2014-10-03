package com.five35.dex;

import mockit.Tested;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings({ "javadoc", "static-method" })
public class ResultTest {
	private static class DummyResult extends Result<Class<?>> {
		protected DummyResult() {
			this(Result.class);
		}

		protected DummyResult(final Class<?> value) {
			super(value);
		}
	}

	@Tested
	private ResultTest.DummyResult result;

	@Test
	public void cast_returnsOriginalOnClassMatch() throws Exception {
		Assert.assertSame(this.result, this.result.cast(ResultTest.DummyResult.class));
	}

	@Test(expected = ResultCastException.class)
	public void cast_throwsOnClassMismatch() throws Exception {
		new ResultTest.DummyResult().cast(ScalarResult.class);
	}

	@Test
	public void equals_returnsFalseForNonResults() {
		Assert.assertFalse(this.result.equals(new Integer(0)));
	}

	@Test
	public void equals_returnsFalseForNull() {
		Assert.assertFalse(this.result.equals(null));
	}

	@Test
	public void equals_returnsFalseOnDifferentValue() {
		Assert.assertFalse(this.result.equals(new ResultTest.DummyResult(ResultTest.class)));
	}

	@Test
	public void equals_returnsTrueOnSameValue() {
		Assert.assertTrue(this.result.equals(new ResultTest.DummyResult(Result.class)));
	}

	@Test
	public void hashCode_isStableForSameValue() {
		Assert.assertEquals(this.result.hashCode(), new ResultTest.DummyResult(Result.class).hashCode());
	}

	@Test
	public void hashCode_isUnstableForDifferentValue() {
		Assert.assertNotEquals(this.result.hashCode(), new ResultTest.DummyResult(ResultTest.class).hashCode());
	}
}
