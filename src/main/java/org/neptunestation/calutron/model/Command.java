package org.neptunestation.calutron.model;

public interface Command {
    boolean acceptsCommandString (String command);
    void setInterpreter (Interpreter interpreter);
    Interpreter getInterpreter ();
    String getCommandString ();
    void setCommandString (String command);
    void execute ();}
