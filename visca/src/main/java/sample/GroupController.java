package main.java.sample;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("/groups")
public class GroupController {

    public Map<String, List<String>> actions = new HashMap();

    @RequestMapping(method = RequestMethod.GET)
    public Map<String, List<String>> getGroups() {
        return this.actions;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{name}")
    public String addActionToGroup(@PathVariable(name = "name") String name, @RequestParam(value = "action") String action) {
        List<String> act;
        if (this.actions.get(name) != null) {
            act = this.actions.get(name);
        } else {
            act = new ArrayList<>();
        }
        act.add(action);
        return "OK";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/execute/{name}")
    public String executeGroup(@PathVariable(name = "name") String name) {
        return ResponseStore.getInstance().getLastResponse();
    }

}