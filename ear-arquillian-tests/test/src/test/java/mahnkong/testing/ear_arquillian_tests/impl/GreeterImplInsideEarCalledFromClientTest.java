package mahnkong.testing.ear_arquillian_tests.impl;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import mahnkong.testing.ear_arquillian_tests.api.Greeter;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

//The standalone client EJB method invocation test case
@RunWith(Arquillian.class)
public class GreeterImplInsideEarCalledFromClientTest extends
		AbstractGreeterImplInsideEarTest {

	@Test
	@RunAsClient
	public void testSayHello() {
		try {
			// create standard ejb client
			final Properties jndiProperties = new Properties();
			jndiProperties.put(Context.URL_PKG_PREFIXES,
					"org.jboss.ejb.client.naming");
			final Context context = new InitialContext(jndiProperties);

			// remote lookup
			Greeter greeter = (Greeter) context
					.lookup("ejb:ear-arquillian-tests-ear-1.0/ear-arquillian-tests-impl-1.0/GreeterImpl!mahnkong.testing.ear_arquillian_tests.api.Greeter");
			// invoke
			String result = greeter.sayHello("world");
			// test result
			Assert.assertEquals("response as expected", "Hello, world!", result);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Fail");
		}
	}
}
