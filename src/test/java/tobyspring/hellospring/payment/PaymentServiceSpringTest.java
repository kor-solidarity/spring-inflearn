package tobyspring.hellospring.payment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobyspring.hellospring.TestPaymentConfig;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;


// 테스트에선 아래 ContextConfiguration 쓰려면 이게 무조건 있어야 한다.
@ExtendWith(SpringExtension.class)
// 구성정보 클래스를 직접 지정 가능
@ContextConfiguration(classes = TestPaymentConfig.class)
class PaymentServiceSpringTest {

    // 의존관계 주입 - new를 직접 만드는 대신 가져오기
    //
    // @Autowired BeanFactory beanFactory;

    // 보통은 위에서 빈팩토리 갖고오기보다는 그냥 바로 Autowired 적용해버린다고 함...
    @Autowired PaymentService paymentService;
    // 장점: 추후 PaymentService가 많아져도 매번 안바꿔도 됨

    @Autowired Clock clock;
    @Autowired ExRateProviderStub exRateProviderStub;

    @Test
    @DisplayName("prepare 메소드가 요구사항 3가지를 잘 충족했는지 검증")
    void convertedAmount() throws IOException {

        // BeanFactory beanFactory = new AnnotationConfigApplicationContext(TestObjectFactory.class);
        // PaymentService paymentService = beanFactory.getBean(PaymentService.class);

        // exRate:1000

        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        // 검증내용
        // 환율정보 가져오기
        assertThat(payment.getExRate()).isEqualByComparingTo(valueOf(1000));
        // 원화환산금액 계산
        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(valueOf(10_000));

        // exRate:500
        exRateProviderStub.setExRate(valueOf(500));
        Payment payment2 = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        assertThat(payment2.getExRate()).isEqualByComparingTo(valueOf(500));
        assertThat(payment2.getConvertedAmount()).isEqualByComparingTo(valueOf(5_000));


        // validUntil(valueOf(500), valueOf(5_000));
        // testAmount(valueOf(1000), valueOf(10_000));
        // testAmount(valueOf(3_000), valueOf(30_000));

        // 원화환산금액 유효시간 계산
        // assertThat(payment.getValidUntil()).isAfter(LocalDateTime.now());
        // assertThat(payment.getValidUntil()).isBefore(LocalDateTime.now().plusMinutes(30));
    }


    @Test
    void validUntil() throws IOException {
        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        // valid until이 prepare() 삼십분 뒤로 설정됐는가?
        LocalDateTime now = LocalDateTime.now(this.clock);
        LocalDateTime expectedValidUntil = now.plusMinutes(30);

        Assertions.assertThat(payment.getValidUntil()).isEqualTo(expectedValidUntil);

    }


    // private static void testAmount(BigDecimal exRate, BigDecimal convertedAmount) throws IOException {
    // }

}