package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.commands.Command;
import by.ksu.training.entity.Role;

public abstract class AdminCommand extends Command {
    public AdminCommand() {getAllowedRoles().add(Role.ADMINISTRATOR);}

}
