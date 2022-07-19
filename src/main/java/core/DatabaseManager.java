package core;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Settings;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;

public class DatabaseManager {
	private static final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);

	private static Connection connection;

	/**
	 * Sets up a connection to the database. The connection will be retained through the entire lifespan of the bot.
	 */
	public static void init() {
		String url = "jdbc:postgresql://" + Settings.getDBHost() + "/" + Settings.getDBName();

		// Connect to database.
		try {
			connection = DriverManager.getConnection(url, Settings.getDBUser(), Settings.getDBPassword());
		} catch (SQLTimeoutException ex) {
			logger.error("Connecting to database timed out; terminating.");

			System.exit(-1);
			return;
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
			logger.error("Could not connect to database; terminating.");

			System.exit(-1);
			return;
		}

		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("SELECT VERSION()");

			if (rs.next()) {
				logger.info(rs.getString(1));
			}
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
		}
	}

	/**
	 * Publish SQL statement. Use ? to fill in arguments, and the code will apply them automatically. Amount of ? should be the same as list of args.
	 * @param sql The SQL statement to execute.
	 * @param event An event so we can write the error as a message if debug is enabled.
	 * @param param The parameters to insert into this prepared statement.
	 * @return Whether the execution was successful or not.
	 */
	public static boolean publish(String sql, MessageReceivedEvent event, Object... param) {
		try {
			PreparedStatement st = connection.prepareStatement(sql);

			for (int i = 0; i < param.length; i++) {
				handleParameter(st, i + 1, param[i]);
			}

			st.addBatch();

			st.execute();
			st.close();

			return true;
		} catch (Exception ex) {
			logger.error(ex.getMessage());

			if (Settings.debug) {
				ErrorHandler.CustomEmbedError(ex.getMessage(), event);
			}
		}
		return false;
	}

	/**
	 * Get a two-dimensional array of objects representing a table.
	 * @param sql The SQL statement to execute.
	 * @param event An event so we can write the error as a message if debug is enabled.
	 * @param param The parameters to insert into this prepared statement.
	 * @return A two-dimensional array of objects.
	 */
	public static Object[][] getTable(String sql, MessageReceivedEvent event, Object... param) {
		// TODO: I'm not very proud of this.
		try {
			PreparedStatement st = connection.prepareStatement(sql);

			for (int i = 0; i < param.length; i++) {
				handleParameter(st, i + 1, param[i]);
			}

			st.addBatch();

			ResultSet rs = st.executeQuery();

			// Does some magic and puts it in
			ArrayList<Object[]> rows = new ArrayList<>();
			while (rs.next()) {
				Object[] columns = new Object[rs.getMetaData().getColumnCount()];
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					columns[i-1] = rs.getObject(i);
				}

				rows.add(columns);
			}

			st.close();
			return rows.toArray(new Object[0][0]);
		} catch (Exception ex) {
			logger.error(ex.getMessage());

			if (Settings.debug) {
				ErrorHandler.CustomEmbedError(ex.getMessage(), event);
			}
		}
		return null;
	}

	@Deprecated
	public static boolean EstablishConnection(MessageReceivedEvent event, String target) {
		try {
			BufferedReader in = BaseConnection(target);
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				if (inputLine.contains("successfully")) {
					ErrorHandler.CustomEmbed(inputLine, new Color(3, 193, 19), event);
					in.close();
					return true;
				} else {
					ErrorHandler.CustomEmbedError(inputLine, event);
					in.close();
					return false;
				}
			}
		} catch (Exception e) {
			if (Settings.debug) {
				ErrorHandler.CustomEmbedError(e.getMessage(), event);
			}
		}
		return false;
	}

	@Deprecated
	public static String EstablishConnectionReturn(MessageReceivedEvent event, String target) {
		try {
			BufferedReader in = BaseConnection(target);
			String inputLine = IOUtils.toString(in);

			in.close();
			return inputLine;
		} catch (Exception e) {
			if (Settings.debug) {
				ErrorHandler.CustomEmbedError(e.getMessage(), event);
			}
		}
		return "";
	}

	@Deprecated
	private static BufferedReader BaseConnection(String target) throws Exception {
		URL script = new URL(Settings.getDBHost() + target);
		URLConnection sc = script.openConnection();
		String userpass = Settings.getDBPassword();
		String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));
		sc.setRequestProperty("Authorization", basicAuth);
		return new BufferedReader(new InputStreamReader(sc.getInputStream()));
	}

	/**
	 * Gets a statement and applies an object to a parameter with a specified index.
	 * @param st The statement to add handle parameter for.
	 * @param ix The index of the parameter.
	 * @param obj The object to apply.
	 * @throws SQLException If something goes wrong.
	 */
	private static void handleParameter(PreparedStatement st, int ix, Object obj) throws SQLException {
		// TODO: There has to be a better way to do this. PSQL doesn't support setObject.
		if (obj instanceof Integer) {
			st.setInt(ix, (Integer) obj);
		} else if (obj instanceof String) {
			st.setString(ix, (String) obj);
		} else if (obj instanceof Long) {
			st.setLong(ix, (Long) obj);
		} else if (obj instanceof Short) {
			st.setShort(ix, (Short) obj);
		}
	}
}
