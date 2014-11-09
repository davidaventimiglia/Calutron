package org.neptunestation.calutron.commands;

import java.net.*;
import java.util.*;
import org.neptunestation.calutron.model.*;
import org.apache.olingo.odata2.api.edm.*;

public class GetEdm extends AbstractCommand {
    public GetEdm (Interpreter interpreter, String commandString) {super(interpreter, commandString);}
    @Override public void execute () {
        if (getInterpreter().getSetting("SERVICE_URL")==null) getInterpreter().getConsole().printf("%s\n", "URL has not been set.");
        if (getInterpreter().getSetting("USERNAME")==null) getInterpreter().getConsole().printf("%s\n", "USERNAME has not been set.");
        if (getInterpreter().getSetting("PASSWORD")==null) getInterpreter().getConsole().printf("%s\n", "PASSWORD has not been set.");
        try {getInterpreter().setEdm(getInterpreter().readEdm(getInterpreter().getSetting("SERVICE_URL"),
                                                              getInterpreter().getSetting("USERNAME"),
                                                              getInterpreter().getSetting("PASSWORD")));}
        catch (Throwable t) {try {getInterpreter().getConsole().printf("%s\n", "Error performing operation");} catch (Throwable t2) {}}}}
