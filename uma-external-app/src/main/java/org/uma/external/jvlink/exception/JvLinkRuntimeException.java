package org.uma.external.jvlink.exception;

import java.io.Serializable;

public class JvLinkRuntimeException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1592676865941999515L;

    /**
     * JV-Linkのメソッドが返す戻り値です。
     */
    private final int errorCode;


    public JvLinkRuntimeException(JvLinkErrorCode jvLinkErrorCode) {
        this(jvLinkErrorCode.getCode(), jvLinkErrorCode.getName());
    }

    public JvLinkRuntimeException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public final String getMessage() {
        return String.format("[JV-Link Error Code : %d] %s", this.errorCode, super.getMessage());
    }
}

