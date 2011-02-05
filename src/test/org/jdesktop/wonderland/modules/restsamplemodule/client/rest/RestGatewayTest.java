package org.jdesktop.wonderland.modules.restsamplemodule.client.rest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jos
 */
public class RestGatewayTest {
    private RESTURLGateway gateway;
    
    @Before
    public void setUp() {
        gateway = new RESTURLGateway();
    }


    @Test
    public void testDoGet() {
        //TODO Isolate this test with a mockito fake!
        String getURL = "http://173.45.228.42:3000/users.xml";
        String response = gateway.restGETConnection(getURL);
        assertTrue(response.startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\"?><users"));
    }

    @Test
    public void testPOSTConnection() throws Exception {
        StringBuffer userString = new StringBuffer("");
        userString.append("<user>");
        userString.append("<first-name>Josmas</first-name>");
        userString.append("<last-name>mola mucho mas</last-name>");
        userString.append("</user>");
        assertTrue((gateway.restPOSTConnection("http://173.45.228.42:3000/users.xml", userString.toString()))
                .startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\"?><user>"));
    }

    //TODO can I test createMessageToPost by mocking LoginManager.getPrimary().getUsername() somehow?

    @Test
    public void testCreateURLToPostTo(){
        assertEquals("http://173.45.228.42:3000/users.xml", gateway.createURLToPostTo());
    }

}