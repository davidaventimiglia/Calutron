package org.neptunestation.calutron.commands;

import java.net.*;
import org.neptunestation.calutron.model.*;

public class SetUrl extends AbstractCommand {
    public SetUrl (CalutronModel calutronModel, String commandString) {
        super(calutronModel, commandString);}
    @Override public void execute () {
        try {getCalutronModel().setSetting("SERVICE_URL", new URL(System.console().readLine("Service URL: ")).toExternalForm());}
        catch (MalformedURLException e) {System.console().printf("%s\n", "Invalid URL");}}}
