package mahnke.testing.ear_arquillian_tests.impl;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import mahnke.testing.ear_arquillian_tests.api.SimpleBean;

@Stateless
@Remote
public class SimpleBeanImpl implements SimpleBean {

	@Override
	public String saySomething(String parameter) {
		// TODO Auto-generated method stub
		return "Hello!";
	}
}
