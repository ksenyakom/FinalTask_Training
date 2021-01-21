package by.ksu.training.controller.commands;


import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.exception.PersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommandManager {
    ResponseState execute(Command command, HttpServletRequest request, HttpServletResponse response) throws PersistentException;

    void close();
}
