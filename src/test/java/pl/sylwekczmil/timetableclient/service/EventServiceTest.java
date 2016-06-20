/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.sylwekczmil.timetableclient.service;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pl.sylwekczmil.timetableclient.model.Event;
import pl.sylwekczmil.timetableclient.model.Timetable;
import pl.sylwekczmil.timetableclient.service.exceptions.NotLoggedInException;
import pl.sylwekczmil.timetableclient.service.exceptions.NotModifiedException;

/**
 *
 * @author bzyk4
 */
public class EventServiceTest {

    public EventServiceTest() {
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

//    @Test
//    public void testGetEventByTimetableId() throws Exception {       
//        Integer timetableId = 78;
//        EventService instance = EventService.getInstance();
//        List<Event> expResult = new ArrayList<Event>();
//        List<Event> result = instance.getEventByTimetableId(timetableId);
//        assertEquals(expResult, result);
//    }
    /**
     * Test of addEvent method, of class EventService.
     */
//    @Test
//    public void testAddEvent() throws Exception {
//        System.out.println("addEvent");
//        TimetableService ts = TimetableService.getInstance();
//        List<Timetable> lt = ts.getTimetablesByUserId(1);
//        for (int i = 1; i < 6; i++) {
//            Event event = new Event("new"+i, "ktos"+i, i, i+1, i, lt.get(0),"B10"+i);
//            EventService instance = EventService.getInstance();
//            instance.addEvent(event);
//        }
//    }

    /**
     * Test of removeEvent method, of class EventService.
     */
//    @Test
//    public void testRemoveEvent() throws NotLoggedInException, NotModifiedException  {
//        System.out.println("removeEvent");
//        Integer eventId = 2;
//        EventService instance =  EventService.getInstance();;
//        instance.removeEvent(eventId);
//    }
}
