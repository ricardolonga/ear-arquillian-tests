package mahnke.testing.ear_arquillian_tests.impl;

import javax.ejb.EJB;

import mahnke.testing.ear_arquillian_tests.api.SimpleBean;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class SimpleBeanImplIntegrationTest {
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
            .addClasses(SimpleBeanImpl.class, SimpleBean.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    
    @EJB
    SimpleBean simpleBean;

    @Test
    public void testSaySomething() {
    	String result = simpleBean.saySomething("Whatever");
        Assert.assertEquals("response as expected", "Hello!", result);
    }
}
