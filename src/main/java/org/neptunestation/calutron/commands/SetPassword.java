package org.neptunestation.calutron.commands;

import org.neptunestation.calutron.model.*;

public class SetPassword extends AbstractCommand {
    public SetPassword (Interpreter interpreter, String commandString) {super(interpreter, commandString);}
    @Override public void execute () {
        getInterpreter().setSetting("PASSWORD", new String(getInterpreter().getConsole().readPassword("%s", "Password:")));}}
