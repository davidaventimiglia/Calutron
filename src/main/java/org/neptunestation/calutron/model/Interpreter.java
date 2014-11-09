package org.neptunestation.calutron.model;

import java.io.*;
import java.util.*;
import org.apache.olingo.odata2.api.edm.*;

public interface Interpreter extends ODataCommand {
    CommandMap getCommands ();
    Properties getSettings ();
    String getSetting (final String name);
    void setSetting (final String name, final String value);
    Console getConsole ();
    String getPrompt ();
    void executeCommand (final String name);
    void addCommands (final Command... commands);
    Command getCommand (final String name);
    Edm getEdm ();
    void setEdm (final Edm edm);
    void start ();}

