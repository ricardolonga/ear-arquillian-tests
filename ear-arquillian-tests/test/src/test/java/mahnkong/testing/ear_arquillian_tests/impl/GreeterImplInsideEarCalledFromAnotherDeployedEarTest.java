package mahnkong.testing.ear_arquillian_tests.impl;

import java.io.File;

import javax.ejb.EJB;

import mahnkong.testing.ear_arquillian_tests.api.GreeterWrapper;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class GreeterImplInsideEarCalledFromAnotherDeployedEarTest extends AbstractGreeterImplInsideEarTest{  
	
    @Deployment(testable = false, name="eartest.ear", order=2)
    public static EnterpriseArchive createTestDeployment() {
        File api = Maven.configureResolver().fromFile(new File("settings.xml")).resolve("mahnkong.testing:ear-arquillian-tests-api:jar:1.0").withoutTransitivity().asSingleFile();

        JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "eartest.jar")
                .addClasses(GreeterWrapper.class, GreeterWrapperImpl.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        
        EnterpriseArchive ear = ShrinkWrap.create(EnterpriseArchive.class, "eartest.ear")
        		.addAsLibrary(api)
        		.addAsModule(jar);
        return ear;
    }
    
    @EJB(mappedName="ejb:eartest/eartest/GreeterWrapperImpl!mahnkong.testing.ear_arquillian_tests.api.GreeterWrapper")
    private GreeterWrapper greeterWrapper;
    
    @Test
    public void testSayHello() {
    	try {
	    	String result = greeterWrapper.sayHelloFromGreeter("world");
	        Assert.assertEquals("response as expected", "Hello, world!", result);
    	} catch (Exception e) {
    		e.printStackTrace();
    		Assert.fail("Fail");
    	}
    }
}
