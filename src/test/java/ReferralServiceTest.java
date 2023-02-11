import messenger.dto.User;
import messenger.repository.UserRepository;
import messenger.service.CreateUserService;
import messenger.service.ReferralService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;

public class ReferralServiceTest {

    @BeforeClass
    public static void before(){
        InitDb.createTables();
    }

    @AfterClass
    public static void clear(){
        InitDb.clearDB();
    }
    @Test
    public void getPartnerId() {
        CreateUserService.getInstance().
                registrationNewUser(new User(null, "Referrer", "refTest@mail.ru", "ghgy465"), null);
        Integer expectedPartnerId = 0;
        Optional<User> user = null;
        user = UserRepository.getInstance().getUser("refTest@mail.ru");

        if (user.isPresent()) {
            expectedPartnerId = user.get().getPartnerId();
        }
        Integer actualPartnerId = ReferralService.getInstance().getPartnerId("refTest@mail.ru");

        Assert.assertEquals(expectedPartnerId, actualPartnerId);
    }

    @Test
    public void testingGeneratePartnerId() {
        User user = new User(null, "Rama", "ndhyt@mail.ru", "1234n");
        CreateUserService.getInstance().
                registrationNewUser(user, null);
        ReferralService referralService = ReferralService.getInstance();
        Integer partnerIdExpected = referralService.generatePartnerId(user.getEmail());
        Optional<User> userExpected = UserRepository.getInstance().getUser("ndhyt@mail.ru");

        if (userExpected.isPresent()) {
            Integer partnerIdActual = referralService.getPartnerId(userExpected.get().getEmail());
            Assert.assertEquals(partnerIdExpected, partnerIdActual);
        }
    }

    @Test
    public void testingAddReferralsAndGettingListOfReferrals() {

        User userReferrer = new User(null, "Odi", "j82hjd0@mail.ru", "njd7");
        User userReferralOne = new User(null, "Tommi", "nitommi@mail.ru", "12887yu");
        User userReferralTwo = new User(null, "Bronx", "bromx@mail.ru", "buyty890");

        UserRepository userRepository = UserRepository.getInstance();
        try {
            userReferrer = userRepository.createNewUser(userReferrer);
            userReferralOne = userRepository.createNewUser(userReferralOne);
            userReferralTwo = userRepository.createNewUser(userReferralTwo);

        } catch (SQLException e) {
            
            throw new RuntimeException(e);
        }
        ReferralService referralService = ReferralService.getInstance();
        referralService.registrationAsReferral(userReferrer.getPartnerId(),userReferralOne.getEmail());
        referralService.registrationAsReferral(userReferrer.getPartnerId(),userReferralTwo.getEmail());

        HashSet<String> referrals = referralService.getReferrals(userReferrer.getEmail());
        Assert.assertTrue(referrals.contains(userReferralOne.getEmail())
                &&referrals.contains(userReferralTwo.getEmail()));
    }
}
