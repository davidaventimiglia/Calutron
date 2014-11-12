package org.neptunestation.calutron.commands;

import java.net.*;
import java.util.*;
import org.apache.olingo.odata2.api.edm.*;
import org.neptunestation.calutron.*;
import org.neptunestation.calutron.model.*;

public class GetEdm extends AbstractCommand {
    public GetEdm (CommandContext ctx, String commandString) {
        super(ctx, commandString);}
    @Override public void execute () {
        if (((Calutron)getContext().getState()).getSetting("SERVICE_URL")==null) System.console().printf("%s\n", "URL has not been set.");
        if (((Calutron)getContext().getState()).getSetting("USERNAME")==null) System.console().printf("%s\n", "USERNAME has not been set.");
        if (((Calutron)getContext().getState()).getSetting("PASSWORD")==null) System.console().printf("%s\n", "PASSWORD has not been set.");
        try {((Calutron)getContext().getState()).setEdm(((Calutron)getContext().getState()).readEdm(((Calutron)getContext().getState()).getSetting("SERVICE_URL"),
                                                              ((Calutron)getContext().getState()).getSetting("USERNAME"),
                                                              ((Calutron)getContext().getState()).getSetting("PASSWORD")));}
        catch (Throwable t) {try {System.console().printf("%s\n", "Error performing operation");} catch (Throwable t2) {}}}}
