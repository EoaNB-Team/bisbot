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

	public static boolean publish(String sql, MessageReceivedEvent event) {
		try {
			Statement st = connection.createStatement();

			st.execute(sql);

			return true;
		} catch (Exception ex) {
			logger.error(ex.getMessage());

			if (Settings.debug) {
				ErrorHandler.CustomEmbedError(ex.getMessage(), event);
			}
		}
		return false;
	}

	public static Object[] retrieveColumn(String sql, MessageReceivedEvent event) {
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(sql);

			ArrayList<Object> list = new ArrayList<>();

			while (rs.next()) {
				list.add(rs.getObject(0));
			}

			return list.toArray(new Object[0]);
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
        sc.setRequestProperty ("Authorization", basicAuth);
        return new BufferedReader(new InputStreamReader(sc.getInputStream()));
    }
}
