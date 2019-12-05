package main.java.sample;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WorkController {

    private ViscaController controller = new ViscaController();
    private SerialPort serialPort;

    @RequestMapping(method = RequestMethod.GET, path = "/ports")
    public String[] getPortList() {
        return SerialPortList.getPortNames();
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


    @RequestMapping(method = RequestMethod.POST, path = "/tilt_up")
    public String toggleUp() {
        try {
            controller.sendPanTiltUp(serialPort);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        return ResponseStore.getInstance().getLastResponse();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/tilt_down")
    public String toggleDown() {
        try {
            controller.sendPanTiltDown(serialPort);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        return ResponseStore.getInstance().getLastResponse();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/tilt_left")
    public String toggleLeft(@RequestParam(value = "speed") Integer speed) {
        byte b = -1;
        try {
            b = Byte.parseByte(Integer.toString(speed));
        } catch (NumberFormatException ignored) {}
        try {
            if(b > 0 && b < 16) {
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
    public String toggleRight(@RequestParam(value = "speed") Integer speed) {
        byte b = -1;
        try {
            b = Byte.parseByte(Integer.toString(speed));
        } catch (NumberFormatException ignored) {}
        try {
            if(b > 0 && b < 16) {
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
    public String zoomIn(@RequestParam(value = "speed") Integer speed) {
        try {
            controller.sendZoomTeleStd(serialPort);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        return ResponseStore.getInstance().getLastResponse();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/zoom_wide")
    public String zoomOut(@RequestParam(value = "speed") Integer speed) {
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

    @RequestMapping(method = RequestMethod.POST, path = "/address")
    public String switchToPort(@RequestParam(value = "port") String name) {
        byte address = -1;
        try {
            address = Byte.parseByte(name);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            if(address > 0) {
                controller.sendAddress(serialPort, address);
            } else {
                controller.sendAddress(serialPort);
            }
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        return ResponseStore.getInstance().getLastResponse();
    }

}