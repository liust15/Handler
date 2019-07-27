package com.liust.handler;

import com.liust.handler.handler.CostomHandler;
import com.liust.handler.handler.Handler;
import com.liust.handler.util.ClassUtils;
import com.liust.handler.util.impl.ClassUtilsImpl;
import java.util.List;

public class Main {

  public static void main(String[] args)
    throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    ClassUtils classUtils = new ClassUtilsImpl();
    //拿到
    List<Class> handlerClass = classUtils.loadClass(Handler.class);
    CostomHandler costomHandler;
    for (Class aClass : handlerClass) {
      costomHandler = (CostomHandler) aClass.newInstance();
      costomHandler.handleMessage();
    }

  }
}
