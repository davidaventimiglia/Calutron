package org.neptunestation.calutron.commands;

import org.neptunestation.calutron.model.*;

public class SetUsername extends AbstractCommand {
    public SetUsername (Interpreter interpreter, String commandString) {super(interpreter, commandString);}
    @Override public void execute () {
        getInterpreter().setSetting("USERNAME", getInterpreter().getConsole().readLine("Username: "));}}
