package org.neptunestation.calutron.commands;

import org.neptunestation.calutron.model.*;

public class SetPassword extends AbstractCommand {
    public SetPassword (CalutronModel calutronModel, String commandString) {
        super(calutronModel, commandString);}
    @Override public void execute () {
        getCalutronModel().setSetting("PASSWORD", new String(System.console().readPassword("%s", "Password:")));}}
