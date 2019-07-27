package com.liust.handler.handler.impl;

import com.liust.handler.annotation.Order;
import com.liust.handler.handler.CostomHandler;

@Order(2)
public class CostomHandlerImpl2 extends CostomHandler {

  @Override
  public void handleMessage() {
    System.out.println(this.getClass().getName());

  }
}
