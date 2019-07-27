package com.liust.handler.handler.impl;

import com.liust.handler.annotation.Order;
import com.liust.handler.handler.CostomHandler;

@Order(3)
public class CostomHandlerImpl3 extends CostomHandler {

  @Override
  public void handleMessage() {
    System.out.println(this.getClass().getName());
  }
}
