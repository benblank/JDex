package com.five35.dex;

import com.google.common.base.Optional;
import java.util.Map;

/**
 * Represents a Dex expression.
 */
public interface Expression {
	/**
	 * Execute this expression.
	 * 
	 * @param variables A mapping containing any variables available to the expression.
	 * @return The result of executing the expression.
	 */
	public Result execute(Optional<Map<String, Expression>> variables);
}
