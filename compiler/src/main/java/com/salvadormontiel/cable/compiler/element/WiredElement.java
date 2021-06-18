package com.salvadormontiel.cable.compiler.element;

import com.salvadormontiel.cable.annotation.Wired;
import com.salvadormontiel.cable.compiler.Registry;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeMirror;

import java.util.Collections;
import java.util.List;

import static javax.lang.model.element.ElementKind.FIELD;

/**
 * @author Salvador Montiel
 */
public class WiredElement {
    private final Element element;
    private final Wired wired;
    private final TypeElement enclosingType;
    private final TypeElement deserializedType;
    private final boolean isField;
    private final List<? extends TypeMirror> parameters;

    public WiredElement(Registry registry, TypeElement enclosingType, Element element) {
        this.element = element;
        this.isField = element.getKind().equals(FIELD);
        this.wired = element.getAnnotation(Wired.class);
        this.enclosingType = enclosingType;
        TypeMirror elementType = element.asType();
        String et = elementType.toString();
        if (!isField) et = et.substring(2);
        TypeElement dt = registry.getElements().getTypeElement(convertPrimitiveToBoxed(et));
        if (dt == null) {
            dt = (TypeElement) registry.getTypes().asElement(elementType);
        }
        this.deserializedType = dt;

        if (!isField) {
            ExecutableType executableType = (ExecutableType) element.asType();
            this.parameters = executableType.getParameterTypes();
        } else this.parameters = Collections.emptyList();
    }

    public String getName() {
        return element.getSimpleName().toString();
    }

    public boolean isReadOnly() {
        return wired.readOnly();
    }

    public TypeElement getEnclosingElement() {
        return enclosingType;
    }

    public String getEnclosingQualifiedName() {
        return enclosingType.getQualifiedName().toString();
    }

    public TypeElement getDeserializedType() {
        return deserializedType;
    }

    private String convertPrimitiveToBoxed(String name) {
        switch (name) {
            case "int": return "java.lang.Integer";
            case "double": return "java.lang.Double";
            case "char": return "java.lang.Character";
            case "long": return "java.lang.Long";
            case "short": return "java.lang.Short";
            case "float": return "java.lang.Float";
            case "void": return "java.lang.Void";
            default: return name;
        }
    }

    public boolean isField() {
        return isField;
    }

    public List<? extends TypeMirror> getParameters() {
        return parameters;
    }
}
