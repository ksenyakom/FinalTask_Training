package by.ksu.training.controller.state;

/**
 * A state when server must redirect response to url "url".
 * @Author Kseniya Oznobishina
 * @Date 21.01.2021
 */
public class RedirectState extends ResponseState {
    public RedirectState() {
    }
    public RedirectState(String url) {
        super(url);
    }

}
