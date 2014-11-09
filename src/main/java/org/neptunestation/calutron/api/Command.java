package org.neptunestation.calutron.api;

public interface Command {
    String getCommandString ();
    void setCommandString (String command);
    void execute ();}
