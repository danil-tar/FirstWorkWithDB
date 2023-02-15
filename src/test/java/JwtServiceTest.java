import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.JwtException;
import messenger.dto.User;
import messenger.menegment.InstanceFactory;
import messenger.service.JWTService;
import org.junit.Assert;
import org.junit.Test;

public class JwtServiceTest {
    @Test
    public void testCreateJwtToken() throws JsonProcessingException {
        JWTService jwtService = InstanceFactory.getInstance(JWTService.class);

        User userExpected = new User(null, "testName", "333@mail.ru", null);

        String testToken = jwtService.generateJWToken(userExpected.getName(), userExpected.getEmail());
        User userActual = jwtService.testValidity(testToken);
        System.out.println(testToken);

        Assert.assertEquals(userExpected.getName(), userActual.getName());
        Assert.assertEquals(userExpected.getEmail(), userActual.getEmail());

        jwtService.setTtlMillis(10);
        String invalidityToken = jwtService.generateJWToken(userExpected.getName(), userExpected.getEmail());

        try {
            userActual = jwtService.testValidity(invalidityToken);
        } catch (JwtException e) {
            Assert.assertTrue(true);
        }
    }
}
