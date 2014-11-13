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
        super.execute();
        if (getContext().getSuperCommand().getContext().getSetting("SERVICE_URL")==null) System.console().printf("%s\n", "URL has not been set.");
        if (getContext().getSuperCommand().getContext().getSetting("USERNAME")==null) System.console().printf("%s\n", "USERNAME has not been set.");
        if (getContext().getSuperCommand().getContext().getSetting("PASSWORD")==null) System.console().printf("%s\n", "PASSWORD has not been set.");
        try {((Calutron)getContext().getState()).setEdm(((Calutron)getContext().getState()).readEdm(getContext().getSuperCommand().getContext().getSetting("SERVICE_URL"),
                                                                                                    getContext().getSuperCommand().getContext().getSetting("USERNAME"),
                                                                                                    getContext().getSuperCommand().getContext().getSetting("PASSWORD")));}
        catch (Throwable t) {try {System.console().printf("%s\n", "Error performing operation");} catch (Throwable t2) {}}}}
