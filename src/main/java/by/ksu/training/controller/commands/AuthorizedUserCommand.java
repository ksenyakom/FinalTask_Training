package by.ksu.training.controller.commands;

import by.ksu.training.entity.Role;

import java.util.Arrays;

public abstract class AuthorizedUserCommand extends Command {
    public AuthorizedUserCommand() {
      //  getAllowedRoles().addAll(Arrays.asList(Role.values()));
        setAllowedRoles(null);
    }
}
