package org.neptunestation.calutron.commands;

import java.net.*;
import org.neptunestation.calutron.*;
import org.neptunestation.calutron.model.*;

public class SetUrl extends AbstractCommand {
    public SetUrl (String commandString) {
        super(commandString);}
    public SetUrl (CommandContext ctx, String commandString) {
        super(ctx, commandString);}
    @Override public void execute () {
        super.execute();
        try {getContext().setSetting("url", new URL(System.console().readLine("Service URL: ")).toExternalForm());}
        catch (MalformedURLException e) {System.console().printf("%s\n", "Invalid URL");}}}
