package by.ksu.training.controller.commands.authorized_user;

import by.ksu.training.controller.commands.Command;
import by.ksu.training.entity.Role;

import java.util.Arrays;

public abstract class AuthorizedUserCommand extends Command {
    public AuthorizedUserCommand() {
        getAllowedRoles().addAll(Arrays.asList(Role.values()));
    }
}
