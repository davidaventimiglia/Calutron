package org.neptunestation.calutron.commands;

import java.net.*;
import java.util.*;
import org.neptunestation.calutron.model.*;
import org.apache.olingo.odata2.api.edm.*;

public class ShowEntitySets extends AbstractCommand {
    public ShowEntitySets (CalutronModel calutronmodel, String commandString) {super(calutronmodel, commandString);}
    @Override public void execute () {
        if (getCalutronModel().getSetting("SERVICE_URL")==null) getCalutronModel().getConsole().printf("%s\n", "URL has not been set.");
        if (getCalutronModel().getSetting("USERNAME")==null) getCalutronModel().getConsole().printf("%s\n", "USERNAME has not been set.");
        if (getCalutronModel().getSetting("PASSWORD")==null) getCalutronModel().getConsole().printf("%s\n", "PASSWORD has not been set.");
        SortedSet<String> names = new TreeSet<String>();
        try {
            for (EdmEntitySet e : getCalutronModel().getEdm().getEntitySets()) names.add(e.getName());
            for (String name : names) getCalutronModel().getConsole().printf("%s\n", name);}
        catch (Throwable t) {try {getCalutronModel().getConsole().printf("%s\n", "Error performing operation");} catch (Throwable t2) {}}}}
