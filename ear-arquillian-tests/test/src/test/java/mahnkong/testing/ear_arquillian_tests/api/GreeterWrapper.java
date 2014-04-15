package mahnkong.testing.ear_arquillian_tests.api;

import java.util.List;

public interface GreeterWrapper {
	public String sayHelloFromGreeter(String name);
	public int invoke(List<String> list);
}
