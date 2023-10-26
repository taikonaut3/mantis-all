package io.github.astro.mantis.configuration.extension.spi;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceProviderWrapper {

    private String value;

    private List<String> interfaceList;

    public ServiceProviderWrapper(AnnotationMirror annotationMirror) {
        interfaceList = new ArrayList<>();
        Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = annotationMirror.getElementValues();
        for (ExecutableElement key : elementValues.keySet()) {
            AnnotationValue annotationValue = elementValues.get(key);
            if (key.getSimpleName().toString().equals("interfaces")) {
                interfaceList.addAll(getPropertyTypes(annotationValue));
            } else if (key.getSimpleName().toString().equals("value")) {
                value = (String) annotationValue.getValue();
            }
        }

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<String> interfaces() {
        return interfaceList;
    }

    public void setInterfaces(List<String> interfaceList) {
        this.interfaceList = interfaceList;
    }

    private List<String> getPropertyTypes(AnnotationValue value) {
        List<String> propertyTypes = new ArrayList<>();
        @SuppressWarnings("unchecked")
        List<AnnotationValue> values = (List<AnnotationValue>) value.getValue();
        for (AnnotationValue annotationValue : values) {
            TypeMirror typeMirror = (TypeMirror) annotationValue.getValue();
            String propertyType = typeMirror.toString();
            propertyTypes.add(propertyType);
        }
        return propertyTypes;
    }
}
