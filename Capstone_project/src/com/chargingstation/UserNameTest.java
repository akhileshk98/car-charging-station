package com.chargingstation;
import java.io.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserNameTest {

	@Test
	public void testUserName() {
	    UserName u1= new UserName();
	    ArrayList<UserName> username = new ArrayList<>();
	    String input = "Mary\nmojo";
	    ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    u1.NewUserRegistration(username);
	    assertEquals("Mary", u1.userName);
	}
	
	@Test
	public void testPassword() {
	    UserName u1= new UserName();
	    ArrayList<UserName> username = new ArrayList<>();
	    String input = "Mary\nmojo";
	    ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    u1.NewUserRegistration(username);
	    assertEquals("mojo", u1.password);
	}
	@Test
	public void testUserAdded() {
	    UserName u1= new UserName();
	    ArrayList<UserName> username = new ArrayList<>();
	    String input = "Mary\nmojo";
	    ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    u1.NewUserRegistration(username);
	    assertEquals(1, username.get(0).c.id);
	}

}
