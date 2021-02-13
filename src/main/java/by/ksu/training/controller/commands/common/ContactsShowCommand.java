package by.ksu.training.controller.commands.common;

import by.ksu.training.controller.commands.Command;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Role;
import by.ksu.training.exception.PersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * @Author Kseniya Oznobishina
 * @Date 08.02.2021
 */
public class ContactsShowCommand extends Command {
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        return new ForwardState("contacts.jsp");
    }

    @Override
    public Set<Role> getAllowedRoles() {
        return null;
    }
}
