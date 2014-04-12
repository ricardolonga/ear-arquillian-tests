package mahnkong.testing.ear_arquillian_tests.impl;

import javax.ejb.EJB;

import mahnkong.testing.ear_arquillian_tests.api.Greeter;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class GreeterImplInsideEarCalledFromDeployedJarTest extends AbstractGreeterImplInsideEarTest{  
    @Deployment(testable = true, name="eartest.jar", order=2)
    public static JavaArchive createTestDeployment() {
        
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "eartest.jar")
                .addClasses(Greeter.class, AbstractGreeterImplInsideEarTest.class, GreeterImplInsideEarCalledFromDeployedJarTest.class)
                .addAsManifestResource("jboss-ejb-client.properties", "jboss-ejb-client.properties")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        
        return jar;
    }
    
    @EJB(mappedName="ejb:ear-arquillian-tests-ear-1.0/ear-arquillian-tests-impl-1.0/GreeterImpl!mahnkong.testing.ear_arquillian_tests.api.Greeter")
    private Greeter greeter;
    
    @Test
    public void testSayHello() {
    	try {
	    	String result = greeter.sayHello("world");
	        Assert.assertEquals("response as expected", "Hello, world!", result);
    	} catch (Exception e) {
    		e.printStackTrace();
    		Assert.fail("Fail");
    	}
    }
}
