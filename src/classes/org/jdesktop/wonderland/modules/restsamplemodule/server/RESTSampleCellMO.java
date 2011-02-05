package org.jdesktop.wonderland.modules.restsamplemodule.server;

import org.jdesktop.wonderland.common.cell.ClientCapabilities;
import org.jdesktop.wonderland.common.cell.state.CellClientState;
import org.jdesktop.wonderland.common.cell.state.CellServerState;
import org.jdesktop.wonderland.modules.restsamplemodule.common.RESTSampleCellClientState;
import org.jdesktop.wonderland.modules.restsamplemodule.common.RESTSampleCellServerState;
import org.jdesktop.wonderland.server.cell.CellMO;
import org.jdesktop.wonderland.server.comms.WonderlandClientID;

/**
 *
 * @author jos
 */
public class RESTSampleCellMO extends CellMO {

    private String shapeType = null;
    private String textureURI = null;

    public RESTSampleCellMO() {
    }

    @Override
    public String getClientCellClassName(WonderlandClientID clientID, ClientCapabilities capabilities) {
         return "org.jdesktop.wonderland.modules.restsamplemodule.client.RESTSampleCell";
    }

    @Override
    public CellClientState getClientState(CellClientState state, WonderlandClientID clientID, ClientCapabilities capabilities) {
        if (state == null) {
            state = new RESTSampleCellClientState();
        }
        ((RESTSampleCellClientState)state).setShapeType(shapeType);
        ((RESTSampleCellClientState)state).setTextureURI(textureURI);
        return super.getClientState(state, clientID, capabilities);
    }

    @Override
    public CellServerState getServerState(CellServerState state) {
        if (state == null) {
            state = new RESTSampleCellServerState();
        }
        ((RESTSampleCellServerState)state).setShapeType(shapeType);
        ((RESTSampleCellServerState)state).setTextureURI(textureURI);
        return super.getServerState(state);
    }

    @Override
    public void setServerState(CellServerState state) {
        super.setServerState(state);
        this.shapeType = ((RESTSampleCellServerState)state).getShapeType();
        this.textureURI = ((RESTSampleCellServerState)state).getTextureURI();
    }
}
