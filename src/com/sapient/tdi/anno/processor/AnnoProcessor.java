package com.sapient.tdi.anno.processor;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import com.sapient.tdi.anno.CodeReview;


//@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedAnnotationTypes("com.sapient.tdi.anno.CodeReview")
public class AnnoProcessor extends AbstractProcessor {
	private static DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {
		for(TypeElement te : annotations){
			Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(te);
			for(Element elem : elements){
				String element = elem.getSimpleName().toString();
				CodeReview codeReview = elem.getAnnotation(CodeReview.class);
				if(null != codeReview )
				try {
					Date changeDate = df.parse(codeReview.changeDate());
					Date reviewDate = df.parse(codeReview.reviewDate());
					String message = String.format("Change Date: %s, Review Date: %s, Authors: %s",df.format(changeDate), df.format(reviewDate), Arrays.toString(codeReview.authors()));
					if(changeDate.after(reviewDate)){
						String errorMsg = "Code Review not done for "+element;
						processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, errorMsg+" - "+message);
					}else{
						String Msg = "Code review Details for "+element;
						processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, Msg);
						processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);
					}
				} catch (ParseException e) {
					String message = "Error processing Type "+element;
					processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message);
					processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
				}				
			}
		}
		
		return true;
	}

}
