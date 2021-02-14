package by.ksu.training.controller.commands.trainer;

import by.ksu.training.controller.commands.Command;
import by.ksu.training.entity.Role;

import java.util.Set;

public abstract class TrainerCommand extends Command {
    protected TrainerCommand() {setAllowedRoles(Set.of(Role.TRAINER));}
}
