package org.apache.uima.fit.legacy;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.util.HashMap;
import java.util.Map;

import org.apache.uima.fit.legacy.converter.ConfigurationParameterConverter;
import org.apache.uima.fit.legacy.converter.ExternalResourceConverter;
import org.apache.uima.fit.legacy.converter.FsIndexCollectionConverter;
import org.apache.uima.fit.legacy.converter.FsIndexConverter;
import org.apache.uima.fit.legacy.converter.FsIndexKeyConverter;
import org.apache.uima.fit.legacy.converter.NoConversionConverter;
import org.apache.uima.fit.legacy.converter.OperationalPropertiesConverter;
import org.apache.uima.fit.legacy.converter.SofaCapabilityConverter;
import org.apache.uima.fit.legacy.converter.TypeCapabilityConverter;
import org.apache.uima.fit.util.LegacySupportPlugin;

/**
 * Legacy support plug in for the Google Code version of uimaFIT.
 */
public class LegacySupportPluginImpl implements LegacySupportPlugin {
  private Map<Class<? extends Annotation>, AnnotationConverter<?,?>> converterRegistry;
  
  public LegacySupportPluginImpl() {
    register(new ConfigurationParameterConverter());
    register(new ExternalResourceConverter());
    register(new FsIndexConverter());
    register(new FsIndexCollectionConverter());
    register(new FsIndexKeyConverter());
    register(new OperationalPropertiesConverter());
    register(new SofaCapabilityConverter());
    register(new TypeCapabilityConverter());
  }

  public boolean isAnnotationPresent(AccessibleObject aObject,
          Class<? extends Annotation> aAnnotationClass) {
    Class<? extends Annotation> legacyType = getLegacyType(aAnnotationClass);
    if (legacyType != null) {
      return aObject.isAnnotationPresent(legacyType);
    }
    else {
      return false;
    }
  }

  public boolean isAnnotationPresent(Class<?> aObject,
          Class<? extends Annotation> aAnnotationClass) {
    Class<? extends Annotation> legacyType = getLegacyType(aAnnotationClass);
    if (legacyType != null) {
      return aObject.isAnnotationPresent(legacyType);
    }
    else {
      return false;
    }
  }

  public <L extends Annotation, M extends Annotation> M getAnnotation(AccessibleObject aObject,
          Class<M> aAnnotationClass) {
    // Get converter
    AnnotationConverter<L, M> converter = getConverter(aAnnotationClass);
    // Find legacy annotation
    L legacyAnnotation = aObject.getAnnotation(converter.getLegacyType());
    if (legacyAnnotation != null) {
      // If legacy annotation is present, convert it to a modern annotation
      return converter.convert(legacyAnnotation);
    } else {
      return null;
    }
  }

  public <L extends Annotation, M extends Annotation> M getAnnotation(Class<?> aObject,
          Class<M> aAnnotationClass) {
    // Get converter
    AnnotationConverter<L, M> converter = getConverter(aAnnotationClass);
    // Find legacy annotation
    L legacyAnnotation = aObject.getAnnotation(converter.getLegacyType());
    if (legacyAnnotation != null) {
      // If legacy annotation is present, convert it to a modern annotation
      return converter.convert(legacyAnnotation);
    } else {
      return null;
    }
  }

  /**
   * Get a converter for the given modern type.
   * 
   * @param aModernType a modern annotation type.
   * @return a converter. This method never returns {@code null}.
   */
  @SuppressWarnings("unchecked")
  private <L extends Annotation, M extends Annotation> AnnotationConverter<L, M> getConverter(Class<M> aModernType)
  {
    AnnotationConverter<?,?> conv = converterRegistry.get(aModernType);
    if (conv == null) {
      conv = NoConversionConverter.getInstance();
    }
    return (AnnotationConverter<L, M>) conv;
  }

  private <L extends Annotation, M extends Annotation> Class<L> getLegacyType(Class<M> aModernType)
  {
    AnnotationConverter<L, M> converter = getConverter(aModernType);
    if (converter != null) {
      return converter.getLegacyType();
    }
    else {
      return null;
    }
  }

  /**
   * Register a new converter.
   */
  private void register(AnnotationConverter<?,?> aConverter)
  {
    if (converterRegistry == null) {
      converterRegistry = new HashMap<Class<? extends Annotation>, AnnotationConverter<?,?>>();
    }
    converterRegistry.put(aConverter.getModernType(), aConverter);
  }
}
