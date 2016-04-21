/*
 * Copyright (c) 1994, 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package it.csttech.etltools;

/**
 * Signals that an ETL exception of some sort has occurred. This
 * class is the general class of exceptions produced by failed or
 * interrupted ETL operations.
 *
 * @author  Toninus, Orsone
 */
public
class ETLException extends Exception {
    static final long serialVersionUID = 1L;

    /**
     * Constructs an {@code ETLException} with {@code null}
     * as its error detail message.
     */
    public ETLException() {
        super();
    }

    /**
     * Constructs an {@code ETLException} with the specified detail message.
     *
     * @param message
     *        The detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method)
     */
    public ETLException(String message) {
        super(message);
    }

    /**
     * Constructs an {@code ETLException} with the specified detail message
     * and cause.
     *
     * <p> Note that the detail message associated with {@code cause} is
     * <i>not</i> automatically incorporated into this exception's detail
     * message.
     *
     * @param message
     *        The detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method)
     *
     * @param cause
     *        The cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A null value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     *
     * @since 1.6
     */
    public ETLException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an {@code ETLException} with the specified cause and a
     * detail message of {@code (cause==null ? null : cause.toString())}
     * (which typically contains the class and detail message of {@code cause}).
     * This constructor is useful for IO exceptions that are little more
     * than wrappers for other throwables.
     *
     * @param cause
     *        The cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A null value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     *
     * @since 1.6
     */
    public ETLException(Throwable cause) {
        super(cause);
    }
}
