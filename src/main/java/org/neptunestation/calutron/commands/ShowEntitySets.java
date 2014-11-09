package org.neptunestation.calutron.commands;

import java.net.*;
import java.util.*;
import org.neptunestation.calutron.model.*;
import org.apache.olingo.odata2.api.edm.*;

public class ShowEntitySets extends AbstractCommand {
    public ShowEntitySets (Interpreter interpreter, String commandString) {super(interpreter, commandString);}
    @Override public void execute () {
        if (getInterpreter().getSetting("SERVICE_URL")==null) getInterpreter().getConsole().printf("%s\n", "URL has not been set.");
        if (getInterpreter().getSetting("USERNAME")==null) getInterpreter().getConsole().printf("%s\n", "USERNAME has not been set.");
        if (getInterpreter().getSetting("PASSWORD")==null) getInterpreter().getConsole().printf("%s\n", "PASSWORD has not been set.");
        SortedSet<String> names = new TreeSet<String>();
        try {
            for (EdmEntitySet e : getInterpreter().readEdm(getInterpreter().getSetting("SERVICE_URL"),
                                                           getInterpreter().getSetting("USERNAME"),
                                                           getInterpreter().getSetting("PASSWORD")).getEntitySets()) names.add(e.getName());
            for (String name : names) getInterpreter().getConsole().printf("%s\n", name);}
        catch (Throwable t) {try {getInterpreter().getConsole().printf("%s\n", "Error performing operation");} catch (Throwable t2) {}}}}
