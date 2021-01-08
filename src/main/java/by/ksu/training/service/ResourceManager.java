package by.ksu.training.service;

import by.ksu.training.exception.PersistentException;

import javax.servlet.http.Cookie;

public interface ResourceManager {
    String getString(Cookie[] cookies, String key);
}
