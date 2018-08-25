package jenkins.plugins.rocketchatnotifier.utils;

import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import hudson.model.AbstractDescribableImpl;

public class IgnoreInheritedIntrospector extends JacksonAnnotationIntrospector {
  @Override
  public boolean hasIgnoreMarker(final AnnotatedMember m) {
    return m.getDeclaringClass() == AbstractDescribableImpl.class || super.hasIgnoreMarker(m);
  }
}
