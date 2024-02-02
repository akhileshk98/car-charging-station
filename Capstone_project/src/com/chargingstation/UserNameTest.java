package com.chargingstation;

import java.io.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserNameTest {

	/* Tests the NewUserRegistration method in the UserName class by simulating user
	 input for a username and checks if the username is correctly assigned to the
	 userName field of the UserName instance. */
	@Test
	public void testUserName() {
		UserName u1 = new UserName();
		ArrayList<UserName> username = new ArrayList<>();
		String input = "Mary\nmojo";
		ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		u1.NewUserRegistration(username);
		assertEquals("Mary", u1.userName);
	}

	/* Tests the NewUserRegistration method in the UserName class by simulating user
	 input for a password and checks if the password is correctly assigned to the
	 password field of the UserName instance. */
	@Test
	public void testPassword() {
		UserName u1 = new UserName();
		ArrayList<UserName> username = new ArrayList<>();
		String input = "Mary\nmojo";
		ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		u1.NewUserRegistration(username);
		assertEquals("mojo", u1.password);
	}

	/* Tests the NewUserRegistration method in the UserName class by simulating user
	 input for a username and password. It then verifies if a new UserName object
	 is added to the username ArrayList and checks if the ID of the added object is 1. */
	@Test
	public void testUserAdded() {
		UserName u1 = new UserName();
		ArrayList<UserName> username = new ArrayList<>();
		String input = "Mary\nmojo";
		ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		u1.NewUserRegistration(username);
		assertEquals(1, username.get(0).c.id);
	}

}
