package cotato.growingpain.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @GetMapping("/test")
    public void test() {

        log.warn("위험..");
        log.error("에러 발생1!!!");
        log.error("에러 발생2!!!");


    }
}
