package org.jdesktop.wonderland.modules.restsamplemodule.client.rest;

/**
 *
 * @author jos
 */
public interface RESTGateway {

    String createMessageToPost();

    String createURLToPostTo();

    /**
     * A GET REST request is sent to the external service. An xml reply is expected.
     * @param remoteRestURL the URL and data to be accessed.
     * @return String wrapping the xml information read from the remote server.
     */
    String restGETConnection(String remoteRestURL);

    /**
     * A POST REST request is sent to the remote server, to insert data in the database.
     * @param remoteRestURL
     * @param xmlMessage xml snippet to be inserted in the remote database
     * @return String wrapping the xml of the new inserted item
     * @throws Exception see TODO
     */
    String restPOSTConnection(String remoteRestURL, String xmlMessage) throws Exception;

}
