package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;

@SpringApplicationConfiguration(classes = DemoApplication.class)
@WebAppConfiguration
public class DemoApplicationTests extends AbstractTestNGSpringContextTests {

	@Test
	public void shouldGreet() {

	}

}
