package org.neptunestation.calutron.commands;

import org.neptunestation.calutron.model.*;

public class Quit extends AbstractCommand {
    public Quit (String commandString) {
        super(commandString);}
    @Override public void execute () {
        System.exit(0);}}
