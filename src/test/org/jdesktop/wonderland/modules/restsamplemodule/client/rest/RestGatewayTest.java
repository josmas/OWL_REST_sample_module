package org.jdesktop.wonderland.modules.restsamplemodule.client.rest;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jos
 */
public class RestGatewayTest {
    
    @Before
    public void setUp() {
    }


    @Test
    public void testDoGet() {
        //TODO Isolate this test with a mockito fake!
        String getURL = "http://173.45.228.42:3000/users.xml";
        String response = RestGateway.restGETConnection(getURL);
        assertTrue(response.startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\"?><users"));
    }

    @Test
    public void testPOSTConnection() throws Exception {
        StringBuffer userString = new StringBuffer("");
        userString.append("<user>");
        userString.append("<first-name>Josmas</first-name>");
        userString.append("<last-name>mola mucho mas</last-name>");
        userString.append("</user>");
        assertTrue((RestGateway.restPOSTConnection("http://173.45.228.42:3000/users.xml", userString.toString()))
                .startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\"?><user>"));

    }

}