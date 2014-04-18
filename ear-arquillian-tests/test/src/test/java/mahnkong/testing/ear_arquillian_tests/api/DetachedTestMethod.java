package mahnkong.testing.ear_arquillian_tests.api;

import java.io.Serializable;

//The functional interface for our lamda
public interface DetachedTestMethod<T> extends Serializable {
	public T execute(Greeter greeter);
}