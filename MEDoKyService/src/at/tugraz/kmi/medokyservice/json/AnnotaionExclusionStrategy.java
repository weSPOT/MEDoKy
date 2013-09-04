package at.tugraz.kmi.medokyservice.json;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

class AnnotationExclusionStrategy implements ExclusionStrategy {

  @Override
  public boolean shouldSkipField(FieldAttributes fa) {
    return fa.getAnnotation(JsonIgnore.class) != null;
  }

  @Override
  public boolean shouldSkipClass(Class<?> clazz) {
    return clazz.getAnnotation(JsonIgnore.class) != null;
  }
}