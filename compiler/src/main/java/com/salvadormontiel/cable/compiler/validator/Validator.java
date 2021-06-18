package com.salvadormontiel.cable.compiler.validator;

import javax.lang.model.element.Element;

public interface Validator {

    boolean validate(Element enclosingElement, Element element);
}
