package org.weakref.jmx;

import static java.lang.String.format;

import java.lang.annotation.Annotation;

import com.google.inject.name.Named;

/**
 * Generate JMX object names.
 */
public class ObjectNames {

  /**
   * Produce a standardized JMX object name.
   * 
   * @param clazz
   * @return JMX object name of the form "[package_name]:name=[class_name]"
   */
  public static String generatedNameOf(Class<?> clazz) {
    return format("%s:name=%s", 
        clazz.getPackage().getName(),
        clazz.getSimpleName());
  }
  
  /**
   * Produce a generated JMX object name.
   * 
   * @param clazz
   * @param annotation
   * @return JMX object name of the form "[package_name]:type=[class_name],name=[ann_class_name]"
   */
  public static String generatedNameOf(Class<?> clazz, Annotation annotation) {
    return format("%s:type=%s,name=%s", 
        clazz.getPackage().getName(),
        clazz.getSimpleName(),
        annotation.annotationType().getSimpleName());
  }
  
  /**
   * Produce a generated JMX object name.
   * 
   * @param clazz
   * @param annotationClass
   * @return JMX object name of the form "[package_name]:type=[class_name],name=[ann_class_name]"
   */
  public static String generatedNameOf(Class<?> clazz, Class<? extends Annotation> annotationClass) {
    return format("%s:type=%s,name=%s", 
        clazz.getPackage().getName(),
        clazz.getSimpleName(),
        annotationClass.getSimpleName());
  }
  
  /**
   * Produce a generated JMX object name.
   * 
   * @param clazz
   * @param named
   * @return JMX object name of the form "[package_name]:type=[class_name],name=[named_value]"
   */
  public static String generatedNameOf(Class<?> clazz, Named named)
  {
      return generatedNameOf(clazz, named.value());
  }

    /**
     * Produce a generated JMX object name.
     *
     * @param clazz
     * @param name
     * @return JMX object name of the form "[package_name]:type=[class_name],name=[named_value]"
     */
    public static String generatedNameOf(Class<?> clazz, String name)
    {
        return format("%s:type=%s,name=%s",
                clazz.getPackage().getName(),
                clazz.getSimpleName(),
                quoteValueIfNecessary(name));
    }

    private static String quoteValueIfNecessary(String name)
    {
        boolean needQuote = false;
        StringBuilder builder = new StringBuilder("\"");
        for (int i = 0; i < name.length(); ++i) {
            char c = name.charAt(i);
            switch (c) {
                case ':':
                case ',':
                case '=':
                    needQuote = true;
                    builder.append(c);
                    break;
                case '\"':
                case '?':
                case '*':
                    needQuote = true;
                    builder.append('\\');
                    builder.append(c);
                    break;
                case '\n':
                    needQuote = true;
                    builder.append("\\n");
                    break;
                case '\\':
                    builder.append("\\\\");
                    break;
                default:
                    builder.append(c);
            }
        }

        if (needQuote) {
            name = builder.append('\"').toString();
        }
        return name;
    }
}
