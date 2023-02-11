import messenger.repository.ConnectionFactory;
import messenger.repository.UserRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InitDb {
    public static void createTables() {
        try {
            Class<?> driver = Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
        ConnectionFactory.changeDbURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");

        Connection connection = connectionFactory.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE users (\n" +
                    "\tid INT PRIMARY KEY AUTO_INCREMENT  NOT NULL,\n" +
                    "\t\"name\" varchar(30) NULL,\n" +
                    "\temail varchar(30) NULL,\n" +
                    "\t\"password\" varchar(30) NULL,\n" +
                    "\tpartner_id int4 NULL,\n" +
                    "\tCONSTRAINT users_email_key UNIQUE (email)\n" +
                    ");");

            statement.executeUpdate("CREATE TABLE referrals (\n" +
                    "\treferrer_id int4 NULL,\n" +
                    "\treferral_id int4 NOT NULL,\n" +
                    "\tCONSTRAINT referals_pkey PRIMARY KEY (referral_id)\n" +
                    ");\n");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void clearDB() {
        try {
            Class<?> driver = Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Connection connection = ConnectionFactory.getInstance().getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE users;");

            statement.executeUpdate("DROP TABLE referrals;");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
