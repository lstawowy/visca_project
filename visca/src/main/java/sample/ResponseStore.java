package main.java.sample;

import javafx.beans.property.SimpleStringProperty;

public class ResponseStore {
    private static ResponseStore instance = new ResponseStore();
    private SimpleStringProperty lastResponse = new SimpleStringProperty("");

    static ResponseStore getInstance(){
        return instance;
    }

    private ResponseStore(){}

    public String getLastResponse() {
        return lastResponse.get();
    }

    SimpleStringProperty lastResponseProperty() {
        return lastResponse;
    }

    void setLastResponse(String lastResponse) {
        this.lastResponse.set(decode(lastResponse));
    }
//p=13
//t=15
    private String decode(String lastResponse) {
        String trim = lastResponse.trim();
        if(trim.contains("01 06 01 0")) {
            String res = "Ruch";
            if(trim.endsWith("03 01 FF")) {
                res += " w górę";
            } else if(trim.endsWith("03 02 FF")) {
                res += " w dół";
            } else if(trim.endsWith("02 03 FF")) {
                res += " w prawo";
            } else if(trim.endsWith("01 03 FF")) {
                res += " w lewo";
            }
            res += ", p=" + trim.charAt(13);
            res += ", t=" + trim.charAt(15);
            return res;
        } else if(trim.contains("01 00 01 FF")) {
            return "Wyczyszczono bufor";
        } else if(trim.length() == 11 && trim.charAt(1) == trim.charAt(7)) {
            return "Adres: " + trim.charAt(1);
        } else if(trim.endsWith("01 04 07 02 FF")) {
            return "Zoom tele";
        } else if(trim.endsWith("01 04 07 03 FF")) {
            return "Zoom wide";
        }
        return lastResponse;
    }
}
