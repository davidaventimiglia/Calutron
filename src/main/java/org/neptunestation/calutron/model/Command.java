package org.neptunestation.calutron.model;

public interface Command {
    Command getCommand (String name);
    void setContext (Command ctx);
    Command getContext ();
    CommandSet getCommands ();
    void addCommands (Command... commands);
    boolean acceptsCommandString (String command);
    void setCalutronModel (CalutronModel calutronModel);
    CalutronModel getCalutronModel ();
    String getCommandString ();
    void setCommandString (String command);
    void execute ();}
