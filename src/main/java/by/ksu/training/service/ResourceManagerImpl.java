package by.ksu.training.service;

import by.ksu.training.exception.PersistentException;

import javax.servlet.http.Cookie;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @Author Kseniya Oznobishina
 * @Date 08.01.2021
 */
public enum ResourceManagerImpl implements ResourceManager {

    INSTANCE;

    @Override
    public String getString(Cookie[] cookies, String key)  {
        String lang = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("language")) {
                lang = cookie.getValue();
                break;
            }
        }
        Locale locale = null;
        if (lang != null) {
            String[] mass = lang.split("_");
            locale = new Locale(mass[0], mass[1]);
        }
        if (locale == null) {
            locale = Locale.getDefault();
        }

        String resourceName = "properties.text";
        ResourceBundle resourceBundle = ResourceBundle.getBundle(resourceName, locale);
        return resourceBundle.getString(key);
    }
}
