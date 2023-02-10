import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import messenger.MessengerApplication;
import messenger.controller.AuthorizationController;
import messenger.dto.User;
import messenger.service.CreateUserService;
import messenger.service.DeleteUserService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.List;

public class TestAuthorizationInService {

    @BeforeAll
    // @Test
    public static void createRowsToDataBaseForTest() {
        DeleteUserService deleteUserService = DeleteUserService.getInstance();
        deleteUserService.clear();


        List<User> users = List.of(new User(null, "dan", "dan@mail.ru", "123nj"),
                new User(null, "dan", "dan1253@mail.ru", "58rgg"),
                new User(null, "andi", "andi@mail.ru", "khjgyf14"),
                new User(null, "andi", "154andi@mail.ru", "pofu775mn"),
                new User(null, "Fan", "jhayt134@mail.ru", "iuf755f"));

        CreateUserService createUserService = CreateUserService.getInstance();
        for (User user : users) {
            createUserService.registrationNewUser(user, null);
        }

    }

    @Test
    public void testThatUserCanGaveJWTTokenAfterAuthorizationWithCorrectCredentials() {

        class WorkTomcat extends Thread {
            @Override
            public void run() {
                MessengerApplication.startTomcat();
            }
        }

        WorkTomcat workTomcat = new WorkTomcat();
        workTomcat.start();


        System.out.println(Thread.currentThread());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Unirest unirest = new Unirest();
        unirest.setTimeouts(0, 0);

        HttpResponse<String> response;

        try {
            response = unirest.get("http://localhost:8080/messenger//login?email=dan@mail.ru&name=dan&password=123nj")
                    .asString();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }

        String responseBody = response.getBody();
        Headers headers = response.getHeaders();

        System.out.println(responseBody);
        System.out.println(headers);

//        AuthorizationController.AuthorizationResponse authorizationResponse = null;
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            authorizationResponse = objectMapper.readValue(responseBody, AuthorizationController.AuthorizationResponse.class);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println(authorizationResponse.getResult());

//        OkHttpClient client = new OkHttpClient();
//        MediaType mediaType = MediaType.parse("text/plain");
//        RequestBody body = RequestBody.create(mediaType, "");
//        Request request = new Request.Builder()
//                .url("http://localhost:8080/messenger//login?email=dan@mail.ru&name=dan&password=123nj")
//                .method("GET", body)
//                .addHeader("Jwt-token", "")
//                .build();
//        Response response = null;
//        try {
//            response = client.newCall(request).execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        ResponseBody body1 = response.body();
//        System.out.println(body1);

        workTomcat.interrupt();
    }


}
