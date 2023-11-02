package ca.qc.bdeb.sim203.TP2;

import javafx.scene.input.KeyCode;

import java.util.HashMap;

public class Input {
    private static HashMap<KeyCode,Boolean> input=new HashMap<>();
    public static boolean isKeyPressed(KeyCode code) {
        return input.getOrDefault(code, false);
    }
    public static void setKeyPressed(KeyCode code, boolean isPressed) {
        input.put(code, isPressed);
    }
}
