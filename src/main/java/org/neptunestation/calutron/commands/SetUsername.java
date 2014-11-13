package org.neptunestation.calutron.commands;

import org.neptunestation.calutron.*;
import org.neptunestation.calutron.model.*;

public class SetUsername extends AbstractCommand {
    public SetUsername (String commandString) {
        super(commandString);}
    @Override public void execute () {
        getContext().setSetting("user", System.console().readLine("Username: "));}}
