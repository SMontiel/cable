package com.salvadormontiel.cable.compiler.validator;

import com.salvadormontiel.cable.compiler.Registry;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.tools.Diagnostic;

/**
 * @author Salvador Montiel
 */
public class ComponentValidator implements Validator {
    private final Messager messager;

    public ComponentValidator(Registry registry) {
        this.messager = registry.getMessager();
    }

    @Override
    public boolean validate(Element enclosingElement, Element element) {
        // TODO: validate that component has no constructor
        if (!element.getKind().equals(ElementKind.CLASS)) {
            messager.printMessage(Diagnostic.Kind.ERROR, "@CableComponent applies only to classes.", element);
            return false;
        }

        return true;
    }
}
