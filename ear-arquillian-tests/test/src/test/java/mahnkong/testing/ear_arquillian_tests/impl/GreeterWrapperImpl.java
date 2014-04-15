package mahnkong.testing.ear_arquillian_tests.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import mahnkong.testing.ear_arquillian_tests.api.Greeter;
import mahnkong.testing.ear_arquillian_tests.api.GreeterWrapper;

@Stateless
@Remote(GreeterWrapper.class)
public class GreeterWrapperImpl implements GreeterWrapper {
	@EJB(mappedName="java:global/ear-arquillian-tests-ear-1.0/ear-arquillian-tests-impl-1.0/GreeterImpl!mahnkong.testing.ear_arquillian_tests.api.Greeter")
	private Greeter greeter;
	
	@Override
	public String sayHelloFromGreeter(String name) {
		return greeter.sayHello(name);
	}

	@Override
	public int invoke(List<String> list) {
		return list.size();
	}
}
