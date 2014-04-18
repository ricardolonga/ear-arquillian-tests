package mahnkong.testing.ear_arquillian_tests.api;


//The bean wrapper interface
public interface GreeterWrapper {
	public Object executeTest();
	public void registerDetachedTestMethod(DetachedTestMethod<? extends Object> detachedTestable);
}
