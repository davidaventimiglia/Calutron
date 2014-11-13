package org.neptunestation.calutron.commands;

import org.neptunestation.calutron.*;
import org.neptunestation.calutron.model.*;

public class SetPassword extends AbstractCommand {
    public SetPassword (String commandString) {
        super(commandString);}
    public SetPassword (CommandContext ctx, String commandString) {
        super(ctx, commandString);}
    @Override public void execute () {
        super.execute();
        getContext().setSetting("password", new String(System.console().readPassword("%s", "Password:")));}}
