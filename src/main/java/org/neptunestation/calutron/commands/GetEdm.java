package org.neptunestation.calutron.commands;

import java.net.*;
import java.util.*;
import org.neptunestation.calutron.model.*;
import org.apache.olingo.odata2.api.edm.*;

public class GetEdm extends AbstractCommand {
    public GetEdm (CalutronModel calutronModel, String commandString) {super(calutronModel, commandString);}
    @Override public void execute () {
        if (getCalutronModel().getSetting("SERVICE_URL")==null) getCalutronModel().getConsole().printf("%s\n", "URL has not been set.");
        if (getCalutronModel().getSetting("USERNAME")==null) getCalutronModel().getConsole().printf("%s\n", "USERNAME has not been set.");
        if (getCalutronModel().getSetting("PASSWORD")==null) getCalutronModel().getConsole().printf("%s\n", "PASSWORD has not been set.");
        try {getCalutronModel().setEdm(getCalutronModel().readEdm(getCalutronModel().getSetting("SERVICE_URL"),
                                                              getCalutronModel().getSetting("USERNAME"),
                                                              getCalutronModel().getSetting("PASSWORD")));}
        catch (Throwable t) {try {getCalutronModel().getConsole().printf("%s\n", "Error performing operation");} catch (Throwable t2) {}}}}
