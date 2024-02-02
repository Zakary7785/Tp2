package ca.qc.bdeb.sim203.TP2;

import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Map;

public class Input {
    private static final Map<KeyCode, Boolean> keyStates = new HashMap<>();

    // Méthode synchronisée pour éviter des problèmes de concurrence
    public static synchronized boolean isKeyPressed(KeyCode code) {
        return keyStates.getOrDefault(code, false);
    }

    // Méthode synchronisée pour éviter des problèmes de concurrence
    public static synchronized void setKeyPressed(KeyCode code, boolean isPressed) {
        keyStates.put(code, isPressed);
    }
}
