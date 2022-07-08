package commands.mariadb.hiatuses;

import commands.interfaces.DBCommand;
import core.ErrorHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Secrets;
import util.SharedComRequirements;

import java.awt.*;

public class comUpdateHiatus implements DBCommand {
    private final String commandName = "hupdate";

    @Override
    public boolean called(String[] Args, MessageReceivedEvent event) {
        return SharedComRequirements.checkCuria(event);
    }

    @Override
    public void action(String[] Args, MessageReceivedEvent event) {
        try {
            String userid;
            String reason;
            String comment = "";
            String start;
            String end;

            try {
                if (Args[0].contains("@")) {
                    userid = Args[0].replace("<", "").replace(">", "").replace("@", "").replace("!", "");
                } else {
                    ErrorHandler.CustomEmbedError("Invalid user. Use `@Username` (ping).", event);
                    return;
                }
            }
            catch (Exception e) {
                ErrorHandler.CustomEmbedError("Invalid user.", event);
                return;
            }
            if (Args.length > 1 && Args[1].matches("\\d{4}-\\d{2}-\\d{2}")) {
                start = Args[1];
            } else {
                ErrorHandler.CustomEmbedError("Invalid start date. Use `YYYY-MM-DD`.", event);
                return;
            }
            if (Args.length > 2 && Args[2].matches("\\d{4}-\\d{2}-\\d{2}")) {
                end = Args[2];
            } else {
                ErrorHandler.CustomEmbedError("Invalid end date. Use `YYYY-MM-DD`.", event);
                return;
            }
            if (start.equals(end)) {
                ErrorHandler.CustomEmbedError("`start` and `end` cannot be the same.", event);
                return;
            }
            if (Args.length > 3) {
                reason = Args[3];
            } else {
                ErrorHandler.CustomEmbedError("Invalid reason.", event);
                return;
            }
            if (Args.length > 4) {
                comment = Args[4];
            }

            if (!event.getGuild().retrieveMemberById(event.getAuthor().getId()).complete().getRoles().contains(event.getGuild().getRoleById(Secrets.CENTURION))) {
                if (event.getAuthor().getId().equals(userid)) {
                    HiatusManager.UpdateHiatusToDB(event, userid, reason, end, start, comment);
                } else {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setColor(new Color(200, 0, 0));
                    eb.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
                    eb.setDescription("You can only update your own hiatus record.");
                    event.getChannel().sendMessage(eb.build()).queue();
                }
            } else {
                HiatusManager.UpdateHiatusToDB(event, userid, reason, end, start, comment);
            }
        } catch (Exception e) {
            ErrorHandler.CustomEmbedError("Wrong syntax.", event);
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return Secrets.prefix + commandName + " <user> <start date> <end date> <reason> [comment]";
    }

    @Override
    public String longhelp() {
        return "Updates a hiatus record in the database. Updating other people's records is only possible for Centurions.";
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
}
