package by.ksu.training.useless_classes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Kseniya Oznobishina
 * @Date 30.01.2021
 */

public class Listener implements HttpSessionListener {
    private static Logger logger = LogManager.getLogger(Listener.class);
    private final AtomicInteger activeSessions;

    Listener() {
        super();
        activeSessions = new AtomicInteger();
    }

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        activeSessions.incrementAndGet();
        logger.debug(" {} : session created, total number: {}", event.getClass().getSimpleName(), activeSessions);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        activeSessions.decrementAndGet();
        logger.debug(" {} : session destroyed, total number: {}", event.getClass().getSimpleName(), activeSessions);
    }

    public int getTotalActiveSession() {
        return activeSessions.get();
    }
}