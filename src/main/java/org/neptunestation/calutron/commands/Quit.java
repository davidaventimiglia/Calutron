package org.neptunestation.calutron.commands;

import org.neptunestation.calutron.model.*;

public class Quit extends AbstractCommand {
    public Quit (Interpreter interpreter, String commandString) {super(interpreter, commandString);}
    @Override public void execute () {System.exit(0);}}
