package DesignPattern.SimpleFactory.ConcretePayStrategy;

import DesignPattern.SimpleFactory.IPayment;

public class AliPay implements IPayment {
    @Override
    public void pay() {
        System.out.println("使用支付宝支付...");
    }
}
