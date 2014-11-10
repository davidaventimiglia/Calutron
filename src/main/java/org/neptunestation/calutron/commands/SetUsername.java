package org.neptunestation.calutron.commands;

import org.neptunestation.calutron.model.*;

public class SetUsername extends AbstractCommand {
    public SetUsername (CalutronModel calutronModel, String commandString) {
        super(calutronModel, commandString);}
    @Override public void execute () {
        getCalutronModel().setSetting("USERNAME", System.console().readLine("Username: "));}}
