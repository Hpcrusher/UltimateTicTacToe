package hpcrusher.web.controller;

/**
 * @author liebl
 */

import hpcrusher.model.Greeting;
import hpcrusher.model.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {


    @MessageMapping("/hello")
    @SendTo("/back")
    public Greeting greeting(HelloMessage message) throws Exception {
        return new Greeting("Hello!");
    }

}
