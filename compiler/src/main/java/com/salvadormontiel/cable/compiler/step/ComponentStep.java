package com.salvadormontiel.cable.compiler.step;

import com.salvadormontiel.cable.annotation.CableComponent;
import com.salvadormontiel.cable.annotation.Wired;
import com.salvadormontiel.cable.compiler.Registry;
import com.salvadormontiel.cable.compiler.element.ComponentElement;
import com.salvadormontiel.cable.compiler.element.WiredElement;
import com.salvadormontiel.cable.compiler.validator.ComponentValidator;
import com.salvadormontiel.cable.compiler.validator.Validator;
import com.salvadormontiel.cable.compiler.validator.WiredValidator;
import com.salvadormontiel.cable.compiler.writer.ComponentWriter;
import com.salvadormontiel.cable.compiler.writer.SourceWriter;

import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Salvador Montiel
 */
public class ComponentStep implements ProcessingStep {
    private final Registry registry;
    private final Elements elements;
    private final Messager messager;
    private final Validator componentValidator;
    private final Validator wiredValidator;
    private final SourceWriter<ComponentElement> sourceWriter;

    public ComponentStep(Registry registry) {
        this.registry = registry;
        this.elements = registry.getElements();
        this.messager = registry.getMessager();
        this.componentValidator = new ComponentValidator(registry);
        this.wiredValidator = new WiredValidator(registry);
        this.sourceWriter = new ComponentWriter(registry);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(CableComponent.class).stream()
            .filter(tableElement -> componentValidator.validate(tableElement.getEnclosingElement(), tableElement))
            .map(tableElement -> (TypeElement) tableElement)
            .map(tableElement -> {
                ComponentElement componentElement = new ComponentElement(registry, tableElement);
                registry.addComponentElement(componentElement);
                addMemberElements(tableElement);
                return componentElement;
            })
            .forEach(componentElement -> {
                try {
                    String name = sourceWriter.createSourceName(componentElement);
                    JavaFileObject object = registry.getFiler().createSourceFile(name);
                    Writer writer = object.openWriter();
                    sourceWriter.writeSource(writer, componentElement);
                    writer.flush();
                    writer.close();
                    System.out.println(componentElement.getName() + " component is done!");
                } catch (IOException e) {
                    e.printStackTrace();
                    this.messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage(), componentElement.getTypeElement());
                }
            });

        return false;
    }

    private void addMemberElements(TypeElement tableElement) {
        List<Element> wiredElements = elements.getAllMembers(tableElement).stream()
                .filter(member -> member.getAnnotation(Wired.class) != null
                        || member.getSimpleName().contentEquals("mount")
                        || member.getSimpleName().contentEquals("dehydrate"))
                .collect(Collectors.toList());
        wiredElements.stream()
                .filter(member -> wiredValidator.validate(tableElement, member))
                .forEach(member -> registry.addWiredElement(new WiredElement(registry, tableElement,
                    /*(VariableElement or MethodElement)*/ member)));
    }
}
