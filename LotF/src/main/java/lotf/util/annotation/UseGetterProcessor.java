package main.java.lotf.util.annotation;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@SupportedAnnotationTypes("main.java.lotf.util.annotation.UseGetter")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class UseGetterProcessor extends AbstractProcessor {
	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
	}
	
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
		for (Element e : env.getElementsAnnotatedWith(UseGetter.class)) {
			UseGetter an = e.getAnnotation(UseGetter.class);
			
			StringBuilder sb = new StringBuilder();
			for (String m : an.value()) {
				sb.append(m + ", ");
			}
			
			String msg = "Field '" + e.getSimpleName() + "' should not be used! You should use the provided getter instead ! (" + sb.substring(0, sb.length() - 2) + ")";
			
			processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg, e);
		}
		
		return true;
	}
}
