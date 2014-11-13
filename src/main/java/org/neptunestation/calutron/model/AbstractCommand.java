package org.neptunestation.calutron.model;

public abstract class AbstractCommand implements Command {
    protected String commandString = null;
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
    @Override public void execute () {}
    @Override public Command getCommand (final String name) {
        if (name==null) throw new NullArgumentException("name");
        if (getCommands().get(name)!=null) return getCommands().get(name);
        return null;}
    @Override public CommandSet getCommands () {
        return commands;}
    @Override public CommandContext getContext () {
        if (ctx==null) throw new IllegalStateException("Context has not been set");
        return ctx;}
    @Override public void setContext (CommandContext ctx) {
        if (ctx==null) throw new NullArgumentException("command");
        // if (this.ctx!=null) throw new IllegalStateException("Context has already been set.");
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
    @Override public void setCommandString (String command) {
        if (command==null) throw new NullArgumentException("command");
        commandString = command.replaceAll("\\s+", " ").toUpperCase().trim();}}
