package org.neptunestation.calutron.commands;

import org.neptunestation.calutron.*;
import org.neptunestation.calutron.model.*;

public class SetPassword extends AbstractCommand {
    public SetPassword (CommandContext ctx, String commandString) {
        super(ctx, commandString);}
    @Override public void execute () {
        ((Calutron)getContext().getState()).setSetting("PASSWORD", new String(System.console().readPassword("%s", "Password:")));}}
