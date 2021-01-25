package by.ksu.training.controller.commands.admin_and_trainer;

import by.ksu.training.controller.commands.Command;
import by.ksu.training.entity.Role;

import java.util.Set;

public abstract class AdminAndTrainerCommand extends Command {
    public AdminAndTrainerCommand() {setAllowedRoles(Set.of(Role.ADMINISTRATOR, Role.TRAINER));}

}
