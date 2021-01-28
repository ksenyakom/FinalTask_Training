package by.ksu.training.controller.state;

/**
 * A state when server must forward response to url "url".
 * @Author Kseniya Oznobishina
 * @Date 21.01.2021
 */
public class ForwardState extends ResponseState {
    public ForwardState() {
    }
    public ForwardState(String url) {
        super(url);
    }

}
