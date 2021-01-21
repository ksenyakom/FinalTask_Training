package by.ksu.training.controller.state;

/**
 * @Author Kseniya Oznobishina
 * @Date 21.01.2021
 */
public class ReturnBackState extends ResponseState {
    public ReturnBackState() {
    }
    public ReturnBackState(String url) {
        super(url);
    }

    public ReturnBackState(String url, boolean temp) {
        super(url);
        this.temp = temp;
    }
}
