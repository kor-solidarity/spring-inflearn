package tobyspring.hellospring.learningTest;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ClockTest {
    // Clock 을 이용해서 LocalDateTime.now 를 이용

    @Test
    void clock(){
        Clock clock = Clock.systemDefaultZone();

        LocalDateTime d1 = LocalDateTime.now(clock);
        System.out.println();
        LocalDateTime d2 = LocalDateTime.now(clock);

        Assertions.assertThat(d2).isAfter(d1);
    }

    // Clock 을 Test에서 사용할 때 내가 원하는 시간을 지정해서 현재 시간을 가져오게 할 수 있는가?
    @Test
    void fixedClock(){

        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        LocalDateTime d1 = LocalDateTime.now(clock);
        LocalDateTime d2 = LocalDateTime.now(clock);

        LocalDateTime d3 = LocalDateTime.now(clock).plusHours(1);

        Assertions.assertThat(d2).isEqualTo(d1);
        Assertions.assertThat(d3).isEqualTo(d1.plusHours(1));

    }
}
