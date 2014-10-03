package com.five35.dex;

import com.google.common.base.Optional;
import java.util.ArrayList;
import java.util.List;

class SetSymbol extends Symbol {
	SetSymbol(final String name, final String characters, final BindingPower bindingPower) {
		super(name, characters, bindingPower);
	}

	@Override
	Expression getNullDenotation(final Parser parser) throws ParserException {
		final List<Expression> elements = new ArrayList<>();

		if (parser.previewNextSymbol() != Symbol.VIRTUAL_CLOSE_SET) {
			while (true) {
				elements.add(parser.getExpression(BindingPower.NONE));

				if (parser.previewNextSymbol() != Symbol.VIRTUAL_COMMA) {
					break;
				}

				parser.advance(Optional.of(Symbol.VIRTUAL_COMMA));
			}
		}

		parser.advance(Optional.of(Symbol.VIRTUAL_CLOSE_SET));

		return new SetExpression(elements);
	}
}
