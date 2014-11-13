package org.neptunestation.calutron.model;

import java.util.*;

public interface CommandContext {
    Command getSuperCommand ();
    Object getState ();
    String getSetting (final String name);
    Properties getSettings ();
    void setSetting (final String name, final String value);}

