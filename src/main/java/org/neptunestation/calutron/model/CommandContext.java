package org.neptunestation.calutron.model;

import java.util.*;

public interface CommandContext<T> {
    Command getSuperCommand ();
    T getState ();
    Properties getSettings ();}

