/**
 * Project Wonderland
 *
 * Copyright (c) 2004-2009, Sun Microsystems, Inc., All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * Sun designates this particular file as subject to the "Classpath"
 * exception as provided by Sun in the License file that accompanied
 * this code.
 */
package org.jdesktop.wonderland.modules.restsamplemodule.common;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.jdesktop.wonderland.common.cell.state.CellServerState;
import org.jdesktop.wonderland.common.cell.state.annotation.ServerState;

@XmlRootElement(name="shape-cell")
@ServerState
public class RESTSampleCellServerState extends CellServerState {

    @XmlElement(name="shape-type")
    private String shapeType = "BOX";

    @XmlElement(name="texture-uri")
    private String textureURI = null;

    public RESTSampleCellServerState() {
    }
    
    @XmlTransient public String getShapeType() { return this.shapeType; }
    public void setShapeType(String shapeType) { this.shapeType = shapeType; }
    @XmlTransient public String getTextureURI() { return this.textureURI; }
    public void setTextureURI(String textureURI) { this.textureURI = textureURI; }

    @Override
    public String getServerClassName() {
        return "org.jdesktop.wonderland.modules.restsamplemodule.server.RESTSampleCellMO";
    }
}
