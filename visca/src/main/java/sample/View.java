package main.java.sample;

import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class View extends GridPane {
    private ComboBox<String> commNameField = new ComboBox<>();
    private Button connectBtn = new Button("Połącz");
    private Button tiltLeftBtn = new Button("W lewo");
    private TextField panField = new TextField();
    private Button tiltRightBtn = new Button("W prawo");
    private Button tiltUpBtn = new Button("W górę");
    private Button tiltDownBtn = new Button("W dół");
    private Button zoomTeleBtn = new Button("Zoom tele");
    private Button zoomWideBtn = new Button("Zoom wide");
    private Button clearBtn = new Button("Zeruj");
    private Button createGroupBtn = new Button("Utwórz grupę zadań");
    private Button runGroupBtn = new Button("Uruchom grupę");
    private Button changeAddressBtn = new Button("Zmień adres");
    private TextArea responsesArea = new TextArea();
    private ListView<String> commandGroup = new ListView<>();
    private TextField addressField = new TextField();
    private ViscaController controller = new ViscaController();
    private SerialPort serialPort;

    private Map<String, ParamLambda<SerialPort>> actionMap = new HashMap<>();


    View(){
        build();
    }

    private void build() {
        commNameField.getItems().setAll(SerialPortList.getPortNames());
        if(commNameField.getItems().size() > 0) {
            commNameField.setValue(commNameField.getItems().get(0));
        }
        commNameField.setMinWidth(250);
        commNameField.setPromptText("Nazwa portu");
        responsesArea.setEditable(false);
        add(commNameField, 0,0);
        add(connectBtn,1,0);
        add(new VBox(10, tiltLeftBtn, new HBox(10, new Label("Prędkość"), panField)),0,1);
        tiltLeftBtn.setMaxWidth(Double.MAX_VALUE);
        add(tiltRightBtn,1,1);
        add(tiltUpBtn,0,2);
        add(tiltDownBtn,1,2);
        add(zoomTeleBtn,0,3);
        add(zoomWideBtn,1,3);
        add(clearBtn,0,4);
        add(createGroupBtn,1,4);
        add(commandGroup, 0,5);
        add(responsesArea,1,5);
        add(runGroupBtn, 0,6);
        add(new HBox(10, addressField, changeAddressBtn), 1,6);
        putActions();
        setEvents();
        setPadding(new Insets(20));
        GridPane.setHgrow(commNameField, Priority.ALWAYS);
        GridPane.setHgrow(connectBtn, Priority.ALWAYS);
        GridPane.setHgrow(tiltLeftBtn, Priority.ALWAYS);
        GridPane.setHgrow(tiltRightBtn, Priority.ALWAYS);
        GridPane.setHgrow(tiltUpBtn, Priority.ALWAYS);
        GridPane.setHgrow(tiltDownBtn, Priority.ALWAYS);
        GridPane.setHgrow(zoomWideBtn, Priority.ALWAYS);
        GridPane.setHgrow(zoomTeleBtn, Priority.ALWAYS);
        GridPane.setHgrow(clearBtn, Priority.ALWAYS);
        GridPane.setHgrow(createGroupBtn, Priority.ALWAYS);
        GridPane.setHgrow(runGroupBtn, Priority.ALWAYS);
        GridPane.setValignment(tiltRightBtn, VPos.TOP);
        setHgap(10);
        setVgap(10);
        getChildren().forEach(n -> {
            if(n instanceof Button) {
                ((Button) n).setMaxWidth(Double.MAX_VALUE);
            }
        });
    }

    private void setEvents() {
        connectBtn.setOnAction(e -> connect());
        tiltLeftBtn.setOnAction(e -> actionMap.get("tilt_left").run(serialPort));
        tiltRightBtn.setOnAction(e -> actionMap.get("tilt_right").run(serialPort));
        tiltDownBtn.setOnAction(e -> actionMap.get("tilt_down").run(serialPort));
        tiltUpBtn.setOnAction(e -> actionMap.get("tilt_up").run(serialPort));
        zoomTeleBtn.setOnAction(e -> actionMap.get("zoom_tele").run(serialPort));
        zoomWideBtn.setOnAction(e -> actionMap.get("zoom_wide").run(serialPort));
        clearBtn.setOnAction(e -> actionMap.get("clear").run(serialPort));
        changeAddressBtn.setOnAction(e -> actionMap.get("address").run(serialPort));
        createGroupBtn.setOnAction(e -> {
            CreateGroupView createGroupView = new CreateGroupView();
            createGroupView.showAndWait();
            List<String> items = createGroupView.getItems();
            commandGroup.getItems().setAll(items);
        });
        runGroupBtn.setOnAction(e -> new Thread(() -> commandGroup.getItems().forEach(item -> {
            actionMap.get(item).run(serialPort);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        })).start());
        ResponseStore.getInstance().lastResponseProperty().addListener((obs, old, val) -> responsesArea.appendText(val + "\n"));
    }

    private void connect() {
        String commName = commNameField.getValue();
        if(commName != null) {
            serialPort = new SerialPort(commName);

            try {
                serialPort.openPort();
                serialPort.setParams(9600, 8, 1, 0);
                System.out.println("Address");
                controller.sendAddress(serialPort);
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        }
    }

    private void putActions() {
        actionMap.put("tilt_left", serial -> {
            byte b = -1;
            try {
                b = Byte.parseByte(panField.getText());
            } catch (NumberFormatException ignored) {}
            try {
                if(b > 0 && b < 16) {
                    controller.sendPanTiltLeft(serial, b);
                } else {
                    controller.sendPanTiltLeft(serial);
                }
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        });
        actionMap.put("tilt_right", serial -> {
            byte b = -1;
            try {
                b = Byte.parseByte(panField.getText());
            } catch (NumberFormatException ignored) {}
            try {
                if(b > 0 && b < 16) {
                    controller.sendPanTiltRight(serial, b);
                } else {
                    controller.sendPanTiltRight(serial);
                }
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
        actionMap.put("clear", serial -> {
            try {
                controller.sendClearAll(serial);
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        });
        actionMap.put("address", serial -> {
            byte address = -1;
            try {
                address = Byte.parseByte(addressField.getText());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            try {
                if(address > 0) {
                    controller.sendAddress(serial, address);
                } else {
                    controller.sendAddress(serial);
                }
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        });
    }
}
