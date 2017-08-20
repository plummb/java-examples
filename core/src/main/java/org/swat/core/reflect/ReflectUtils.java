package org.swat.core.reflect;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.List;

/**
 * Created by swat
 * on 16/8/17.
 */
public class ReflectUtils {
  private int x=0;
  private int y=0;

  public int getX() {
    return x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public static void main(String[] args) throws Exception {
    String[] strs = getters(ReflectUtils.class);
    for(String str:strs){
      System.out.println(str);
    }
  }
  public static String[] getters(Class clazz) throws Exception {
    BeanInfo beanInfo = Introspector.getBeanInfo(clazz, Object.class);
    PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
    List<String> list = new ArrayList<>();
    for(PropertyDescriptor descriptor:descriptors){
      list.add(descriptor.getName());
      System.out.println(descriptor.getName()+" : "+descriptor.getReadMethod()+" : "+descriptor.getWriteMethod());
    }
    return list.toArray(new String[0]);
  }
}
