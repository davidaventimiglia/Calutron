package org.neptunestation.calutron.commands;

import org.neptunestation.calutron.model.*;

public class Quit extends AbstractCommand {
    public Quit (CommandContext ctx, String commandString) {
        super(ctx, commandString);}
    @Override public void execute () {
        super.execute();
        System.exit(0);}}
