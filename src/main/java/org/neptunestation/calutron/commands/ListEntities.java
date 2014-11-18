package org.neptunestation.calutron.commands;

import java.io.*;
import java.net.*;
import java.util.*;
import org.apache.olingo.odata2.api.edm.*;
import org.apache.olingo.odata2.api.exception.*;
import org.neptunestation.calutron.*;
import org.neptunestation.calutron.model.*;

public class ListEntities extends AbstractCommand {
    public ListEntities (String commandString) {
        super(commandString);}
    @Override public void execute () {
        System.console().printf("Missing argument:  %s\n", "entity name");}
    private void listEntitySet (String name) throws IOException, EdmException, ODataException {
        Calutron cal = (Calutron)getContext().getState();
        Edm edm = cal.getEdm();
        EdmEntityContainer container = edm.getEntityContainer("public");
        List<EdmEntitySet> entitySets = edm.getEntitySets();
        for (EdmEntitySet e : entitySets)
            if (e.getName().equals("person"))
                System.console().printf("%s\n", cal.readFeed(edm, getContext().getSetting("url"), container, e, getContext().getSetting("user"), getContext().getSetting("password")));}
    @Override public void execute (String... args) {
        if (args==null) throw new NullArgumentException("args");
        if (args.length>0) try {listEntitySet(args[0]);} catch (Throwable t) {t.printStackTrace(System.err);}
        if (args.length==0) execute();}}
