package org.neptunestation.calutron.commands;

import java.net.*;
import java.util.*;
import org.apache.olingo.odata2.api.edm.*;
import org.neptunestation.calutron.*;
import org.neptunestation.calutron.model.*;

public class Interpreter extends AbstractCommand {
    public Interpreter (String commandString) {
        super(commandString);}
    public Interpreter (CommandContext ctx, String commandString) {
        super(ctx, commandString);}
    protected String getEndPointName (String url) {
        URL u = null;
        try {u = new URL(url);} catch (MalformedURLException e) {return "[BAD URL]";}
        if (u.getPort()==80) return String.format("%s/%s", u.getHost(), u.getPath());
        return String.format("%s:%s/%s", u.getHost(), u.getPort(), u.getPath());}
    public String getPrompt () {
        return String.format("%s:%s@%s> ",
                             getContext().getSetting("user")==null ? "[username]" : getContext().getSetting("user"),
                             getContext().getSetting("password")==null ? "[password]" : "****",
                             getContext().getSetting("url")==null ? "[url]" : getEndPointName(getContext().getSetting("url"))).toUpperCase();}
    public void execute (String commandString) {
        String[] commandTokens = commandString.toUpperCase().trim().split("\\s+");
        execute(commandTokens);}
    public void execute (String... tokens) {
        getCommand(tokens).execute(getArguments(tokens));}
    @Override public void execute () {
        while (true) execute(System.console().readLine(getPrompt()).replaceAll("\\s+", " ").trim());}}
