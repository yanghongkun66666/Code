package DesignPattern.SimpleFactory.PaymentFactory;

import DesignPattern.SimpleFactory.ConcretePayStrategy.AliPay;
import DesignPattern.SimpleFactory.ConcretePayStrategy.WechatPay;
import DesignPattern.SimpleFactory.IPayment;

//创建核心工厂类
public class PaymentFactory {
    //提供静态方法，根据类型创建产品
    public static IPayment createPayment(String paymentType) {
        if (paymentType == null || paymentType.isEmpty()) {
            return null;
        }

        if ("alipay".equalsIgnoreCase(paymentType)) {
            return new AliPay();
        } else if ("wechatpay".equalsIgnoreCase(paymentType)) {
            return new WechatPay();
        }

        return null;
    }
}
