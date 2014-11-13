package org.neptunestation.calutron.commands;

import java.net.*;
import java.util.*;
import org.apache.olingo.odata2.api.edm.*;
import org.neptunestation.calutron.model.*;

public class Connect extends AbstractCommand {
    public Connect (String commandString) {
        super(commandString);}
    @Override public void execute () {
        getContext().getSuperCommand().getCommand("set username").execute();
        getContext().getSuperCommand().getCommand("set password").execute();
        getContext().getSuperCommand().getCommand("set url").execute();
        getContext().getSuperCommand().getCommand("rehash").execute();}}

