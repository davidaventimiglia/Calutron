package org.neptunestation.calutron.model;

import java.util.*;

public class CommandSet extends HashSet<Command> {
    public Command get (final String[] tokens) {
        if (tokens==null) throw new NullArgumentException("tokens");
        for (int i=0; i<=tokens.length; i++) for (Command c: this) if (c!=null) if (Arrays.asList(tokens).subList(0, i).toString().equalsIgnoreCase(Arrays.asList(c.getCommandTokens()).toString())) return c;
        return null;}
    public String[] getArguments (final String[] tokens) {
        if (tokens==null) throw new NullArgumentException("tokens");
        for (int i=0; i<=tokens.length; i++) for (Command c: this) if (c!=null) if (Arrays.asList(tokens).subList(0, i).toString().equalsIgnoreCase(Arrays.asList(c.getCommandTokens()).toString())) return Arrays.asList(tokens).subList(i, tokens.length).toArray(new String[]{});
        return tokens;}
    public void add (final Command... commands) {
        if (commands==null) throw new NullArgumentException("commands");
        for (int i=0; i<commands.length; i++) if (commands[i].getContext()==null) throw new IllegalStateException(String.format("command[%s] context has not been set", i));
        for (int i=0; i<commands.length; i++) add(commands[i]);}}
