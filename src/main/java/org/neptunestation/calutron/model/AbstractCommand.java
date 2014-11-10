package org.neptunestation.calutron.model;

public abstract class AbstractCommand implements Command {
    String commandString = null;
    Interpreter interpreter = null;
    public AbstractCommand (final String command) {
        if (command==null) throw new NullArgumentException("command");
        setCommandString(command);}
    public AbstractCommand (final Interpreter interpreter, final String command) {
        if (interpreter==null) throw new NullArgumentException("interpreter");
        if (command==null) throw new NullArgumentException("command");
        this.interpreter = interpreter;
        setCommandString(command);}
    @Override public boolean acceptsCommandString (final String command) {
        if (command==null) throw new NullArgumentException("command");
        return command.equalsIgnoreCase(getCommandString());}
    @Override public void setInterpreter (Interpreter interpreter) {
        if (interpreter==null) throw new NullArgumentException("interpreter");
        this.interpreter = interpreter;}
    @Override public Interpreter getInterpreter () {
        return interpreter;}
    @Override public String getCommandString () {
        return commandString.toUpperCase();}
    @Override public void setCommandString (String command) {
        if (command==null) throw new NullArgumentException("command");
        commandString = command.replaceAll("\\s+", " ").toUpperCase().trim();}}
