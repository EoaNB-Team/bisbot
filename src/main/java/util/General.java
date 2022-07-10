package util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.requests.RestAction;

import java.awt.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class General {
    public static EmbedBuilder getInfoEmbed(ReadyEvent event, MessageReceivedEvent mevent, String title) throws ExecutionException, InterruptedException {
        JDA usedJDA;
        if (event == null) {
            usedJDA = mevent.getJDA();
        } else {
            usedJDA = event.getJDA();
        }
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(title);
        eb.setColor(new Color(3, 193, 19));
        eb.setFooter("edbotJ", usedJDA.getSelfUser().getAvatarUrl());
        eb.addField("System", "Platform: " + System.getProperty("os.name") + "\nVersion: " + System.getProperty("os.version") + "\nArch: " + System.getProperty("os.arch"), true);
        long runt = System.nanoTime() - Settings.STARTTIME;
        long mem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        eb.addField("edbotJ", "Runtime: " + TimeUnit.HOURS.convert(runt, TimeUnit.NANOSECONDS) + "h" + "\nU.Memory: " + mem / 1000000 + "MB" + "\nVersion: " + Settings.VERSION, true);
        eb.addField("Discord", "Guilds: " + usedJDA.getGuilds().size() + "\nUsers: " + usedJDA.getUsers().size() + "\nPing: " + usedJDA.getGatewayPing() + "ms", true);
        RestAction<User> u = usedJDA.retrieveUserById(Settings.OWNER);
        eb.addField("Info", "edbotJ was written by " + u.submit().get().getAsMention() + " with the help of [JDA 4.2](https://github.com/DV8FromTheWorld/JDA).", true);
        return eb;
    }

    /**
     * RegEx for Web URL
     * <p><a href="https://github.com/aosp-mirror/platform_frameworks_base/blob/master/core/java/android/util/Patterns.java">Source GitHub</a>
    */
    public static final String GOOD_IRI_CHAR =
            "a-zA-Z0-9\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF";

    public static Pattern IP_ADDRESS () {
        return Pattern.compile(
                "((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]"
                        + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
                        + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
                        + "|[1-9][0-9]|[0-9]))");
    }

    /**
     * RFC 1035 Section 2.3.4 limits the labels to a maximum 63 octets.
     */
    private static final String IRI
            = "[" + GOOD_IRI_CHAR + "]([" + GOOD_IRI_CHAR + "\\-]{0,61}[" + GOOD_IRI_CHAR + "]){0,1}";

    private static final String GOOD_GTLD_CHAR =
            "a-zA-Z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF";

    private static final String GTLD = "[" + GOOD_GTLD_CHAR + "]{2,63}";
    private static final String HOST_NAME = "(" + IRI + "\\.)+" + GTLD;

    public static Pattern DOMAIN_NAME() {
        return Pattern.compile("(" + HOST_NAME + "|" + IP_ADDRESS() + ")");
    }

    public static Pattern WEB_URL() {
        return Pattern.compile(
            "((?:(http|https|Http|Https|rtsp|Rtsp):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)"
                    + "\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_"
                    + "\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?"
                    + "(?:" + DOMAIN_NAME() + ")"
                    + "(?:\\:\\d{1,5})?)" // plus option port number
                    + "(\\/(?:(?:[" + GOOD_IRI_CHAR + "\\;\\/\\?\\:\\@\\&\\=\\#\\~"  // plus option query params
                    + "\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?"
                    + "(?:\\b|$)");
    }
}
