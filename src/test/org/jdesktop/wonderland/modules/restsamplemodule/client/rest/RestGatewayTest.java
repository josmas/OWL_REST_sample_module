package org.jdesktop.wonderland.modules.restsamplemodule.client.rest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author jos
 */
public class RestGatewayTest {
    private RESTGateway mockedGateway;
    RESTURLGateway gateway;
    private String getURL = "http://173.45.228.42:3000/users.xml";
    
    @Before
    public void setUp() {
        //Different test can use a mock or a real gateway
        mockedGateway = mock(RESTGateway.class);
        gateway = new RESTURLGateway();
    }


    @Test
    public void testrestGETConnection() throws Exception {

        when(mockedGateway.restGETConnection(getURL))
                .thenReturn("<?xml version=\"1.0\" encoding=\"UTF-8\"?><users");

        String response = mockedGateway.restGETConnection(getURL);
        assertTrue(response.startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\"?><users"));

        verify(mockedGateway).restGETConnection(getURL);
        //I don't understand very well this; if I stub a return, then asserting
        //does not make much sense, so I guess here we want to verify that the
        //call was done? Have to read more about mocks. Same for POST
    }

    @Test
    public void testExceptionGETConnection(){
        try{
            gateway.restGETConnection("malformedURL");
            fail("Exception expected");
        }
        catch (Exception e){
            //ignore
        }
    }

    @Test
    public void testPOSTConnection() throws Exception {
        StringBuffer userString = new StringBuffer("");
        userString.append("<user>");
        userString.append("<first-name>Josmas</first-name>");
        userString.append("<last-name>mola mucho mas</last-name>");
        userString.append("</user>");

        when(mockedGateway.restPOSTConnection(getURL, userString.toString()))
                .thenReturn("</user>");

        mockedGateway.restPOSTConnection(getURL, userString.toString());
        
        verify(mockedGateway).restPOSTConnection(getURL, userString.toString());
    }

    @Test
    public void testExceptionPOSTConnection(){
        try{
            gateway.restPOSTConnection("malformedURL", "");
            fail("Exception expected");
        }
        catch (Exception e){
            //ignore
        }
    }

    //TODO can I test createMessageToPost by mocking LoginManager.getPrimary().getUsername() somehow?

    @Test
    public void testCreateURLToPostTo(){
        assertEquals(getURL, gateway.createURLToPostTo("users.xml"));
    }

}