package by.ksu.training.controller.state;

/**
 * @Author Kseniya Oznobishina
 * @Date 21.01.2021
 */
public class ErrorState extends ResponseState {
    public ErrorState() {
    }
    public ErrorState(String url) {
        super(url);
    }

    public ErrorState(String url, boolean temp) {
        super(url);
        this.temp = temp;
    }
}
