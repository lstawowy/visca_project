package main.java.sample;

import java.util.List;

public class ActionGroup {
    public String name;
    public List<String> actions;

    public ActionGroup() {
    }

    public ActionGroup(String name, List<String> actions) {
        this.name = name;
        this.actions = actions;
    }
}
