package com.paito.biz.auction.common;

/**
 * Created by patrick on 16/1/17.
 */
public class DAOException extends RuntimeException {

    private static final long serialVersionUID = 1468298595189615850L;

    public DAOException() {

    }

    public DAOException(Throwable cause) {
        super(cause);
    }

    public DAOException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public DAOException(String message) {
        super(message);
    }
}
