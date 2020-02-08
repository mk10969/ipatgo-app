package org.uma.extarnal.ipatgo;

public class IPatGoException extends RuntimeException {


    public IPatGoException(String message) {
        super(message);
    }

    public IPatGoException(String message, Throwable cause) {
        super(message, cause);
    }

}
