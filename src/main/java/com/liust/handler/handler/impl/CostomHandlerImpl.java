package com.liust.handler.handler.impl;

import com.liust.handler.annotation.Order;
import com.liust.handler.handler.CostomHandler;

@Order(1)
public class CostomHandlerImpl extends CostomHandler {

  @Override
  public void handleMessage() {
    System.out.println(this.getClass().getName());
  }
}
