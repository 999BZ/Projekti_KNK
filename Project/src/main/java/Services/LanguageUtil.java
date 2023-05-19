package Services;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LanguageUtil {
    private static final StringProperty language = new SimpleStringProperty("English");

    public static StringProperty languageProperty() {
        return language;
    }

    public static String getLanguage() {
        return language.get();
    }

    public static void setLanguage(String lang) {
        language.set(lang);
    }
}
