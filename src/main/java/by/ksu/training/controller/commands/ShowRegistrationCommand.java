package by.ksu.training.controller.commands;

import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Set;

/**
 * @Author Kseniya Oznobishina
 * @Date 05.01.2021
 */
public class ShowRegistrationCommand extends Command{
    @Override
    protected Forward exec(HttpServletRequest request, HttpServletResponse response) {
        return new Forward("registration.jsp");
    }

    @Override
    public Set<Role> getAllowedRoles() {
        return null;
    }
}
