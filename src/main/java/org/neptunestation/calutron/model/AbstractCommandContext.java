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
        this.settings = new Properties(settings);}
    @Override public Command getSuperCommand () {
        return superCommand;}
    @Override public Object getState () {
        return state;}
    @Override public String getSetting (final String name) {
        return getSettings().getProperty(name);}
    @Override public Properties getSettings () {
        return settings;}
    @Override public void setSetting (final String name, final String value) {
        if (name==null) throw new NullArgumentException("name");
        if (value==null) throw new NullArgumentException("value");
        getSettings().setProperty(name, value);}}

