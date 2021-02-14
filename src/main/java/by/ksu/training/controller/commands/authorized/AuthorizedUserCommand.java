package by.ksu.training.controller.commands.authorized;

import by.ksu.training.controller.commands.Command;
import by.ksu.training.entity.Role;

import java.util.Arrays;

public abstract class AuthorizedUserCommand extends Command {
    protected AuthorizedUserCommand() {
        getAllowedRoles().addAll(Arrays.asList(Role.values()));
    }
}
