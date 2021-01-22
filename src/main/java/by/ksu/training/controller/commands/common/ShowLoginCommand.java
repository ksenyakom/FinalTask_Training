package by.ksu.training.controller.commands.common;

import by.ksu.training.controller.commands.Command;
import by.ksu.training.controller.state.ForwardState;
import by.ksu.training.controller.state.ResponseState;
import by.ksu.training.entity.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * @Author Kseniya Oznobishina
 * @Date 05.01.2021
 */
public class ShowLoginCommand extends Command {
    @Override
    protected ResponseState exec(HttpServletRequest request, HttpServletResponse response) {
        // TODO проверить куки,если там есть юзер, то положить его в request
        return new ForwardState("login.jsp");
    }
}
