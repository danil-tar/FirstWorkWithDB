package messenger.repository;

import messenger.dto.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Optional;

public class ReferralRepository {
    private static ReferralRepository instance = null;

    private ReferralRepository() {
    }

    public static synchronized ReferralRepository getInstance() {
        if (instance == null) {
            instance = new ReferralRepository();
        }
        return instance;
    }

    public Optional<User> getUserFromPartnerId(Integer partnerId) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        Statement statement = connection.createStatement();

        String queryGet = String.format("SELECT * FROM users WHERE partner_id ='%d'", partnerId);
        ResultSet resultSet = statement.executeQuery(queryGet);

        User user = null;
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String email = resultSet.getString(3);
            String password = resultSet.getString(4);
            user = new User(id, name, email, password);
            user.setPartnerId(partnerId);

            System.out.printf("%d. %s  %s %s\n", id, name, email, password);
        }
        connection.close();
        return Optional.ofNullable(user);
    }

    public Optional<User> getUserFromId(Integer id) throws SQLException {

        Connection connection = ConnectionFactory.getInstance().getConnection();

        Statement statement = connection.createStatement();

        String queryGet = String.format("SELECT * FROM users WHERE id ='%d'", id);
        ResultSet resultSet = statement.executeQuery(queryGet);

        User user = null;
        while (resultSet.next()) {
            String name = resultSet.getString(2);
            String email = resultSet.getString(3);
            String password = resultSet.getString(4);
            int partnerId = resultSet.getInt(5);
            user = new User(id, name, email, password);
            user.setPartnerId(partnerId);

            System.out.printf("%d. %s  %s %s\n", id, name, email, password);
        }
        connection.close();
        return Optional.ofNullable(user);
    }

    public void setReferralId(Integer referrerId, Integer referralId) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        Statement statement = connection.createStatement();

        String querySetReferralId = String.format("INSERT INTO referrals (referrer_id, referral_id ) VALUES ('%d', '%d');", referrerId, referralId);
        statement.execute(querySetReferralId);

        connection.close();
    }

    public HashSet<String> getReferralEmails(Integer partnerId) {

        HashSet<String> referralEmailsHashSet = new HashSet<>();

        Connection connection = ConnectionFactory.getInstance().getConnection();

        try {
            Statement statement = connection.createStatement();

            String queryReferralSelection = String.format
                    ("Select * FROM users u INNER JOiN referrals r on u.id = r.referrer_id where u.partner_id = '%d'", partnerId);
            ResultSet resultSet = statement.executeQuery(queryReferralSelection);

            while (resultSet.next()) {
                int referralId = resultSet.getInt(7);
                referralEmailsHashSet.add(ReferralRepository.getInstance().getUserFromId(referralId).get().getEmail());
            }

            connection.close();
            return referralEmailsHashSet;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Problem witch DB");
        } finally {
        }
        return referralEmailsHashSet;
    }

    public void clear() {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM referrals;");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
