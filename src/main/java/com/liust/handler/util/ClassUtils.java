package com.liust.handler.util;

import java.util.List;

public interface ClassUtils<Class> {

  /**
   * 指定路径加载
   */
  List<Class> loadClass(String scanPath, java.lang.Class extendClass) throws ClassNotFoundException;

  /**
   * 全局扫描
   */
  List<Class> loadClass(java.lang.Class extendClass) throws ClassNotFoundException;

}
