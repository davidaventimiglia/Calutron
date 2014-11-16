package org.neptunestation.calutron.model;

import java.io.*;
import org.apache.olingo.odata2.api.commons.*;
import org.apache.olingo.odata2.api.edm.*;
import org.apache.olingo.odata2.api.ep.*;
import org.apache.olingo.odata2.api.ep.feed.*;
import org.apache.olingo.odata2.api.exception.*;

public interface ODataModel {
    ODataFeed readFeed (Edm edm, String serviceUri, EdmEntityContainer entityContainer, EdmEntitySet entitySet, final String username, final String password) throws IOException, ODataException;
    Edm readEdm (String serviceUrl, String username, String password) throws IOException, ODataException;}
