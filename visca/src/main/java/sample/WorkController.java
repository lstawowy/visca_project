package main.java.sample;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
public class WorkController {

    private ViscaController controller = new ViscaController();
    private SerialPort serialPort;

    @RequestMapping(method = RequestMethod.GET, path = "/ports")
    public String[] getPortList() {
        return SerialPortList.getPortNames();
//        return new String[]{"test1", "test2", "test3"};
    }

    @RequestMapping(method = RequestMethod.POST, path = "/connect")
    public String connect(@RequestParam(value = "port") String portName) {
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
    public String switchAddress(@RequestParam(value = "address") String name) {
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


    public List<ActionGroup> actions = fillActions();

    private List<ActionGroup> fillActions() {
        ActionGroup actionGroup = new ActionGroup("test", Collections.singletonList("dup"));
        ArrayList<ActionGroup> actionGroups = new ArrayList<>();
        actionGroups.add(actionGroup);
        return actionGroups;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/groups")
    public List<ActionGroup> getGroups() {
        return this.actions;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/groups/{name}")
    public String addGroup(@PathVariable(name = "name") String name, @RequestBody String[] action) {
        ActionGroup e = new ActionGroup(name, Arrays.asList(action));
        actions.add(actions.size(), e);
        return "OK";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/groups/execute/{name}")
    public String executeGroup(@PathVariable(name = "name") String name, @RequestParam(value = "delay") Integer delay) {
        return ResponseStore.getInstance().getLastResponse();
    }


}