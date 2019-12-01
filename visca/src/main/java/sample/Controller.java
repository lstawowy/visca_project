package main.java.sample;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @RequestMapping(method = RequestMethod.POST, path = "/connect")
    public String connect() {
        return "mock";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/up")
    public String toggleUp(@RequestParam(value = "speed") Integer speed) {
        return "mock";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/down")
    public String toggleDown(@RequestParam(value = "speed") Integer speed) {
        return "mock";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/left")
    public String toggleLeft(@RequestParam(value = "speed") Integer speed) {
        return "mock";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/right")
    public String toggleRight(@RequestParam(value = "speed") Integer speed) {
        return "mock";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/zoom_in")
    public String zoomIn(@RequestParam(value = "speed") Integer speed) {
        return "mock";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/zoom_out")
    public String zoomOut(@RequestParam(value = "speed") Integer speed) {
        return "mock";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/clear_all")
    public String clearAll() {
        return "mock";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/switch_port")
    public String switchToPort(@RequestParam(value = "port", defaultValue = "1") String name) {
        return "mock";
    }
}