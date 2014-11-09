package org.neptunestation.calutron.api;

import java.util.*;
import org.neptunestation.calutron.exceptions.*;

public class CommandMap extends TreeMap<String, Command> {
    public void add (final Command command) {
        if (command==null) throw new NullArgumentException("command");
        super.put(command.getCommandString(), command);}
    public void add (final Command... commands) {
        if (commands==null) throw new NullArgumentException("commands");
        for (int i=0; i<commands.length; i++) add(commands[i]);}}

