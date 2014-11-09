package org.neptunestation.calutron.commands;

import java.net.*;
import org.neptunestation.calutron.model.*;

public class SetUrl extends AbstractCommand {
    public SetUrl (Interpreter interpreter, String commandString) {super(interpreter, commandString);}
    @Override public void execute () {
        try {getInterpreter().setSetting("SERVICE_URL", new URL(getInterpreter().getConsole().readLine("Service URL: ")).toExternalForm());}
        catch (MalformedURLException e) {getInterpreter().getConsole().printf("%s\n", "Invalid URL");}}}
