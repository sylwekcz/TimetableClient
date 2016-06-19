package pl.sylwekczmil.timetableclient.service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import java.util.List;
import javax.ws.rs.core.MediaType;
import pl.sylwekczmil.timetableclient.model.Event;
import pl.sylwekczmil.timetableclient.service.exceptions.NotLoggedInException;
import pl.sylwekczmil.timetableclient.service.exceptions.NotModifiedException;

public class EventService {

    UserService us = UserService.getInstance();
    WebResource resource = Client.create().resource("http://localhost:8080/TimetableServer/webapi");

    private static EventService instance = null;

    private EventService() {
    }

    public static EventService getInstance() {
        if (instance == null) {
            instance = new EventService();
        }
        return instance;
    }
    
    
    public List<Event> getEventByTimetableId(Integer timetableId) throws NotLoggedInException {
        ClientResponse response = resource
                .path("timetable")
                .path(timetableId.toString())
                .path("event")
                .accept(MediaType.APPLICATION_XML)
                .header("Authorization", us.getToken())
                .get(ClientResponse.class);

        if (response.getStatus() != 200) {
            if (response.getStatus() == 401) {
                throw new NotLoggedInException();
            } else {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        }

        return response.getEntity(new GenericType<List<Event>>(){});

    }
    
    public void addEvent(Event event) throws NotLoggedInException, NotModifiedException {
        ClientResponse response = resource
                .path("event")
                .post(ClientResponse.class, event);
        if (response.getStatus() != 201) {
            if (response.getStatus() == 401) {
                throw new NotLoggedInException();
            } else {
                throw new NotModifiedException();
            }
        }
    }

    public void removeEvent(Integer eventId) throws NotLoggedInException, NotModifiedException {

        ClientResponse response = resource
                .path("event")
                .path(eventId.toString())
                .delete(ClientResponse.class);
        if (response.getStatus() != 200) {
            if (response.getStatus() == 401) {
                throw new NotLoggedInException();
            } else {
                throw new NotModifiedException();
            }
        }
    }
}
