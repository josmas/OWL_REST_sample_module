/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.restsamplemodule.client.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

/**
 *
 * @author jos
 */
public class RestGateway {

    private static final Logger LOGGER =
            Logger.getLogger(RestGateway.class.getName());

     /**
     * A GET REST request is sent to the external service. An xml reply is expected.
     * @param remoteRestURL the URL and data to be accessed.
     * @return String wrapping the xml information read from the remote server.
     */
    public static String restGETConnection(String remoteRestURL) {

        URL railsAppURL;
        StringBuffer xmlReply = new StringBuffer("");

        try {
            //Open a connection and read from the buffer
            railsAppURL = new URL(remoteRestURL);
            URLConnection railsAppCon = railsAppURL.openConnection();
            BufferedReader xmlReplyReader = new BufferedReader(
                    new InputStreamReader(railsAppCon.getInputStream()));

            String inputLine;
            while ((inputLine = xmlReplyReader.readLine()) != null) {
                System.out.println(inputLine);
                xmlReply.append(inputLine);
            }

            xmlReplyReader.close();

        } catch (MalformedURLException mue) { //TODO think about how to handle exceptions
            LOGGER.warning("Error : " + mue.getMessage());
        } catch (IOException ioe) {
            LOGGER.warning("Error : " + ioe.getMessage());
        }

        return xmlReply.toString();
    }

        /**
     * A POST REST request is sent to the remote server, to insert data in the database.
     * @param remoteRestURL
     * @param xmlMessage xml snippet to be inserted in the remote database
     * @return String wrapping the xml of the new inserted item
     * @throws Exception see TODO
     */
    public static String restPOSTConnection(String remoteRestURL, String xmlMessage) throws Exception {

        //TODO catch the exception; Could read the HTTP response code, that would be the return type.
        URL railsApp = new URL(remoteRestURL);
        URLConnection c = railsApp.openConnection();
        StringBuffer xmlReply = new StringBuffer("");

        //Setting the action to POST
        c.setDoOutput(true);
        if (c instanceof HttpURLConnection) {
            ((HttpURLConnection) c).setRequestMethod("POST");
        }
        c.setRequestProperty("Content-Type", "text/xml");

        OutputStreamWriter out = new OutputStreamWriter(c.getOutputStream());

        out.write(xmlMessage);
        out.flush();

        out.close();

        BufferedReader xmlReplyReader = new BufferedReader(new InputStreamReader(c.getInputStream()));

        String inputLine = null;
        while ((inputLine = xmlReplyReader.readLine()) != null) {
            xmlReply.append(inputLine);
        }
        xmlReplyReader.close();

        return xmlReply.toString();
    }

}
