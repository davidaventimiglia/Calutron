package org.neptunestation.calutron.model;

public abstract class AbstractCommand implements Command {
    String commandString = null;
    CalutronModel calutronmodel = null;
    public AbstractCommand (final String command) {
        if (command==null) throw new NullArgumentException("command");
        setCommandString(command);}
    public AbstractCommand (final CalutronModel calutronmodel, final String command) {
        if (calutronmodel==null) throw new NullArgumentException("calutronmodel");
        if (command==null) throw new NullArgumentException("command");
        this.calutronmodel = calutronmodel;
        setCommandString(command);}
    @Override public boolean acceptsCommandString (final String command) {
        if (command==null) throw new NullArgumentException("command");
        return command.equalsIgnoreCase(getCommandString());}
    @Override public void setCalutronModel (CalutronModel calutronmodel) {
        if (calutronmodel==null) throw new NullArgumentException("calutronmodel");
        this.calutronmodel = calutronmodel;}
    @Override public CalutronModel getCalutronModel () {
        return calutronmodel;}
    @Override public String getCommandString () {
        return commandString.toUpperCase();}
    @Override public void setCommandString (String command) {
        if (command==null) throw new NullArgumentException("command");
        commandString = command.replaceAll("\\s+", " ").toUpperCase().trim();}}
