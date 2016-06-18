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
import pl.sylwekczmil.timetableclient.service.exceptions.NotModifiedException;

/**
 *
 * @author Hp
 */
public class UserServiceTest {

    UserService instance = UserService.getInstance();

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

    
//    @Test
//    public void testLogin() throws Exception {
//
//        System.out.println("login");
//        String username = "sylwek";
//        String password = "haslo123";
//        instance.login(username, password);
//    }
//
//   
//    @Test
//    public void testGetCurrentUser() throws Exception {
//
//        System.out.println("getCurrentUser");
//        testLogin();
//        User expResult = new User(1, "sylwek", null);
//        User result = instance.getCurrentUser();
//        assertEquals(expResult, result);
//    }
//
//    
//    @Test
//    public void testGetToken() throws Exception {
//        
//        System.out.println("getToken");
//        String expResult = instance.getToken();
//        testLogin();
//        String result = instance.getToken();
//        assertNotEquals(expResult, result);
//    }
    
//    @Test
//    public void addUser(){
//        User user = new User("sdfsfdaf","aaaaa");
//        user.setIdUser(1);
//        instance.addUser(user);
//                
//    }

}
