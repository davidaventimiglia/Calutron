package org.neptunestation.calutron.model;

public interface Command {
    boolean acceptsCommandString (String command);
    void setCalutronModel (CalutronModel calutronModel);
    CalutronModel getCalutronModel ();
    String getCommandString ();
    void setCommandString (String command);
    void execute ();}
