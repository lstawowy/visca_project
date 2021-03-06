package main.java.sample;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.*;

@CrossOrigin
@RestController
public class WorkController {

    public WorkController() {
        this.putActions();
    }

    private ViscaController controller = new ViscaController();
    private SerialPort serialPort;

    public List<ActionGroup> actions = new ArrayList<>();
    private Map<String, ParamLambda<SerialPort>> actionMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.GET, path = "/ports")
    public String[] getPortList() {
        return SerialPortList.getPortNames();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/connect")
    public String connect(@RequestParam(value = "port") @NotBlank String portName) {
        serialPort = new SerialPort(portName);
        try {
            serialPort.openPort();
            serialPort.setParams(9600, 8, 1, 0);
            System.out.println("Address");
            controller.sendAddress(serialPort);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        return ResponseStore.getInstance().getLastResponse();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/switch_address")
    public String switchAddress(@RequestParam(value = "address") @NotBlank String name) {
        byte address = -1;
        try {
            address = Byte.parseByte(name);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            if (address > 0) {
                controller.sendAddress(serialPort, address);
            } else {
                controller.sendAddress(serialPort);
            }
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        return ResponseStore.getInstance().getLastResponse();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/tilt_up")
    public String tiltUp() {
        try {
            controller.sendPanTiltUp(serialPort);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        return ResponseStore.getInstance().getLastResponse();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/tilt_down")
    public String tiltDown() {
        try {
            controller.sendPanTiltDown(serialPort);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        return ResponseStore.getInstance().getLastResponse();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/tilt_left")
    public String tiltLeft(@RequestParam(value = "speed") Integer speed) {
        byte b = -1;
        try {
            b = Byte.parseByte(Integer.toString(speed));
        } catch (NumberFormatException ignored) {
        }
        try {
            if (b > 0 && b < 16) {
                controller.sendPanTiltLeft(serialPort, b);
            } else {
                controller.sendPanTiltLeft(serialPort);
            }
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        return ResponseStore.getInstance().getLastResponse();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/tilt_right")
    public String tiltRight(@RequestParam(value = "speed") Integer speed) {
        byte b = -1;
        try {
            b = Byte.parseByte(Integer.toString(speed));
        } catch (NumberFormatException ignored) {
        }
        try {
            if (b > 0 && b < 16) {
                controller.sendPanTiltRight(serialPort, b);
            } else {
                controller.sendPanTiltRight(serialPort);
            }
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        return ResponseStore.getInstance().getLastResponse();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/zoom_tele")
    public String zoomTele() {
        try {
            controller.sendZoomTeleStd(serialPort);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        return ResponseStore.getInstance().getLastResponse();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/zoom_wide")
    public String zoomWide() {
        try {
            controller.sendZoomWideStd(serialPort);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        return ResponseStore.getInstance().getLastResponse();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/clear")
    public String clear() {
        try {
            controller.sendClearAll(serialPort);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        return ResponseStore.getInstance().getLastResponse();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/groups")
    public List<ActionGroup> getGroups() {
        return this.actions;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/groups/{name}")
    public String addGroup(@PathVariable(name = "name") @NotBlank String name, @RequestBody String[] action) {
        ActionGroup e = new ActionGroup(name, Arrays.asList(action));
        actions.add(actions.size(), e);
        return "Group " + name + " added.";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/groups/execute/{name}")
    public String executeGroup(@PathVariable(name = "name") @NotBlank String name, @RequestParam(value = "delay") Integer delay) {
        ActionGroup currentAction = null;
        for (ActionGroup action : actions) {
            if (action.name.equalsIgnoreCase(name)) {
                currentAction = action;
                break;
            }
        }
        List<String> currentActions = Objects.requireNonNull(currentAction).actions;
        new Thread(() -> currentActions.forEach(item -> {
            actionMap.get(item).run(serialPort);
            try {
                Thread.sleep(delay * 1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        })).start();
        return "Group " + name + " executed.";
    }

    private void putActions() {
        actionMap.put("tilt_left", serial -> {
            try {
                controller.sendPanTiltLeft(serial);
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        });
        actionMap.put("tilt_right", serial -> {
            try {
                controller.sendPanTiltRight(serial);
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        });
        actionMap.put("tilt_up", serial -> {
            try {
                controller.sendPanTiltUp(serial);
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        });
        actionMap.put("tilt_down", serial -> {
            try {
                controller.sendPanTiltDown(serial);
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        });

        actionMap.put("zoom_tele", serial -> {
            try {
                controller.sendZoomTeleStd(serial);
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        });
        actionMap.put("zoom_wide", serial -> {
            try {
                controller.sendZoomWideStd(serial);
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        });
    }

}