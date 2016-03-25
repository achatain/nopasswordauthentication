package com.github.achatain.nopasswordauthentication.admin;

import com.github.achatain.nopasswordauthentication.utils.AppSettings;
import com.github.achatain.nopasswordauthentication.utils.FakeHttpServletRequest;
import com.github.achatain.nopasswordauthentication.utils.FakeHttpServletResponse;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;

public class AdminServletTest {

    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
            new LocalDatastoreServiceTestConfig(),
            new LocalMemcacheServiceTestConfig());

    private FakeHttpServletRequest req;
    private FakeHttpServletResponse resp;

    private AdminServlet adminServlet;

    @Before
    public void setUp() {
        helper.setUp();
        req = new FakeHttpServletRequest();
        resp = new FakeHttpServletResponse();
        adminServlet = new AdminServlet();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void shouldResetSettings() throws Exception {
        req.addParameter(AdminServlet.ACTION, AdminServlet.ACTION_RESET);
        adminServlet.doGet(req, resp);
        assertEquals(HttpServletResponse.SC_OK, resp.getStatus());
        assertEquals("{\"action\":\"reset\"}", resp.getContentAsString());
        assertEquals("change_me", AppSettings.getSendGridApiKey());
    }

    @Test
    public void shouldDoNothingForUnknownAction() throws Exception {
        req.addParameter(AdminServlet.ACTION, "");
        adminServlet.doGet(req, resp);
        assertEquals(HttpServletResponse.SC_OK, resp.getStatus());
        assertEquals("{\"action\":\"unknown\"}", resp.getContentAsString());
    }

    @Test
    public void shouldDoNothingForMissingAction() throws Exception {
        adminServlet.doGet(req, resp);
        assertEquals(200, resp.getStatus());
        assertEquals("{\"action\":\"unknown\"}", resp.getContentAsString());
    }
}
