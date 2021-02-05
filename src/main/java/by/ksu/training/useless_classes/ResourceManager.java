package by.ksu.training.useless_classes;

import by.ksu.training.exception.PersistentException;

import javax.servlet.http.Cookie;

public interface ResourceManager {
    String getString(Cookie[] cookies, String key);
}
