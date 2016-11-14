package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.CrossOrigin;

import springfox.documentation.annotations.ApiIgnore;

@RestController

public class GreetingController {

  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  @CrossOrigin(origins="*")
  @RequestMapping(value = "/greeting", method = RequestMethod.GET)
  public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
    return new Greeting(counter.incrementAndGet(),
                        String.format(template, name));
  }

  @GetMapping("/greeting-javaconfig")
  public Greeting greetingWithJavaconfig(@RequestParam(required=false, defaultValue="World") String name) {
    System.out.println("===== in greeting =====");
    return new Greeting(counter.incrementAndGet(),
                        String.format(template, name));
  }    
}


