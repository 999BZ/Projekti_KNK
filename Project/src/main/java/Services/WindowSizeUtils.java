package Services;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Window;

import java.util.prefs.Preferences;

public class WindowSizeUtils {
    private static final Preferences preferences = Preferences.userNodeForPackage(WindowSizeUtils.class);

    public static void saveWindowSize(String windowName, Region window) {
        preferences.putDouble(windowName + "Width", window.getWidth());
        preferences.putDouble(windowName + "Height", window.getHeight());
    }

    public static void loadWindowSize(String windowName, Region window) {
        double width = preferences.getDouble(windowName + "Width", 800);
        double height = preferences.getDouble(windowName + "Height", 600);
        window.setPrefWidth(width);
        window.setPrefHeight(height);
    }
}
