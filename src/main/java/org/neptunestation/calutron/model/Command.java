package org.neptunestation.calutron.model;

public interface Command {
    String getCommandString ();
    void setCommandString (String command);
    void execute ();}
