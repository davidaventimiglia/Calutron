package org.neptunestation.calutron.commands;

import org.neptunestation.calutron.model.*;

public class Help extends AbstractCommand {
    public Help (Interpreter interpreter, String commandString) {super(interpreter, commandString);}
    @Override public void execute () {
        for (String s : getInterpreter().getCommands().keySet()) getInterpreter().getConsole().printf("%s\n", s);}}
