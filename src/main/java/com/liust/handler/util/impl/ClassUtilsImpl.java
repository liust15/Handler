package com.liust.handler.util.impl;

import com.liust.handler.annotation.Order;
import com.liust.handler.util.ClassUtils;
import java.util.List;
import java.util.stream.Collectors;

public class ClassUtilsImpl extends HandlerClassLoader implements ClassUtils {


  /**
   * 扫描scanPath文件夹下的所有文件，获取到实现中存在extendClass的类
   */
  @Override
  public List<Class> loadClass(String scanPath, Class extendClass) {
    this.extendClass = extendClass;
    getClassName(getRootPath() + scanPath.replace(POINT, SLASH));
    return sortByOrder(this.classes);
  }

  /**
   * 根据Order排序
   */
  private List<Class> sortByOrder(List<Class> classes) {
    return classes.stream().sorted((o1, o2) -> {
      Order a1 = (Order) o1.getAnnotation(Order.class);
      Order a2 = (Order) o2.getAnnotation(Order.class);
      return (a1 == null ? Integer.MAX_VALUE : a1.value()) -
        (a2 == null ? Integer.MAX_VALUE : a2.value());
    }).collect(Collectors.toList());
  }

  /**
   * 所有文件，获取到实现中存在extendClass的类
   */
  @Override
  public List<Class> loadClass(Class extendClass) {
    this.extendClass = extendClass;
    String currentPath = getClass().getResource(POINT).getFile();
    String rootPath = getRootPath();
    currentPath = currentPath.replace(rootPath, EMPTY);
    int i;
    if ((i = currentPath.indexOf(SLASH)) != -1) {
      currentPath = currentPath.substring(0, i);
    }
    return loadClass(currentPath, extendClass);
  }


  private void getClassName(String fileName) {
    super.findClass(fileName);
  }

}
