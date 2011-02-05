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
package org.jdesktop.wonderland.modules.restsamplemodule.client;

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
import org.jdesktop.wonderland.client.login.LoginManager;
import org.jdesktop.wonderland.client.scenemanager.event.ContextEvent;
import org.jdesktop.wonderland.common.cell.CellID;
import org.jdesktop.wonderland.common.cell.CellStatus;
import org.jdesktop.wonderland.modules.restsamplemodule.common.RESTSampleCellClientState;
import org.jdesktop.wonderland.modules.restsamplemodule.client.rest.RestGateway;

public class RESTSampleCell extends Cell {
    
    private String shapeType = null;
    private String textureURI = null;
    private RESTSampleCellRenderer renderer = null;

    @UsesCellComponent
    private ContextMenuComponent contextComp = null;
    private ContextMenuFactorySPI menuFactory = null;

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
                                    new SimpleContextMenuItem("Register me", l)
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
            renderer.updateShape();
            String URLToPostTO = createURLToPostTo();
            String userString = createMessageToPost();
            try {
                RestGateway.restPOSTConnection(URLToPostTO, userString.toString());
            } catch (Exception ex) { //TODO if an exception happens, have to tell the user!
                //TODO could be great to have a Call to EZScript create HUD with a message (or can it be done
                //with huddialog?)
                Logger.getLogger(RESTSampleCell.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        private String createMessageToPost() {
            StringBuffer userString = new StringBuffer("");
            userString.append("<user>");
            userString.append("<first-name>" + LoginManager.getPrimary().getUsername() + "</first-name>");
            userString.append("<last-name>" + LoginManager.getPrimary().getServerNameAndPort() + "</last-name>");
            userString.append("</user>");
            return userString.toString();
        }

        private String createURLToPostTo() {
            //TODO the first part of this should be read from a properties file
            return "http://173.45.228.42:3000/users.xml";
        }
    }
}
