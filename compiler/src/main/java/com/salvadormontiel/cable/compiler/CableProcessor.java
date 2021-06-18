package com.salvadormontiel.cable.compiler;

import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableSet;
import com.salvadormontiel.cable.annotation.CableComponent;
import com.salvadormontiel.cable.compiler.step.AdapterHolderStep;
import com.salvadormontiel.cable.compiler.step.ComponentStep;
import com.salvadormontiel.cable.compiler.step.ProcessingStep;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@AutoService(Processor.class)
public class CableProcessor extends AbstractProcessor {
	private ImmutableSet<? extends ProcessingStep> processingSteps;
	private Registry registry;

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);

		registry = new Registry(
				processingEnv.getMessager(),
				processingEnv.getTypeUtils(),
				processingEnv.getElementUtils(),
				processingEnv.getFiler()
		);

		processingSteps = ImmutableSet.of(
				new ComponentStep(registry),
				new AdapterHolderStep(registry)
		);
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		processingSteps.forEach(processingStep -> processingStep.process(annotations, roundEnv));

		return false;
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return ImmutableSet.of(
				CableComponent.class.getName()
		);
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}
}
