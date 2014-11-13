package org.neptunestation.calutron.commands;

import org.neptunestation.calutron.*;
import org.neptunestation.calutron.model.*;

public class SetUsername extends AbstractCommand {
    public SetUsername (String commandString) {
        super(commandString);}
    public SetUsername (CommandContext ctx, String commandString) {
        super(ctx, commandString);}
    @Override public void execute () {
        super.execute();
        getContext().setSetting("user", System.console().readLine("Username: "));}}
