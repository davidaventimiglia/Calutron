package org.neptunestation.calutron.model;

import java.util.*;

public class AbstractCommandContext implements CommandContext {
    protected Command superCommand = null;
    protected Object state = null;
    protected Properties settings = null;

    public AbstractCommandContext (final Command superCommand, final Object state, final Properties settings) {
        // if (superCommand==null) throw new NullArgumentException("superCommand");
        if (state==null) throw new NullArgumentException("state");
        if (settings==null) throw new NullArgumentException("settings");
        this.superCommand = superCommand;
        this.state = state;
        this.settings = settings;}
    @Override public Command getSuperCommand () {
        return superCommand;}
    @Override public Object getState () {
        return state;}
    @Override public Properties getSettings () {
        return settings;}}
