package main.java.sample;

import jssc.SerialPort;
import jssc.SerialPortException;
import main.java.sample.cmd.*;

public class ViscaController {
    private byte address = 8;

    void sendClearAll(SerialPort serialPort) throws SerialPortException {
        byte[] cmdData = (new ClearAllCmd()).createCommandData();
        ViscaCommand vCmd = new ViscaCommand();
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;
        vCmd.destinationAdr = 1;
        cmdData = vCmd.getCommandData();
        ResponseStore.getInstance().setLastResponse(byteArrayToString(cmdData));
        serialPort.writeBytes(cmdData);
    }

    void sendPanTiltLeft(SerialPort serialPort) throws SerialPortException {
        sendPanTiltLeft(serialPort, (byte) 5);
    }

    void sendPanTiltLeft(SerialPort serialPort, byte pan) throws SerialPortException {
        byte[] cmdData = (new PanTiltLeftCmd()).createCommandData();
        cmdData[3] = pan;
        cmdData[4] = address;
        ViscaCommand vCmd = new ViscaCommand();
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;
        vCmd.destinationAdr = 1;
        cmdData = vCmd.getCommandData();
        ResponseStore.getInstance().setLastResponse(byteArrayToString(cmdData));
        serialPort.writeBytes(cmdData);
    }

    void sendPanTiltRight(SerialPort serialPort) throws SerialPortException {
        sendPanTiltRight(serialPort, (byte)5);
    }

    void sendPanTiltRight(SerialPort serialPort, byte pan) throws SerialPortException {
        byte[] cmdData = (new PanTiltRightCmd()).createCommandData();
        cmdData[3] = pan;
        ViscaCommand vCmd = new ViscaCommand();
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;
        vCmd.destinationAdr = 1;
        cmdData = vCmd.getCommandData();
        ResponseStore.getInstance().setLastResponse(byteArrayToString(cmdData));
        serialPort.writeBytes(cmdData);
    }

    void sendPanTiltUp(SerialPort serialPort) throws SerialPortException {
        byte[] cmdData = (new PanTiltUpCmd()).createCommandData();
        ViscaCommand vCmd = new ViscaCommand();
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;
        vCmd.destinationAdr = 1;
        cmdData = vCmd.getCommandData();
        ResponseStore.getInstance().setLastResponse(byteArrayToString(cmdData));
        serialPort.writeBytes(cmdData);
    }

    void sendPanTiltDown(SerialPort serialPort) throws SerialPortException {
        byte[] cmdData = (new PanTiltDownCmd()).createCommandData();
        ViscaCommand vCmd = new ViscaCommand();
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;
        vCmd.destinationAdr = 1;
        cmdData = vCmd.getCommandData();
        ResponseStore.getInstance().setLastResponse(byteArrayToString(cmdData));
        serialPort.writeBytes(cmdData);
    }

    void sendAddress(SerialPort serialPort) throws SerialPortException {
        sendAddress(serialPort, (byte) 8);
    }

    void sendAddress(SerialPort serialPort, byte address) throws SerialPortException {
        this.address = address;
        byte[] cmdData = (new AddressCmd()).createCommandData();
        cmdData[1] = address;
        ViscaCommand vCmd = new ViscaCommand();
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;
        vCmd.destinationAdr = address;
        cmdData = vCmd.getCommandData();
        ResponseStore.getInstance().setLastResponse(byteArrayToString(cmdData));
        serialPort.writeBytes(cmdData);
    }

    private String byteArrayToString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString();
    }

    void sendZoomTeleStd(SerialPort serialPort) throws SerialPortException {
        byte[] cmdData = (new ZoomTeleStdCmd()).createCommandData();
        ViscaCommand vCmd = new ViscaCommand();
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;
        vCmd.destinationAdr = 1;
        cmdData = vCmd.getCommandData();
        ResponseStore.getInstance().setLastResponse(byteArrayToString(cmdData));
        serialPort.writeBytes(cmdData);
    }

    void sendZoomWideStd(SerialPort serialPort) throws SerialPortException {
        byte[] cmdData = (new ZoomWideStdCmd()).createCommandData();
        ViscaCommand vCmd = new ViscaCommand();
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;
        vCmd.destinationAdr = 1;
        cmdData = vCmd.getCommandData();
        ResponseStore.getInstance().setLastResponse(byteArrayToString(cmdData));
        serialPort.writeBytes(cmdData);
    }
}
