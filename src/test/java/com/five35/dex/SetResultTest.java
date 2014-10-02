package com.five35.dex;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings({ "javadoc", "static-method" })
public class SetResultTest {
	@Test
	public void cast_castToScalarSums() throws Exception {
		final Collection<ScalarResult> set = ImmutableList.of(new ScalarResult(5), new ScalarResult(3), new ScalarResult(5));
		final SetResult result = new SetResult(set);

		Assert.assertEquals(13, result.cast(ScalarResult.class).getValue(), 0);
	}
}
