package by.ksu.training.controller.commands;

import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Role;
import by.ksu.training.exception.PersistentException;
import by.ksu.training.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

/**
 * command interface for pattern Command
 */
public abstract class Command {
    private Set<Role> allowedRoles = new HashSet<>();
    private String name;
    protected ServiceFactory factory;

    /**
     *A method returns set of allowed roles.
     *
     * @return - hashset with allowed roles for command,
     *         - null if command allowed for both authorized and unauthorized user,
     *         - empty hashset for commands allowed only for unauthorized users.
     */
    public Set<Role> getAllowedRoles() {
        return allowedRoles;
    }

    public void setAllowedRoles(Set<Role> allowedRoles) {
        this.allowedRoles = allowedRoles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFactory(ServiceFactory factory) {
        this.factory = factory;
    }


    protected abstract ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException;
}
