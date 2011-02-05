package org.jdesktop.wonderland.modules.restsamplemodule.common;

import org.jdesktop.wonderland.common.cell.state.CellClientState;

public class RESTSampleCellClientState extends CellClientState {
    private String shapeType = null;
    private String textureURI = null;

    public RESTSampleCellClientState() {
    }
    
    public String getShapeType() {
        return shapeType;
    }

    public void setShapeType(String shapeType) {
        this.shapeType = shapeType;
    }

    public String getTextureURI() {
        return textureURI;
    }

    public void setTextureURI(String textureURI) {
        this.textureURI = textureURI;
    }
}
