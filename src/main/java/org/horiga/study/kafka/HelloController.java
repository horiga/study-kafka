package org.horiga.study.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloController {


    @RequestMapping({"", "/", "/hello"})
    public String hello(
            @RequestParam(name="message", required = false, defaultValue = "hello") String message) {

        return "hello";
    }
}
