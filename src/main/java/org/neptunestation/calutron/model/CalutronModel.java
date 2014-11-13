package org.neptunestation.calutron.model;

import org.apache.olingo.odata2.api.edm.*;

public interface CalutronModel extends ODataModel {
    Edm getEdm ();
    void setEdm (final Edm edm);}
