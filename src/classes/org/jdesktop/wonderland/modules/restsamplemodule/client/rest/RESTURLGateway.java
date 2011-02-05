package org.jdesktop.wonderland.modules.restsamplemodule.client.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import org.jdesktop.wonderland.client.login.LoginManager;

/**
 *
 * @author jos
 */
public class RESTURLGateway implements RESTGateway {

    private static final Logger LOGGER =
            Logger.getLogger(RESTURLGateway.class.getName());
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(
            "org.jdesktop.wonderland.modules.restsamplemodule.client.Bundle");

     /**
     * A GET REST request is sent to the external service. An xml reply is expected.
     * @param remoteRestURL the URL and data to be accessed.
     * @return String wrapping the xml information read from the remote server.
     */
    public String restGETConnection(String remoteRestURL) throws MalformedURLException, IOException {

        URL railsAppURL;
        StringBuffer xmlReply = new StringBuffer("");

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

        return xmlReply.toString();
    }

     /**
     * A POST REST request is sent to the remote server, to insert data in the database.
     * @param remoteRestURL
     * @param xmlMessage xml snippet to be inserted in the remote database
     * @return String wrapping the xml of the new inserted item
     * @throws Exception see TODO
     */
    public String restPOSTConnection(String remoteRestURL, String xmlMessage) throws MalformedURLException, IOException {

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

    public  String createMessageToPost() {
        StringBuffer userString = new StringBuffer("");
        userString.append("<user>");
        userString.append("<first-name>" + LoginManager.getPrimary().getUsername() + "</first-name>");
        userString.append("<last-name>" + LoginManager.getPrimary().getServerNameAndPort() + "</last-name>");
        userString.append("</user>");
        return userString.toString();
    }

    public  String createURLToPostTo(String target) {
        return BUNDLE.getString("URL_To_Connect_To") + target;
    }

}
