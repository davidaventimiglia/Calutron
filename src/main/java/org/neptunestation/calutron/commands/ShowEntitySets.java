package org.neptunestation.calutron.commands;

import java.net.*;
import java.util.*;
import org.apache.olingo.odata2.api.edm.*;
import org.neptunestation.calutron.*;
import org.neptunestation.calutron.model.*;

public class ShowEntitySets extends AbstractCommand {
    public ShowEntitySets (String commandString) {
        super(commandString);}
    public ShowEntitySets (CommandContext ctx, String commandString) {
        super(ctx, commandString);}
    @Override public void execute () {
        super.execute();
        if (getContext().getSetting("url")==null) System.console().printf("%s\n", "URL has not been set.");
        if (getContext().getSetting("user")==null) System.console().printf("%s\n", "user has not been set.");
        if (getContext().getSetting("password")==null) System.console().printf("%s\n", "password has not been set.");
        SortedSet<String> names = new TreeSet<String>();
        try {
            for (EdmEntitySet e : ((Calutron)getContext().getState()).getEdm().getEntitySets()) names.add(e.getName());
            for (String name : names) System.console().printf("%s\n", name);}
        catch (Throwable t) {try {System.console().printf("%s\n", "Error performing operation");} catch (Throwable t2) {}}}}
