package by.ksu.training.controller.commands.common;

import by.ksu.training.controller.commands.Command;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * @Author Kseniya Oznobishina
 * @Date 05.01.2021
 */
public class ShowRegistrationCommand extends Command {
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        return new ResponseState("registration.jsp");
    }

    @Override
    public Set<Role> getAllowedRoles() {
        return null;
    }
}
