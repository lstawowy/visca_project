package main.java.sample;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;


class CreateGroupView extends Stage {

    private VBox content = new VBox(10);
    private ListView<String> availableCommands = new ListView<>();
    private ListView<String> selectedCommands = new ListView<>();
    private Button saveBtn = new Button("Zatwierdź");

    CreateGroupView(){
        super();
        build();
        setEvents();
    }

    List<String> getItems(){
        return selectedCommands.getItems();
    }
    private void setEvents() {
        saveBtn.setOnAction(e -> close());
        availableCommands.setOnMouseClicked(e -> {
            if(e.getClickCount() == 2) {
                String selectedItem = availableCommands.getSelectionModel().getSelectedItem();
                selectedCommands.getItems().add(selectedItem);
            }
        });
        selectedCommands.setOnMouseClicked(e -> {
            if(e.getClickCount() == 2) {
                int selectedIndex = selectedCommands.getSelectionModel().getSelectedIndex();
                selectedCommands.getItems().remove(selectedIndex);
            }
        });
    }

    private void build() {
        HBox box = new HBox(10,availableCommands,selectedCommands);
        content.getChildren().addAll(box, saveBtn);
        Scene scene = new Scene(content);
        setScene(scene);
        availableCommands.getItems().setAll(
                Arrays.asList(
                        "tilt_left",
                        "tilt_right",
                        "tilt_up",
                        "tilt_down",
                        "zoom_tele",
                        "zoom_wide",
                        "clear"
                )
        );
    }
}
