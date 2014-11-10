package org.neptunestation.calutron.commands;

import java.net.*;
import java.util.*;
import org.neptunestation.calutron.model.*;
import org.apache.olingo.odata2.api.edm.*;

public class Connect extends AbstractCommand {
    public Connect (CalutronModel calutronModel, String commandString) {super(calutronModel, commandString);}
    @Override public void execute () {
        getCalutronModel().getCommand("set username").execute();
        getCalutronModel().getCommand("set password").execute();
        getCalutronModel().getCommand("set url").execute();
        getCalutronModel().getCommand("rehash").execute();}}

