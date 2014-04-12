package testing.ear_arquillian_tests.impl;

import java.io.File;

import javax.ejb.EJB;

import mahnke.testing.ear_arquillian_tests.api.SimpleBean;

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
public class SimpleBeanImplEarIntegrationTest {
    @Deployment(testable=false, name="ear-arquillian-tests-ear.ear", order=1)
    public static EnterpriseArchive createDeployment1() {
    	
        File earFile = Maven.configureResolver().fromFile(new File("C:/tools/apache-maven-3.1.1/conf/settings.xml")).resolve("mahnke.testing:ear-arquillian-tests-ear:ear:1.0").withoutTransitivity().asSingleFile();
        EnterpriseArchive archive = ShrinkWrap.createFromZipFile(EnterpriseArchive.class, earFile);

        return archive;
    }
    
    @Deployment(testable=true, name="eartest.jar", order=2)
    public static JavaArchive createDeployment2() {
        
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "eartest.jar")
                .addClasses(SimpleBean.class, SimpleBeanImplEarIntegrationTest.class)
                .addAsManifestResource("jboss-ejb-client.properties", "jboss-ejb-client.properties")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        
        return jar;
    }

    @EJB(mappedName="ejb:ear-arquillian-tests-ear-1.0/ear-arquillian-tests-impl-1.0/SimpleBeanImpl!mahnke.testing.ear_arquillian_tests.api.SimpleBean")
    SimpleBean simpleBean;
    
    @Test
    public void testSaySomething() {
    	try {
	    	String result = simpleBean.saySomething("Whatever");
	        Assert.assertEquals("response as expected", "Hello!", result);
    	} catch (Exception e) {
    		e.printStackTrace();
    		Assert.fail("Fail");
    	}
    }
}
