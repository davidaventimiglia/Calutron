package org.neptunestation.calutron.model;

import java.util.*;

public class CommandSet extends HashSet<Command> {
    public Command get (final String commandString) {
        if (commandString==null) throw new NullArgumentException("commandString");
        for (Command c : this) if (c!=null) if (commandString.equalsIgnoreCase(c.getCommandString())) return c;
        return null;}
    public void add (final Command... commands) {
        if (commands==null) throw new NullArgumentException("commands");
        for (int i=0; i<commands.length; i++) add(commands[i]);}}
