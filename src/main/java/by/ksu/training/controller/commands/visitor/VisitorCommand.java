package by.ksu.training.controller.commands.visitor;

import by.ksu.training.controller.commands.Command;
import by.ksu.training.entity.Role;

public abstract class VisitorCommand extends Command {
    public VisitorCommand() {getAllowedRoles().add(Role.VISITOR);}

}
