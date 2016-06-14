package pl.sylwekczmil.timetableclient.service;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import pl.sylwekczmil.timetableclient.model.Timetable;
import pl.sylwekczmil.timetableclient.service.exceptions.NotLoggedInException;
import pl.sylwekczmil.timetableclient.service.exceptions.WrongCredentialsException;

public class TimetableServiceTest {

    TimetableService instance = TimetableService.getInstance();
    UserService us = UserService.getInstance();
    

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
     * Test of getTimetablesByUserId method, of class TimetableService.
     */
    @org.junit.Test
    public void testGetTimetablesByUserId() throws NotLoggedInException, WrongCredentialsException {
        us.login("sylwek","haslo123");
        System.out.println("getTimetablesByUserId");
        Integer userId = 1;
        List<Timetable> expResult = new ArrayList<Timetable>();
        List<Timetable> result = instance.getTimetablesByUserId(userId);
        assertNotEquals(expResult, result);
    }

}
