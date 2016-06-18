package pl.sylwekczmil.timetableclient.service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import java.util.List;
import javax.ws.rs.core.MediaType;
import pl.sylwekczmil.timetableclient.model.Timetable;
import pl.sylwekczmil.timetableclient.service.exceptions.NotLoggedInException;
import pl.sylwekczmil.timetableclient.service.exceptions.NotModifiedException;

public class TimetableService {

    UserService us = UserService.getInstance();
    WebResource resource = Client.create().resource("http://localhost:8080/TimetableServer/webapi");

    private static TimetableService instance = null;

    private TimetableService() {
    }

    public static TimetableService getInstance() {
        if (instance == null) {
            instance = new TimetableService();
        }
        return instance;
    }

    public List<Timetable> getTimetablesByUserId(Integer userId) throws NotLoggedInException {
        ClientResponse response = resource
                .path("user")
                .path(userId.toString())
                .path("timetable")
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

        return response.getEntity(new GenericType<List<Timetable>>() {
        });

    }

    public void addTimetable(Timetable timetable) throws NotLoggedInException, NotModifiedException {
        ClientResponse response = resource
                .path("timetable")
                .post(ClientResponse.class, timetable);
        if (response.getStatus() != 201) {
            if (response.getStatus() == 401) {
                throw new NotLoggedInException();
            } else {
                throw new NotModifiedException();
            }
        }
    }

    public void removeTimetable(String timetableName) throws NotLoggedInException, NotModifiedException {

        List<Timetable> timetables = getTimetablesByUserId(us.getCurrentUser().getIdUser());
        long timetableId = -1;
        for (Timetable t : timetables) {
            if (t.getName().equals(timetableName)) {
                timetableId = t.getIdTimetable();
            }
        }
        if (timetableId != -1) {
            ClientResponse response = resource
                    .path("timetable")
                    .path(""+timetableId)
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

}
