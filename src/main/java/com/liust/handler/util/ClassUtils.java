package com.liust.handler.util;

import java.util.List;

public interface ClassUtils {

  /**
   * 指定路径加载
   */
  List<Class> loadClass(String scanPath, Class extendClass) throws ClassNotFoundException;

  /**
   * 全局扫描
   */
  List<Class> loadClass(Class extendClass) throws ClassNotFoundException;

}
