package com.salvadormontiel.cable.compiler.step;

import com.salvadormontiel.cable.compiler.Registry;
import com.salvadormontiel.cable.compiler.writer.AdapterHolderWriter;
import com.salvadormontiel.cable.compiler.writer.SourceWriter;

import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

/**
 * @author Salvador Montiel
 */
public class AdapterHolderStep implements ProcessingStep {
    private final Registry registry;
    private final Messager messager;
    private final SourceWriter<Element> sourceWriter;

    public AdapterHolderStep(Registry registry) {
        this.registry = registry;
        this.messager = registry.getMessager();
        this.sourceWriter = new AdapterHolderWriter(registry);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            try {
                String name = sourceWriter.createSourceName(null);
                JavaFileObject object = registry.getFiler().createSourceFile(name);
                Writer writer = object.openWriter();
                sourceWriter.writeSource(writer, null);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                this.messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
            }
        }

        return false;
    }
}
