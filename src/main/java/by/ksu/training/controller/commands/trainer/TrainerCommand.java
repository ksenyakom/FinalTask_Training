package by.ksu.training.controller.commands.trainer;

import by.ksu.training.controller.commands.Command;
import by.ksu.training.entity.Role;

public abstract class TrainerCommand extends Command {
    public TrainerCommand() {getAllowedRoles().add(Role.ADMINISTRATOR);}
}
