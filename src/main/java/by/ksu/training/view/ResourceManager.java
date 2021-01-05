package by.ksu.training.view;

import java.util.Locale;
import java.util.ResourceBundle;

public enum ResourceManager {
        INSTANCE;
        private ResourceBundle resourceBundle;
        private final String resourceName = "properties.text";
        private String languageDefault = "en";
        private String countryDefault ="US";

        private ResourceManager() {
         //   resourceBundle = ResourceBundle.getBundle(resourceName,new Locale(Locale.getDefault().getLanguage()));
            resourceBundle = ResourceBundle.getBundle(resourceName,new Locale(languageDefault, countryDefault));
        }

        public void changeResource(Locale locale) {
            resourceBundle = ResourceBundle.getBundle(resourceName, locale);
        }

        public String getString(String key) {
            return resourceBundle.getString(key);
        }
}
