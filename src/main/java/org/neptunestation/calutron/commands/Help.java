package org.neptunestation.calutron.commands;

import org.neptunestation.calutron.model.*;

public class Help extends AbstractCommand {
    public Help (String commandString) {
        super(commandString);}
    public Help (CommandContext ctx, String commandString) {
        super(ctx, commandString);}
    @Override public void execute () {
        for (Command c : getContext().getSuperCommand().getCommands()) System.console().printf("%s\n", c.getCommandString());}}
