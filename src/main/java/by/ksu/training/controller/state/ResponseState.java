package by.ksu.training.controller.state;

import java.util.HashMap;
import java.util.Map;

/**
 * A class representing state of servlet response
 * @Author Kseniya Oznobishina
 * @Date 21.01.2021
 */
public class ResponseState implements State {
    protected String url;
    private Map<String, Object> attributes = new HashMap<>();


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

    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
