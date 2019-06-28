package com.resource.manager.resource;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainTests {

	@Test
	public void contextLoads() {
		Main m = new Main();

		assertEquals("Welcome to the resource page", m.getResourcePage(), "output must be welcome message");
	}

}
