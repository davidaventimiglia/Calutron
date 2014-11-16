package org.neptunestation.calutron.commands;

import java.net.*;
import java.util.*;
import org.apache.olingo.odata2.api.edm.*;
import org.neptunestation.calutron.*;
import org.neptunestation.calutron.model.*;

public class ListEntities extends AbstractCommand {
    public ListEntities (String commandString) {
        super(commandString);}
    @Override public void execute () {
        System.console().printf("Missing argument:  %s\n", "entity name");}
    @Override public void execute (String... args) {
        if (args==null) throw new NullArgumentException("args");
        if (args.length>0) System.console().printf("Listing entity:  %s\n", args[0]);
        if (args.length==0) execute();}}
