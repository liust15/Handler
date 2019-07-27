package com.liust.handler.util.impl;

import com.liust.handler.annotation.Order;
import com.liust.handler.util.ClassUtils;
import java.io.File;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClassUtilsImpl implements ClassUtils<Class> {

  private static final String POINT = ".";
  private static final String SLASH = "/";
  private static final String EMPTY = "";
  public static final String CLASSNAME = "class";


  /**
   * 扫描scanPath文件夹下的所有文件，获取到实现中存在extendClass的类
   */
  @Override
  public List<Class> loadClass(String scanPath, Class extendClass) throws ClassNotFoundException {
    List<String> className = getClassName(new File(getRootPath() + scanPath.replace(POINT, SLASH)));
    List<Class> list = new ArrayList<>();
    Class c;
    for (String str : className) {
      c = Class.forName(str);
      if (extendClass.isAssignableFrom(c) && extendClass != c && !Modifier
        .isAbstract(c.getModifiers())) {
        list.add(c);
      }
    }
    return sortByOrder(list);
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
  public List<Class> loadClass(Class extendClass) throws ClassNotFoundException {
    String currentPath = ClassUtilsImpl.class.getResource(POINT).getFile();
    String rootPath = getRootPath();
    currentPath = currentPath.replace(rootPath, EMPTY);
    int i;
    if ((i = currentPath.indexOf(SLASH)) != -1) {
      currentPath = currentPath.substring(0, i);
    }
    return loadClass(currentPath, extendClass);
  }

  private List<String> getClassName(File file) {
    List<String> fileList = new ArrayList<>();
    String rootPath = getRootPath();
    getFilesName(file, fileList);
    return fileList.stream()
      .map(s -> s.replace(rootPath, EMPTY).replace(SLASH, POINT))
      .collect(Collectors.toList());
  }

  private void getFilesName(File file, List<String> fileList) {
    if (file.isDirectory()) {
      File[] files = file.listFiles();
      for (File f : files) {
        if (file.isFile()) {
          getClassFile(file, fileList);
        } else {
          getFilesName(f, fileList);
        }
      }
    } else {
      getClassFile(file, fileList);
    }
  }

  public void getClassFile(File file, List<String> fileList) {
    String fileName = file.getAbsolutePath();
    int i = fileName.lastIndexOf(POINT);
    if (CLASSNAME.equals(fileName.substring(i + 1))) {
      fileList.add(fileName.substring(0, i));
    }
  }

  private String getRootPath() {
    return getClass().getResource(SLASH).getFile();
  }


}
