package org.neptunestation.calutron.model;

public class NullArgumentException extends IllegalArgumentException {
    public NullArgumentException (Object param) {
        super(String.format("The `%s' parameter cannot be null.", param));}
    public NullArgumentException (Object param, Throwable cause) {
        super(String.format("The `%s' parameter cannot be null.", param), cause);}
    public NullArgumentException (Throwable cause) {
        super(cause);}}
