package org.jdesktop.wonderland.modules.restsamplemodule.client;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.wonderland.common.cell.state.CellClientState;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.client.cell.Cell.RendererType;
import org.jdesktop.wonderland.client.cell.CellCache;
import org.jdesktop.wonderland.client.cell.CellRenderer;
import org.jdesktop.wonderland.client.cell.annotation.UsesCellComponent;
import org.jdesktop.wonderland.client.contextmenu.ContextMenuActionListener;
import org.jdesktop.wonderland.client.contextmenu.ContextMenuItem;
import org.jdesktop.wonderland.client.contextmenu.ContextMenuItemEvent;
import org.jdesktop.wonderland.client.contextmenu.SimpleContextMenuItem;
import org.jdesktop.wonderland.client.contextmenu.cell.ContextMenuComponent;
import org.jdesktop.wonderland.client.contextmenu.spi.ContextMenuFactorySPI;
import org.jdesktop.wonderland.client.scenemanager.event.ContextEvent;
import org.jdesktop.wonderland.common.cell.CellID;
import org.jdesktop.wonderland.common.cell.CellStatus;
import org.jdesktop.wonderland.modules.restsamplemodule.common.RESTSampleCellClientState;
import org.jdesktop.wonderland.modules.restsamplemodule.client.rest.RESTURLGateway;

public class RESTSampleCell extends Cell {
    
    private String shapeType = null;
    private String textureURI = null;
    private RESTSampleCellRenderer renderer = null;

    @UsesCellComponent
    private ContextMenuComponent contextComp = null;
    private ContextMenuFactorySPI menuFactory = null;
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org.jdesktop.wonderland.modules.restsamplemodule.client.Bundle");

    public RESTSampleCell(CellID cellID, CellCache cellCache) {
        super(cellID, cellCache);
    }

    @Override
    public void setClientState(CellClientState state) {
        super.setClientState(state);
        this.shapeType = ((RESTSampleCellClientState)state).getShapeType();
        this.textureURI = ((RESTSampleCellClientState)state).getTextureURI();
    }

    public String getShapeType() {
        return this.shapeType;
    }

    public String getTextureURI() {
        return textureURI;
    }

    @Override
    protected CellRenderer createCellRenderer(RendererType rendererType) {
        if (rendererType == RendererType.RENDERER_JME) {
            this.renderer = new RESTSampleCellRenderer(this);
            return this.renderer;
        }
        else {
            return super.createCellRenderer(rendererType);
        }
    }

        @Override
    public void setStatus(CellStatus status, boolean increasing) {
        super.setStatus(status, increasing);

        if (status == CellStatus.INACTIVE && increasing == false) {

            if (menuFactory != null) {
                contextComp.removeContextMenuFactory(menuFactory);
                menuFactory = null;
            }
        }
        else if (status == CellStatus.RENDERING && increasing == true) {
            if (menuFactory == null) {
                final MenuItemListener l = new MenuItemListener();
                menuFactory = new ContextMenuFactorySPI() {
                    public ContextMenuItem[] getContextMenuItems(ContextEvent event) {
                        return new ContextMenuItem[]{
                                    new SimpleContextMenuItem(BUNDLE.getString("Register_Me"), l)
                                };
                    }
                };
                contextComp.addContextMenuFactory(menuFactory);
            }
        }
    }

    class MenuItemListener implements ContextMenuActionListener {
        public void actionPerformed(ContextMenuItemEvent event) {
            //TODO this instead of changing shape, will have to call the post action
            shapeType = (shapeType.equals("BOX") == true) ? "SPHERE" : "BOX";
            //Can update shape change to some kind of 'Registering' animation to use as feedback?
            renderer.updateShape();
            RESTURLGateway gateway = new RESTURLGateway();
            String URLToPostTO = gateway.createURLToPostTo("users.xml");
            String userString = gateway.createMessageToPost();
            try {
                gateway.restPOSTConnection(URLToPostTO, userString.toString());
            } catch (Exception ex) { //TODO if an exception happens, have to tell the user!
                //TODO could be great to have a Call to EZScript create HUD with a message (or can it be done
                //with huddialog?)
                Logger.getLogger(RESTSampleCell.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
