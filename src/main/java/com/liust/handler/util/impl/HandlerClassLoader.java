package com.liust.handler.util.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public abstract class HandlerClassLoader extends ClassLoader {

  protected static final String POINT = ".";
  protected static final String SLASH = "/";
  protected static final String EMPTY = "";
  protected static final String CLASSNAME = "class";
  List<Class> classes;
  Class extendClass;

  public HandlerClassLoader() {
    classes = new ArrayList<>();
  }

  /**
   * 重写findClass方法
   */
  @Override
  public Class<?> findClass(String name) {
    getClassess(new File(name));
    return null;
  }

  private void getClassess(File file) {
    if (file.isDirectory()) {
      File[] files = file.listFiles();
      for (File f : files) {
        if (file.isFile()) {
          loadClassData(file);
        } else {
          getClassess(f);
        }
      }
    } else {
      loadClassData(file);
    }
  }


  private void loadClassData(File file) {
    String className = getClassFile(file.getAbsolutePath());
    if (className == null || this.findLoadedClass(className) != null) {
      return;
    }
    try (FileInputStream is = new FileInputStream(file);
      ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      int b;
      while ((b = is.read()) != -1) {
        baos.write(b);
      }
      final byte[] bytes = baos.toByteArray();
      final Class<?> aClass = this
        .defineClass(className, bytes, 0, bytes.length);
      if (extendClass.isAssignableFrom(aClass) &&
        extendClass != aClass &&
        !Modifier.isAbstract(aClass.getModifiers())) {
        classes.add(aClass);
      }
    } catch (Exception e) {
      return;
    }
  }

  private String getClassFile(String className) {
    int i = className.lastIndexOf(POINT);
    final String rootPath = getRootPath();
    if (CLASSNAME.equals(className.substring(i + 1))) {
      return className.substring(0, i).replace(rootPath, EMPTY).replaceAll(SLASH, POINT);
    }
    return null;
  }

  /**
   * 获取项目路径
   */
  protected String getRootPath() {
    return getClass().getResource(SLASH).getFile();
  }

}