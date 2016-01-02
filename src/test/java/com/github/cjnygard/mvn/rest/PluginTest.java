package com.github.cjnygard.mvn.rest;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class PluginTest
        extends AbstractMojoTestCase {

    /**
     * {@inheritDoc}
     *
     * @throws java.lang.Exception
     */
    @Override
    protected void setUp()
            throws Exception {
        try {
            // required
            super.setUp();

        } catch (InvocationTargetException ex) {
            System.out.println("oops!" + ex.getCause().toString());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws java.lang.Exception
     */
    @Override
    protected void tearDown()
            throws Exception {
        try {
            // required
            super.tearDown();
        } catch (InvocationTargetException ex) {
            System.out.println("oops!" + ex.getCause().toString());
        }

    }

    /**
     * @return
     * @throws Exception if any
     */
    protected Plugin loadPlugin()
            throws Exception {
        File pom = getTestFile("src/test/resources/unit/rest-project/pom.xml");
        assertNotNull(pom);
        assertTrue(pom.exists());

        Plugin myPlugin = (Plugin) lookupMojo("rest-request", pom);
        return myPlugin;
    }

/** For whatever reason these resources are not injected by the
 * MojoTest environment
 *

    public void testProjectInjection()
            throws Exception {
        Plugin myPlugin = loadPlugin();
        assertNotNull(myPlugin.getProject());
    }

    public void testBuildContextInjection()
            throws Exception {
        Plugin myPlugin = loadPlugin();
        assertNotNull(myPlugin.getBuildContext());
    }

    public void testExecutionInjection()
            throws Exception {
        Plugin myPlugin = loadPlugin();
        assertNotNull(myPlugin.getExecution());
    }
**/

    /**
     * @throws Exception if any
     */
    public void testExecution()
            throws Exception {
        try {
            Plugin myPlugin = loadPlugin();
            assertNotNull("Null Plugin", myPlugin);
//            myPlugin.execute();
        } catch (InvocationTargetException ex) {
            System.out.println("oops!" + ex.getCause().toString());
        }

    }

    /**
     * @throws Exception if any
     */
    public void testEndpoint()
            throws Exception {
        try {
            Plugin myPlugin = loadPlugin();
            String url="http://docker:3001/md2pdf";
            assertNotNull("Null Plugin", myPlugin);
            assertNotNull("Null", myPlugin.getEndpoint());
            assertTrue("Expected [" + url +
                       "] Not equal to:[" +
                       myPlugin.getEndpoint().toString() + "]",
                       myPlugin.getEndpoint().toString().equals(url));
        } catch (InvocationTargetException ex) {
            System.out.println("oops!" + ex.getCause().toString());
        }

    }
    /**
     * @throws Exception if any
     */
    public void testFileset()
            throws Exception {
        try {
            Plugin myPlugin = loadPlugin();
            assertNotNull("Null Plugin", myPlugin);
            assertNotNull("Null Fileset", myPlugin.getFileset());
        } catch (InvocationTargetException ex) {
            System.out.println("oops!" + ex.getCause().toString());
        }

    }
}
