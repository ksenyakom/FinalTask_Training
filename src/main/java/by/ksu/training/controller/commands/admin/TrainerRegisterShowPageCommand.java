package by.ksu.training.controller.commands.admin;

import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.exception.PersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Kseniya Oznobishina
 * @Date 07.02.2021
 */
public class TrainerRegisterShowPageCommand extends AdminCommand {
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        return new ForwardState("admin/register_trainer.jsp");

    }
}
