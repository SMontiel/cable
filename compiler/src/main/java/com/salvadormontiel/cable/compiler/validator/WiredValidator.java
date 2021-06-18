package com.salvadormontiel.cable.compiler.validator;

import com.salvadormontiel.cable.annotation.CableComponent;
import com.salvadormontiel.cable.compiler.Registry;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;

import static javax.lang.model.element.ElementKind.*;
import static javax.tools.Diagnostic.Kind.ERROR;

/**
 * @author Salvador Montiel
 */
public class WiredValidator implements Validator {
    private final Registry registry;
    private final Messager messager;

    public WiredValidator(Registry registry) {
        this.registry = registry;
        this.messager = registry.getMessager();
    }

    @Override
    public boolean validate(Element enclosingElement, Element element) {
        // TODO: validate that method wired has no parameters
        if (!element.getKind().equals(FIELD) && !element.getKind().equals(METHOD)) {
            messager.printMessage(ERROR, "@Wired applies only to fields or methods.", element);
            return false;
        }

        CableComponent cableComponent = enclosingElement.getAnnotation(CableComponent.class);
        if (!enclosingElement.getKind().equals(CLASS) || cableComponent == null) {
            messager.printMessage(ERROR, "@Wired fields can only be enclosed by component classes.", element);
            return false;
        }

        return true;
    }
}
