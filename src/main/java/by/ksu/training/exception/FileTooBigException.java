package by.ksu.training.exception;

/**
 * @Author Kseniya Oznobishina
 * @Date 29.01.2021
 */
public class FileTooBigException extends Exception{
    public FileTooBigException() {
    }

    public FileTooBigException(String message) {
        super(message);
    }

    public FileTooBigException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileTooBigException(Throwable cause) {
        super(cause);
    }

    public FileTooBigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
