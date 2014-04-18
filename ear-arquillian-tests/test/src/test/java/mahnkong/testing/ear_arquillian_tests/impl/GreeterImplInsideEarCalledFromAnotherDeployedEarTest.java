package mahnkong.testing.ear_arquillian_tests.impl;

import java.io.File;

import javax.ejb.EJB;

import mahnkong.testing.ear_arquillian_tests.api.DetachedTestMethod;
import mahnkong.testing.ear_arquillian_tests.api.GreeterWrapper;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

// The EAR to EAR EJB method invocation test case
@RunWith(Arquillian.class)
public class GreeterImplInsideEarCalledFromAnotherDeployedEarTest extends
		AbstractGreeterImplInsideEarTest {

	// Creation of the test ear
	@Deployment(testable = true, name = "eartest.ear", order = 2)
	public static EnterpriseArchive createTestDeployment() {
		File api = Maven.configureResolver()
				.fromFile(new File(System.getProperty("maven.settingsFile")))
				.resolve("mahnkong.testing:ear-arquillian-tests-api:jar:1.0")
				.withoutTransitivity().asSingleFile();

		JavaArchive jar = ShrinkWrap
				.create(JavaArchive.class, "eartest.jar")
				.addClasses(
						GreeterImplInsideEarCalledFromAnotherDeployedEarTest.class,
						AbstractGreeterImplInsideEarTest.class,
						DetachedTestMethod.class, GreeterWrapper.class,
						GreeterWrapperImpl.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

		EnterpriseArchive ear = ShrinkWrap
				.create(EnterpriseArchive.class, "eartest.ear")
				.addAsLibrary(api).addAsModule(jar);
		return ear;
	}

	// remote lookup of the wrapper bean
	@EJB(mappedName = "ejb:eartest/eartest/GreeterWrapperImpl!mahnkong.testing.ear_arquillian_tests.api.GreeterWrapper?stateful")
	private GreeterWrapper greeterWrapper;

	@Test
	@RunAsClient
	public void testSayHello() {
		// create the lamda
		DetachedTestMethod<String> testMethod = (greeter) -> {
			// invoke method on the EJB we want to test
			return greeter.sayHello("world");
		};
		// register lamda in wrapper bean
		greeterWrapper.registerDetachedTestMethod(testMethod);
		// invoke
		String result = (String) greeterWrapper.executeTest();
		// check result
		Assert.assertEquals("response as expected", "Hello, world!", result);
	}
}
