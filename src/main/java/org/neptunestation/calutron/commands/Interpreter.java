package org.neptunestation.calutron.commands;

import java.net.*;
import java.util.*;
import org.apache.olingo.odata2.api.edm.*;
import org.neptunestation.calutron.model.*;

public class Interpreter extends AbstractCommand {
    public Interpreter (CalutronModel calutronModel, String commandString) {
        super(calutronModel, commandString);}
    protected String getEndPointName (String url) {
        URL u = null;
        try {u = new URL(url);} catch (MalformedURLException e) {return "[BAD URL]";}
        if (u.getPort()==80) return String.format("%s/%s", u.getHost(), u.getPath());
        return String.format("%s:%s/%s", u.getHost(), u.getPort(), u.getPath());}
    public String getPrompt () {
        return String.format("%s:%s@%s> ",
                             getCalutronModel().getSetting("USERNAME")==null ? "[username]" : getCalutronModel().getSetting("USERNAME"),
                             getCalutronModel().getSetting("PASSWORD")==null ? "[password]" : "****",
                             getCalutronModel().getSetting("SERVICE_URL")==null ? "[url]" : getEndPointName(getCalutronModel().getSetting("SERVICE_URL"))).toUpperCase();}
    @Override public void execute () {
        while (true) getCalutronModel().getCommand(System.console().readLine(getPrompt()).replaceAll("\\s+", " ").trim()).execute();}}
