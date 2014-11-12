package org.neptunestation.calutron.commands;

import java.net.*;
import java.util.*;
import org.neptunestation.calutron.model.*;
import org.apache.olingo.odata2.api.edm.*;

public class Connect extends AbstractCommand {
    public Connect (CalutronModel calutronModel, String commandString) {
        super(calutronModel, commandString);}
    @Override public void execute () {
        getContext().getCommand("set username").execute();
        getContext().getCommand("set password").execute();
        getContext().getCommand("set url").execute();
        getContext().getCommand("rehash").execute();}}

