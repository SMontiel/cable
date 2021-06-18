package com.salvadormontiel.cable.compiler;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.salvadormontiel.cable.compiler.element.ComponentElement;
import com.salvadormontiel.cable.compiler.element.WiredElement;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.Set;

public class Registry {
    private final Messager messager;
    private final Types types;
    private final Elements elements;
    private final Filer filer;
    private final SetMultimap<String, WiredElement> wireds = LinkedHashMultimap.create();
    private final Set<ComponentElement> components = Sets.newHashSet();

    public Registry(Messager messager, Types types, Elements elements, Filer filer) {
        this.messager = messager;
        this.types = types;
        this.elements = elements;
        this.filer = filer;
    }

    public Messager getMessager() {
        return messager;
    }

    public Types getTypes() {
        return types;
    }

    public Elements getElements() {
        return elements;
    }

    public Filer getFiler() {
        return filer;
    }

    public void addWiredElement(WiredElement columnElement) {
        this.wireds.put(columnElement.getEnclosingQualifiedName(), columnElement);
    }

    public Set<WiredElement> getWiredElements(TypeElement enclosingType) {
        return Sets.newLinkedHashSet(wireds.get(enclosingType.getQualifiedName().toString()));
    }

    public Set<ComponentElement> getComponentElements() {
        return components;
    }

    public void addComponentElement(ComponentElement element) {
        components.add(element);
    }
}
