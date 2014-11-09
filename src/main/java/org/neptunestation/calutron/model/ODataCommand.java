package org.neptunestation.calutron.model;

import java.io.*;
import org.apache.olingo.odata2.api.commons.*;
import org.apache.olingo.odata2.api.edm.*;
import org.apache.olingo.odata2.api.ep.*;
import org.apache.olingo.odata2.api.exception.*;

public interface ODataCommand {
    Edm readEdm (String serviceUrl, String username, String password) throws IOException, ODataException;}
