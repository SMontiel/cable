package com.salvadormontiel.cable.compiler.element;

import com.salvadormontiel.cable.annotation.CableComponent;
import com.salvadormontiel.cable.compiler.Registry;
import com.salvadormontiel.cable.compiler.util.Str;
import com.salvadormontiel.cable.compiler.util.Util;
import com.salvadormontiel.cable.internal.AdapterHolder;

import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * @author Salvador Montiel
 */
public class ComponentElement implements Comparable<ComponentElement> {
    private final Registry registry;
    private final TypeElement element;

    public ComponentElement(Registry registry, TypeElement element) {
        this.registry = registry;
        this.element = element;
    }

    public String getName() {
        return element.getSimpleName() + Util.getComponentSuffix();
    }

    public String getQualifiedName() {
        return AdapterHolder.IMPL_CLASS_PACKAGE + "." + element.getSimpleName() + Util.getComponentSuffix();
    }

    public String getComponentName() {
        String tableName = element.getAnnotation(CableComponent.class).value();
        return tableName.isEmpty() ? Str.kebab(getComponentClassName()) : tableName;
    }

    public Set<WiredElement> getWiredElements() {
        return registry.getWiredElements(element);
    }

    public String getComponentClassName() {
        return element.getSimpleName().toString();
    }

    public String getComponentQualifiedName() {
        return element.getQualifiedName().toString();
    }

    public String getComponentPackage() {
        return getComponentQualifiedName().substring(0, getComponentQualifiedName().lastIndexOf(getComponentClassName()) - 1);
    }

    public TypeElement getTypeElement() {
        return element;
    }

    @Override
    public int compareTo(ComponentElement other) {
        return getQualifiedName().compareTo(other.getQualifiedName());
    }
}
