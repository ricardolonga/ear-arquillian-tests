package mahnkong.testing.ear_arquillian_tests.impl;

import java.io.File;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

public class AbstractGreeterImplInsideEarTest {
    @Deployment(testable = false, name="ear-arquillian-tests-ear.ear", order=1)
    public static EnterpriseArchive createEarDeployment() {
    	
        File earFile = Maven.configureResolver().fromFile(new File("settings.xml")).resolve("mahnkong.testing:ear-arquillian-tests-ear:ear:1.0").withoutTransitivity().asSingleFile();
        EnterpriseArchive archive = ShrinkWrap.createFromZipFile(EnterpriseArchive.class, earFile);

        return archive;
    }
    
}
