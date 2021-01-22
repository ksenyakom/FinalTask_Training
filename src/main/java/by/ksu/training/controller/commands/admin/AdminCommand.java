package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.commands.Command;
import by.ksu.training.entity.Role;

import java.util.Set;

public abstract class AdminCommand extends Command {
    public AdminCommand() {setAllowedRoles(Set.of(Role.ADMINISTRATOR));}

}
