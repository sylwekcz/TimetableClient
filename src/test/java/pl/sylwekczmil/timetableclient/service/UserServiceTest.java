/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.sylwekczmil.timetableclient.service;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pl.sylwekczmil.timetableclient.model.User;

/**
 *
 * @author Hp
 */
public class UserServiceTest {

    public UserServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of login method, of class UserService.
     */
    @Test
    public void testLogin() throws Exception {

        UserService instance = new UserService();
        System.out.println("login");
        String username = "sylwek";
        String password = "haslo123";
        instance.login(username, password);
    }

    /**
     * Test of getCurrentUser method, of class UserService.
     */
    @Test
    public void testGetCurrentUser() throws Exception {

        UserService instance = new UserService();
        System.out.println("getCurrentUser");
        testLogin();
        User expResult = new User(1, "sylwek", null);
        User result = instance.getCurrentUser();
        assertEquals(expResult, result);
    }

    /**
     * Test of getToken method, of class UserService.
     */
    @Test
    public void testGetToken() throws Exception {

        UserService instance = new UserService();
        System.out.println("getToken");
        String expResult = null;
        String result = instance.getToken();
        assertEquals(expResult, result);
    }

}
