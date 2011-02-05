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
