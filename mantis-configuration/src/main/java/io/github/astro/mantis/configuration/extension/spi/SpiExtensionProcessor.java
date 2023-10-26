package io.github.astro.mantis.configuration.extension.spi;

import io.github.astro.mantis.common.util.FileUtils;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static io.github.astro.mantis.common.constant.Constant.FIX_PATH;

/**
 * Extend JDK SPI
 *
 * @see ServiceInterface
 * @see ServiceProvider
 */
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@SupportedAnnotationTypes({"io.github.astro.mantis.configuration.extension.spi.ServiceInterface",
        "io.github.astro.mantis.configuration.extension.spi.ServiceProvider"})
public class SpiExtensionProcessor extends AbstractProcessor {

    private Map<String, File> fileMap;

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> serviceInterfaceElements = roundEnvironment.getElementsAnnotatedWith(ServiceProvider.class);
        for (Element element : serviceInterfaceElements) {
            if (element instanceof TypeElement typeElement) {
                List<? extends AnnotationMirror> annotationMirrors = typeElement.getAnnotationMirrors().stream().filter(annotationMirror -> annotationMirror.getAnnotationType().toString().equals("io.github.astro.mantis.configuration.extension.spi.ServiceProvider")).toList();
                ServiceProviderWrapper wrapper = new ServiceProviderWrapper(annotationMirrors.get(0));
                List<String> allInterfaces = getAllInterfaces(typeElement).stream().map(TypeMirror::toString).toList();
                if (wrapper.interfaces().isEmpty()) {
                    for (String interfaceType : allInterfaces) {
                        String path = FIX_PATH + interfaceType;
                        writeServiceFile(path, typeElement.getQualifiedName().toString());
                    }
                } else {
                    List<String> interfaces = wrapper.interfaces();
                    for (String interfaceType : interfaces) {
                        if (allInterfaces.contains(interfaceType)) {
                            String path = FIX_PATH + interfaceType;
                            writeServiceFile(path, typeElement.getQualifiedName().toString());
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        fileMap = new HashMap<>();
        super.init(processingEnv);
    }

    private File creatrFile(String path) {
        FileObject fileObject;
        try {
            fileObject = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", path);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return new File(fileObject.toUri());
    }

    private void writeServiceFile(String path, String content) {
        File file = fileMap.get(path);
        if (file == null) {
            file = creatrFile(path);
            fileMap.put(path, file);
        }
        FileUtils.writeLineFile(content, file);
    }

    private List<TypeMirror> getAllInterfaces(TypeElement typeElement) {
        Elements elements = processingEnv.getElementUtils();
        List<? extends TypeMirror> typeMirrors = typeElement.getInterfaces().stream().filter(typeMirror -> {
            Element interfaceElement = processingEnv.getTypeUtils().asElement(typeMirror);
            ServiceInterface annotation = interfaceElement.getAnnotation(ServiceInterface.class);
            return annotation != null;
        }).toList();
        List<TypeMirror> interfaces = new ArrayList<>(typeMirrors);
        Element superElement = elements.getTypeElement(typeElement.getSuperclass().toString());
        if (superElement instanceof TypeElement superClassElement) {
            interfaces.addAll(getAllInterfaces(superClassElement));
        }
        return interfaces;
    }

}

