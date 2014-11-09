package org.neptunestation.calutron.model;

public interface Command {
    void setInterpreter (Interpreter interpreter);
    Interpreter getInterpreter ();
    String getCommandString ();
    void setCommandString (String command);
    void execute ();}
