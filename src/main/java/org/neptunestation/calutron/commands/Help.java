package org.neptunestation.calutron.commands;

import org.neptunestation.calutron.model.*;

public class Help extends AbstractCommand {
    public Help (CalutronModel calutronModel, String commandString) {
        super(calutronModel, commandString);}
    @Override public void execute () {
        for (Command c : getCalutronModel().getCommands()) System.console().printf("%s\n", c.getCommandString());}}
