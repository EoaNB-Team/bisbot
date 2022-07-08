package commands.mariadb.playtest;

import commands.interfaces.DBCommand;
import core.ErrorHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.Button;
import util.*;

import java.awt.*;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;

public class comAddReport implements DBCommand {
    private final String commandName = "aplay";

    @Override
    public boolean called(String[] Args, MessageReceivedEvent event) {
        return SharedComRequirements.checkCuria(event) && !event.isFromType(ChannelType.PRIVATE);
    }

    @Override
    public void action(String[] Args, MessageReceivedEvent event) {
        String name;
        String zone;
        String zoneRaw;
        boolean local;
        String userid = event.getAuthor().getId();
        String username = event.getAuthor().getName();
        String comment;
        String link;

        if (Args.length > 0) {
            String arg = Args[0].toLowerCase(Locale.ROOT);
            zoneRaw = arg;
            if (Secrets.vicari.containsKey(arg)) {
                try {
                    zone = Objects.requireNonNull(event.getGuild().getRoleById(Secrets.vicari.get(arg))).getName();
                } catch (NullPointerException e) {
                    ErrorHandler.CustomEmbedError("Could not get role name by id.", event);
                    event.getChannel().sendMessage("<@" + Secrets.OWNER + ">").queue();
                    return;
                }
            } else {
                ErrorHandler.CustomEmbedError("Zone name not found. See `" + Secrets.prefix + "zones` for a list of all zones.", event);
                return;
            }
        } else {
            ErrorHandler.CustomEmbedError("Invalid zone name. See `" + Secrets.prefix + "zones` for a list of all zones.", event);
            return;
        }
        if (Args.length > 1) {
            String arg = Args[1].toLowerCase(Locale.ROOT);
            if (arg.equals("file") || arg.equals("1") || arg.equals("true")) {
                local = true;
            } else if (arg.equals("link") || arg.equals("0") || arg.equals("false")) {
                local = false;
            } else {
                ErrorHandler.CustomEmbedError("Invalid source. Are you trying to submit a file or a link? Use `file`, `link`, `true`, `false`, `1` or `0`\"", event);
                return;
            }
        } else {
            ErrorHandler.CustomEmbedError("Invalid source. Are you trying to submit a file or a link? Use `file`, `link`, `true`, `false`, `1` or `0`", event);
            return;
        }
        if (Args.length > 2) {
            name = Args[2];
        } else {
            ErrorHandler.CustomEmbedError("Invalid playtest name.", event);
            return;
        }
        if (local) {
            if (event.getMessage().getAttachments().size() > 0) {
                //comment = "[Link](" + event.getMessage().getJumpUrl() + ")" + ((Args.length > 3) ? " " + Args[3] : "");
                link = event.getMessage().getJumpUrl();
                comment = link + ((Args.length > 3) ? " " + Args[3] : "");
            } else {
                ErrorHandler.CustomEmbedError("Submission designated a file but no file was attached.", event);
                return;
            }
        } else {
            if (Args.length > 3) {
                Matcher match = General.WEB_URL().matcher(Args[3]);
                if (match.find()) {
                    link = match.group(0);
                    //comment = Args[3].replace(url,  "[Link](" + url + ")");
                    comment = Args[3];
                } else {
                    ErrorHandler.CustomEmbedError("Could not detect any link in the comment.", event);
                    return;
                }
            } else {
                ErrorHandler.CustomEmbedError("Submission designated a link but no comment was attached.", event);
                return;
            }
        }

        boolean added = PlaytestReportmanager.AddPlaytestToDB(event, name, zone, local, userid, username, comment);
        if (added) {
            TextChannel chan = (TextChannel)Objects.requireNonNull(event.getGuild().getGuildChannelById(Secrets.vicariChans.get(zoneRaw)));

            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(new Color(3, 193, 19));
            eb.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
            eb.setTitle("New Playtest Added for " + zone);
            eb.setDescription("Click the button below to get a link to the playtest.");
            eb.addField(name, "by " + username + "\n\n" + comment, false);

            chan.sendMessage(eb.build())
                    .setActionRow(Button.link(link, "Link to playtest"))
                    .queue();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return Secrets.prefix + commandName + " <zone> <local> <name> <local:[comment]>";
    }

    @Override
    public String longhelp() {
        return "Adds a playtest to the database. `name` is the name of the playtest. `local`" +
                " shows whether this playtest was submitted as a file or a link; file corresponds to" +
                " `1`, `true` or `file`, link corresponds to `0`, `false` or `link`. `comment` is " +
                "a comment for the playtest. Notice that if the playtest is submitted as a link, `comment`" +
                " changes to required and has to contain a URL.";
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
}
