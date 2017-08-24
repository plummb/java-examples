package org.swat.core.reflect;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by swat
 * on 16/8/17.
 */
public class ReflectUtils {
  public static String[] getters(Class clazz) throws Exception {
    BeanInfo beanInfo = Introspector.getBeanInfo(clazz, Object.class);
    PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
    List<String> list = new ArrayList<>();
    for (PropertyDescriptor descriptor : descriptors) {
      list.add(descriptor.getName());
      System.out.println(descriptor.getName() + " : " + descriptor.getReadMethod() + " : " + descriptor.getWriteMethod());
    }
    return list.toArray(new String[0]);
  }
}
