package testing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import model.DBConnection;
import model.User;
import view.RegisterForm;

@RunWith(Parameterized.class)
public class RegisterFormTest {

	private static final String SELECT_USERS_STATEMENT = "SELECT * FROM users";

	@Parameter(0)
	public User testUser;

	@Parameters
	public static ArrayList<User> data() {

		List<User> userList = new ArrayList<User>();

		ResultSet rs = null;

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SELECT_USERS_STATEMENT,
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

			rs = stmt.executeQuery();

			while (rs.next()) {
				final User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("pwd"),
						rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"),
						rs.getString("country"), rs.getString("phone_number"), rs.getInt("administrator"),
						rs.getDate("date_of_birth"));

				userList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return (ArrayList<User>) userList;
	}

	@Test
	public void validEmailTest() {

		boolean emailCheck = RegisterForm.isValidEmail(testUser.getEmail());

		Assert.assertTrue(emailCheck);
	}

	@Test
	public void uniqueUsernameTest() {

		ResultSet rs = null;
		String username = testUser.getUsername();
		int counter = 0;

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SELECT_USERS_STATEMENT,
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

			rs = stmt.executeQuery();

			while (rs.next()) {
				if (username.equals(rs.getString("username")))
					counter++;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Assert.assertEquals(counter, 1);
	}

	@Test
	public void uniqueEmailTest() {

		ResultSet rs = null;
		String email = testUser.getEmail();
		int counter = 0;

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SELECT_USERS_STATEMENT,
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

			rs = stmt.executeQuery();

			while (rs.next()) {
				if (email.equals(rs.getString("email")))
					counter++;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Assert.assertEquals(counter, 1);
	}
}
