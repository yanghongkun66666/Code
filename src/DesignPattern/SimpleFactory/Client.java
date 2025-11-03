package DesignPattern.SimpleFactory;

import DesignPattern.SimpleFactory.PaymentFactory.PaymentFactory;

//客户端调用，获取不同的支付方式
public class Client {
    public static void main(String[] args) {
        // 我想用支付宝支付，我不需要知道AliPay这个类，只需要告诉工厂
        IPayment payment = PaymentFactory.createPayment("alipay");
        if (payment != null) {
            payment.pay();
        }


        //想用微信支付
        payment = PaymentFactory.createPayment("wechatpay");
        if (payment != null) {
            payment.pay();
        }
    }
}
