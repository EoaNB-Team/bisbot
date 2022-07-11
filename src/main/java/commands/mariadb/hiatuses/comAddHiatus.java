package commands.mariadb.hiatuses;

import commands.interfaces.DBCommand;
import core.ErrorHandler;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Settings;
import util.SharedComRequirements;

import java.awt.*;
import java.util.List;

public class comAddHiatus implements DBCommand {
    private final String commandName = "hiatus";

    @Override
    public boolean called(String[] Args, MessageReceivedEvent event) {
        return SharedComRequirements.checkCuria(event);
    }

    @Override
    public void action(String[] Args, MessageReceivedEvent event) {
        try {
            String userid;
            String username;
            String reason;
            String comment = "";
            String start;
            String end;
            try {
                if (Args[0].contains("@")) {
                    userid = Args[0].replace("<", "").replace(">", "").replace("@", "").replace("!", "");
                    username = event.getJDA().retrieveUserById(userid).complete().getName();
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

            boolean added = HiatusManager.AddHiatusToDB(event, userid, username, reason, end, start, comment);
            if (added) {
                Member m = event.getGuild().retrieveMemberById(userid).complete();
                List<Role> mr = m.getRoles();
                Role r = event.getGuild().getRoleById(Settings.HIATUS);
				assert r != null;
                if (!mr.contains(r)) {
					event.getGuild().addRoleToMember(m.getIdLong(), r).queue();
                    ErrorHandler.CustomEmbed(":white_check_mark: " + "Added " + r.getName() + " role.", new Color(3, 193, 19), event);
                }
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
        return Settings.prefix + commandName + " <user> <start date> <end date> <reason> [comment]";
    }

    @Override
    public String longhelp() {
        return "Adds a hiatus record to the database. Dates have to be in `YYYY-MM-DD` format. `reason` and `comment` have to be inside \"s.";
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
}
