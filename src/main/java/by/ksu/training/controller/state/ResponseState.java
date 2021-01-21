package by.ksu.training.controller.state;

/**
 * A class representing state of servlet response
 * @Author Kseniya Oznobishina
 * @Date 21.01.2021
 */
public class ResponseState implements State {
    protected String url;
    protected boolean temp;

    public ResponseState(){}

    public ResponseState(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ResponseState(String url, boolean temp) {
        this.url = url;
        this.temp = temp;
    }
}
