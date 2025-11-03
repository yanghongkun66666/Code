package DesignPattern.SimpleFactory.ConcretePayStrategy;

import DesignPattern.SimpleFactory.IPayment;

public class WechatPay implements IPayment {
    @Override
    public void pay() {
        System.out.println("使用微信支付...");
    }
}
