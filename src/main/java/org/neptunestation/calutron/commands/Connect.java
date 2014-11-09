package org.neptunestation.calutron.commands;

import java.net.*;
import java.util.*;
import org.neptunestation.calutron.model.*;
import org.apache.olingo.odata2.api.edm.*;

public class Connect extends AbstractCommand {
    public Connect (Interpreter interpreter, String commandString) {super(interpreter, commandString);}
    @Override public void execute () {
        getInterpreter().getCommand("set username").execute();
        getInterpreter().getCommand("set password").execute();
        getInterpreter().getCommand("set url").execute();
        getInterpreter().getCommand("rehash").execute();}}

