package com.salvadormontiel.cable.compiler.util;

import com.salvadormontiel.cable.compiler.Registry;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

/**
 * @author Salvador Montiel
 */
public final class Util {


    public static boolean isFromType(Registry registry, Class<?> fromClass, TypeMirror type) {
        TypeElement lazyElement = registry.getElements().getTypeElement(fromClass.getCanonicalName());
        DeclaredType lazyType = registry.getTypes().getDeclaredType(lazyElement);
        return registry.getTypes().isAssignable(type, lazyType);
    }

    public static String getComponentSuffix() {
        return "Component";
    }
}
