package mahnkong.testing.ear_arquillian_tests.impl;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import mahnkong.testing.ear_arquillian_tests.api.Greeter;

@Stateless
@Remote
public class GreeterImpl implements Greeter {

	@Override
	public String sayHello(String name) {
		return "Hello, " + name + "!";
	}
}
