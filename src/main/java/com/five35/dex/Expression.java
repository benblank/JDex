package com.five35.dex;

import com.google.common.base.Optional;
import java.util.Map;
import javax.annotation.concurrent.Immutable;

/**
 * Represents a Dex expression.
 */
@Immutable
public interface Expression {
	/**
	 * Execute this expression.
	 * 
	 * @param variables A mapping containing any variables available to the
	 *        expression.
	 * @return The result of executing the expression.
	 */
	Result execute(Optional<Map<String, Expression>> variables);
}
