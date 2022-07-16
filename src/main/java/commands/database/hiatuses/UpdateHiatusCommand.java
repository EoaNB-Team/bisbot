package commands.database.hiatuses;

import commands.interfaces.DBCommand;
import core.ErrorHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Settings;
import util.SharedComRequirements;

import java.awt.*;

public class UpdateHiatusCommand implements DBCommand {
    private final String commandName = "hupdate";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return SharedComRequirements.checkCuria(event);
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        try {
            String userid;
            String reason;
            String start;
            String end;

            try {
                if (args[0].contains("@")) {
                    userid = args[0].replace("<", "").replace(">", "").replace("@", "").replace("!", "");
                } else {
                    ErrorHandler.CustomEmbedError("Invalid user. Use `@Username` (ping).", event);
                    return;
                }
            }
            catch (Exception e) {
                ErrorHandler.CustomEmbedError("Invalid user.", event);
                return;
            }
            if (args.length > 1 && args[1].matches("\\d{4}-\\d{2}-\\d{2}")) {
                start = args[1];
            } else {
                ErrorHandler.CustomEmbedError("Invalid start date. Use `YYYY-MM-DD`.", event);
                return;
            }
            if (args.length > 2 && args[2].matches("\\d{4}-\\d{2}-\\d{2}")) {
                end = args[2];
            } else {
                ErrorHandler.CustomEmbedError("Invalid end date. Use `YYYY-MM-DD`.", event);
                return;
            }
            if (start.equals(end)) {
                ErrorHandler.CustomEmbedError("`start` and `end` cannot be the same.", event);
                return;
            }
            if (args.length > 3) {
                reason = args[3];
            } else {
                ErrorHandler.CustomEmbedError("Invalid reason.", event);
                return;
            }

            if (!event.getGuild().retrieveMemberById(event.getAuthor().getId()).complete().getRoles().contains(event.getGuild().getRoleById(Settings.CENTURION))) {
                if (event.getAuthor().getId().equals(userid)) {
                    HiatusManager.updateHiatusToDB(event, userid, reason, end, start);
                } else {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setColor(new Color(200, 0, 0));
                    eb.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
                    eb.setDescription("You can only update your own hiatus record.");
                    event.getChannel().sendMessage(eb.build()).queue();
                }
            } else {
                HiatusManager.updateHiatusToDB(event, userid, reason, end, start);
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
        return Settings.prefix + commandName + " <user> <start date> <end date> <reason>";
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
