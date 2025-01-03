package tobyspring.hellospring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tobyspring.hellospring.payment.ExRateProvider;
import tobyspring.hellospring.exrate.WebApiExRateProvider;
import tobyspring.hellospring.payment.PaymentService;

import java.time.Clock;

@Configuration  // 구성정보
public class PaymentConfig {
    @Bean
    public PaymentService paymentService() {
        return new PaymentService(exRateProvider(), clock());
    }

    // @Bean
    // public ExRateProvider cachedExRateProvider() {
    //     return new CachedExRateProvider(exRateProvider());
    // }

    @Bean
    public ExRateProvider exRateProvider() {
        return new WebApiExRateProvider();
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    // class OrderService {
    //     ExRateProvider exRateProvider;
    //
    //     public OrderService(ExRateProvider exRateProvider) {
    //         this.exRateProvider = exRateProvider;
    //     }
    // }
}
