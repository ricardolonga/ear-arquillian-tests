package mahnkong.testing.ear_arquillian_tests.impl;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateful;

import mahnkong.testing.ear_arquillian_tests.api.DetachedTestMethod;
import mahnkong.testing.ear_arquillian_tests.api.Greeter;
import mahnkong.testing.ear_arquillian_tests.api.GreeterWrapper;

//The Been wrapper implementation
@Stateful
@Remote(GreeterWrapper.class)
public class GreeterWrapperImpl implements GreeterWrapper {
	// local lookup
	@EJB(mappedName = "java:global/ear-arquillian-tests-ear-1.0/ear-arquillian-tests-impl-1.0/GreeterImpl!mahnkong.testing.ear_arquillian_tests.api.Greeter")
	private Greeter greeter;
	private DetachedTestMethod<?> detachedTestable;

	@Override
	public Object executeTest() {
		return detachedTestable.execute(greeter);
	}

	@Override
	public void registerDetachedTestMethod(
			DetachedTestMethod<? extends Object> detachedTestable) {
		this.detachedTestable = detachedTestable;
	}
}