package commands.mariadb;

import core.ErrorHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.io.IOUtils;
import util.Settings;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

public class DBManager {
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

    private static BufferedReader BaseConnection(String target) throws Exception {
        URL script = new URL(Settings.getDBHost() + target);
        URLConnection sc = script.openConnection();
        String userpass = Settings.getDBAccess();
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));
        sc.setRequestProperty ("Authorization", basicAuth);
        return new BufferedReader(new InputStreamReader(sc.getInputStream()));
    }
}
