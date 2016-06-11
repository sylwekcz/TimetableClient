package pl.sylwekczmil.timetableclient.service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import java.util.List;
import javax.ws.rs.core.MediaType;
import pl.sylwekczmil.timetableclient.model.Timetable;

public class TimetableService {

    UserService us = new UserService();
    WebResource resource = Client.create().resource("http://localhost:8080/TimetableServer/webapi");

    public List<Timetable> getTimetablesByUserId(Integer userId) {
        ClientResponse response = resource
                .path("user")
                .path(userId.toString())
                .path("timetable")
                .accept(MediaType.APPLICATION_XML)
                .header("Authorization", us.getToken())
                .header("Authorization", "sylwek:t0kvcnneuo6op4324aboku9f91")
                .get(ClientResponse.class);
        
        return response.getEntity(new GenericType<List<Timetable>>() {});
        
        
    }

}
