package org.neptunestation.calutron.commands;

import java.net.*;
import java.util.*;
import org.neptunestation.calutron.model.*;
import org.apache.olingo.odata2.api.edm.*;

public class ShowEntitySets extends AbstractCommand {
    public ShowEntitySets (CalutronModel calutronModel, String commandString) {
        super(calutronModel, commandString);}
    @Override public void execute () {
        if (getCalutronModel().getSetting("SERVICE_URL")==null) System.console().printf("%s\n", "URL has not been set.");
        if (getCalutronModel().getSetting("USERNAME")==null) System.console().printf("%s\n", "USERNAME has not been set.");
        if (getCalutronModel().getSetting("PASSWORD")==null) System.console().printf("%s\n", "PASSWORD has not been set.");
        SortedSet<String> names = new TreeSet<String>();
        try {
            for (EdmEntitySet e : getCalutronModel().getEdm().getEntitySets()) names.add(e.getName());
            for (String name : names) System.console().printf("%s\n", name);}
        catch (Throwable t) {try {System.console().printf("%s\n", "Error performing operation");} catch (Throwable t2) {}}}}
