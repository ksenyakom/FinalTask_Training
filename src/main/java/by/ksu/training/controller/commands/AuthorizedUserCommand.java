package by.ksu.training.controller.commands;

import by.ksu.training.entity.Role;

import java.util.Arrays;
import java.util.List;

public abstract class AuthorizedUserCommand extends Command {
    public AuthorizedUserCommand() {getAllowedRoles().addAll(Arrays.asList(Role.values()));}
}
