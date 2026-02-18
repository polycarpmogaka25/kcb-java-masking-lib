package com.kcb.masking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

@SpringBootTest
class MaskingApplicationTests {

    @Test
    void main_shouldInvokeSpringApplicationRun() {
        try (var mockedSpringApp = mockStatic(SpringApplication.class)) {
            MaskingApplication.main(new String[]{"arg1", "arg2"});
            mockedSpringApp.verify(() -> SpringApplication.run(MaskingApplication.class, new String[]{"arg1", "arg2"}), times(1));
        }
    }


    @Test
    void main_shouldRunSuccessfully() {
        String[] args = {
                "--server.port=0",
                "--spring.main.banner-mode=off",
                "--amqp.force-async-send=false"
        };
        assertDoesNotThrow(() -> MaskingApplication.main(args));
    }


    @Test
    void applicationContextTest() {
        var app = new MaskingApplication();
        Assertions.assertNotNull(app);
    }
}
