package tobyspring.hellospring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import tobyspring.hellospring.payment.Payment;
import tobyspring.hellospring.payment.PaymentService;

import java.io.IOException;
import java.math.BigDecimal;

public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(PaymentConfig.class);
        PaymentService paymentService = beanFactory.getBean(PaymentService.class);

        Payment payment1 = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println("payment1: " + payment1);

        // System.out.println("------------------\n");
        //
        // Payment payment2 = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        // System.out.println("payment2: " + payment2);
        // System.out.println("------------------\n");

        // TimeUnit.SECONDS.sleep(3);
        //
        // Payment payment3 = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        // System.out.println("payment3: " + payment3);

    }
}
