package org.neptunestation.calutron.model;

import java.io.*;
import java.util.*;
import org.apache.olingo.odata2.api.edm.*;

public interface CalutronModel extends ODataModel {
    CommandSet getCommands ();
    Properties getSettings ();
    String getSetting (final String name);
    void setSetting (final String name, final String value);
    // Console getConsole ();
    // String getPrompt ();
    void addCommands (final Command... commands);
    Command getCommand (final String name);
    Edm getEdm ();
    void setEdm (final Edm edm);}
