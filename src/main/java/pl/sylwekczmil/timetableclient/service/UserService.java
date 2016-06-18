package pl.sylwekczmil.timetableclient.service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import javax.ws.rs.core.MediaType;
import pl.sylwekczmil.timetableclient.model.Credentials;
import pl.sylwekczmil.timetableclient.model.User;
import pl.sylwekczmil.timetableclient.service.exceptions.NotLoggedInException;
import pl.sylwekczmil.timetableclient.service.exceptions.NotModifiedException;
import pl.sylwekczmil.timetableclient.service.exceptions.WrongCredentialsException;

public class UserService {

    private User currentUser;
    private String token;
    WebResource resource = Client.create().resource("http://localhost:8080/TimetableServer/webapi");

    private static UserService instance = null;

    private UserService() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public void login(String username, String password) throws WrongCredentialsException {
        Credentials c = new Credentials(username, password);
        ClientResponse response = resource
                .path("login")
                .type(MediaType.APPLICATION_XML)
                .post(ClientResponse.class, c);

        if (response.getStatus() != 200) {
            if (response.getStatus() == 401) {
                throw new WrongCredentialsException();
            } else {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        }

        token = response.getEntity(String.class);
    }

    public void addUser(User user) throws NotModifiedException{
        ClientResponse response = resource
                .path("user")
                .type(MediaType.APPLICATION_XML)
                .post(ClientResponse.class, user);
        if (response.getStatus() != 201) {
           throw new NotModifiedException();
        }
    }

    public User getCurrentUser() throws NotLoggedInException {
        if (currentUser == null) {
            ClientResponse response = resource
                    .path("user")
                    .path("current")
                    .header("Authorization", token)
                    .accept(MediaType.APPLICATION_XML)
                    .get(ClientResponse.class);

            if (response.getStatus() != 200) {
                if (response.getStatus() == 401) {
                    throw new NotLoggedInException();
                } else {
                    throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
                }
            }
            currentUser = response.getEntity(User.class);
            currentUser.setToken(token);
        }

        return currentUser;
    }

    public String getToken() {
        return token;
    }

}
