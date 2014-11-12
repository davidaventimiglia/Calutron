package org.neptunestation.calutron.commands;

import java.net.*;
import java.util.*;
import org.apache.olingo.odata2.api.edm.*;
import org.neptunestation.calutron.*;
import org.neptunestation.calutron.model.*;

public class Interpreter extends AbstractCommand {
    public Interpreter (CommandContext ctx, String commandString) {
        super(ctx, commandString);}
    protected String getEndPointName (String url) {
        URL u = null;
        try {u = new URL(url);} catch (MalformedURLException e) {return "[BAD URL]";}
        if (u.getPort()==80) return String.format("%s/%s", u.getHost(), u.getPath());
        return String.format("%s:%s/%s", u.getHost(), u.getPort(), u.getPath());}
    public String getPrompt () {
        return String.format("%s:%s@%s> ",
                             ((Calutron)getContext().getState()).getSetting("USERNAME")==null ? "[username]" : ((Calutron)getContext().getState()).getSetting("USERNAME"),
                             ((Calutron)getContext().getState()).getSetting("PASSWORD")==null ? "[password]" : "****",
                             ((Calutron)getContext().getState()).getSetting("SERVICE_URL")==null ? "[url]" : getEndPointName(((Calutron)getContext().getState()).getSetting("SERVICE_URL"))).toUpperCase();}
    @Override public void execute () {
        while (true) getCommand(System.console().readLine(getPrompt()).replaceAll("\\s+", " ").trim()).execute();}}
