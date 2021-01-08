package by.ksu.training.controller.commands;

import by.ksu.training.entity.Role;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Set;

/**
 * @Author Kseniya Oznobishina
 * @Date 07.01.2021
 */
public class ChangeLanguageCommand extends Command{
    public static final String LANGUAGE = "language";
    @Override
    protected Forward exec(HttpServletRequest request, HttpServletResponse response) {
        String language = request.getParameter("language");
        String country = request.getParameter("country");

//        if (language != null && country != null) {
//            Locale locale = new Locale(language,country);
//        }

//        Cookie langCookie = null;

//        for (Cookie cookie : request.getCookies()) {
//            if (cookie.getName().equals(LANGUAGE)) {
//                langCookie = cookie;
//            }
//        }

        Cookie cookie = new Cookie("language", language + "_" + country);
        response.addCookie(cookie);

   //     ResourceManager resourceManager = ResourceManager.INSTANCE;
   //     resourceManager.changeResource(locale);
        request.setAttribute("command", null);
        //TODO переходить на текущую страницу
        return new Forward("/index.html", true);
    }

    @Override
    public Set<Role> getAllowedRoles() {
        return null;
    }
}
