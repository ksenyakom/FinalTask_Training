package by.ksu.training.controller.commands;

import by.ksu.training.entity.Role;
import by.ksu.training.entity.User;
import by.ksu.training.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * command interface for pattern Command
 */
public abstract class Command {
    private Set<Role> allowedRoles;
  //  private Set<Role> allowedRoles = new HashSet<>();
    private String name;

    protected ServiceFactory factory;

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


    abstract Command.Forward exec(HttpServletRequest request, HttpServletResponse response);


    public static class Forward {
        private String forward;
        private boolean redirect;
        Map<String,Object> attributes = new HashMap<>();

        public Forward(String forward) {
            this.forward = forward;
        }

        public Forward(String forward, boolean redirect) {
            this.forward = forward;
            this.redirect = redirect;
        }

        public String getForward() {
            return forward;
        }

        public void setForward(String forward) {
            this.forward = forward;
        }

        public boolean isRedirect() {
            return redirect;
        }

        public void setRedirect(boolean redirect) {
            this.redirect = redirect;
        }

        public Map<String, Object> getAttributes() {
            return attributes;
        }

        public void setAttributes(Map<String, Object> attributes) {
            this.attributes = attributes;
        }
    }

}
