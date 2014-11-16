package org.neptunestation.calutron.model;

import java.util.*;

public abstract class AbstractCommand implements Command {
    protected String commandString = null;
    protected String[] commandTokens = null;
    protected CalutronModel calutronModel = null;
    protected final CommandSet commands = new CommandSet();
    protected CommandContext ctx = null;
    public AbstractCommand (final String command) {
        if (command==null) throw new NullArgumentException("command");
        setCommandString(command);}
    public AbstractCommand (final CommandContext ctx, final String command) {
        if (ctx==null) throw new NullArgumentException("ctx");
        if (command==null) throw new NullArgumentException("command");
        this.ctx = ctx;
        setCommandString(command);}
    public AbstractCommand (final CalutronModel calutronModel, final String command) {
        if (calutronModel==null) throw new NullArgumentException("calutronModel");
        if (command==null) throw new NullArgumentException("command");
        this.calutronModel = calutronModel;
        setCommandString(command);}
    public AbstractCommand (final CommandContext ctx, final CalutronModel calutronModel, final String command) {
        this(calutronModel, command);
        setContext(ctx);}
    @Override public boolean equals (Object obj) {
        if (!(obj instanceof Command)) return false;
        if (this==obj) return true;
        if (Arrays.equals(getCommandTokens(), ((Command)obj).getCommandTokens())) return true;
        return false;}
    @Override public int hashCode () {
        return (Arrays.asList(getCommandTokens())+"").hashCode();}
    @Override public void execute (String... args) {
        execute();}
    @Override public String[] getArguments(String... tokens) {
        return getCommands().getArguments(tokens);}
    @Override public Command getCommand (final String... tokens) {
        if (tokens==null) throw new NullArgumentException("tokens");
        if (getCommands().get(tokens)!=null) return getCommands().get(tokens);
        return new AbstractCommand ("default") {
            @Override public void execute () {
                System.console().printf("No such command:  %s\n", tokens);}};}
    @Override public CommandSet getCommands () {
        return commands;}
    @Override public CommandContext getContext () {
        if (ctx==null) throw new IllegalStateException("Context has not been set");
        return ctx;}
    @Override public void setContext (CommandContext ctx) {
        if (ctx==null) throw new NullArgumentException("command");
        this.ctx = ctx;}
    @Override public void addCommands (final Command... commands) {
        for (int i=0; i<commands.length; i++) if (commands[i]==null) throw new NullArgumentException(String.format("command[%s]", i));
        for (int i=0; i<commands.length; i++) if (commands[i]!=null) commands[i].setContext(new AbstractCommandContext(this, getContext().getState(), getContext().getSettings()){});
        this.commands.add(commands);}
    @Override public boolean acceptsCommandString (final String command) {
        if (command==null) throw new NullArgumentException("command");
        return command.equalsIgnoreCase(getCommandString());}
    @Override public void setCalutronModel (CalutronModel calutronModel) {
        if (calutronModel==null) throw new NullArgumentException("calutronModel");
        this.calutronModel = calutronModel;}
    @Override public CalutronModel getCalutronModel () {
        return calutronModel;}
    @Override public String getCommandString () {
        return commandString.toUpperCase();}
    @Override public String[] getCommandTokens () {
        return commandTokens;}
    @Override public void setCommandString (String command) {
        if (command==null) throw new NullArgumentException("command");
        commandString = command.replaceAll("\\s+", " ").toUpperCase().trim();
        commandTokens = commandString.toUpperCase().trim().split("\\s+");}}
