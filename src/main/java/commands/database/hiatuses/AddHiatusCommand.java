package commands.database.hiatuses;

import commands.interfaces.DBCommand;
import core.ErrorHandler;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Settings;
import util.SharedComRequirements;

import java.awt.*;
import java.util.List;

public class AddHiatusCommand implements DBCommand {
    private final String commandName = "hiatus";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return SharedComRequirements.checkCuria(event);
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        try {
            String userid;
            String username;
            String reason;
            String start;
            String end;

            try {
                if (args[0].contains("@")) {
                    userid = args[0].replace("<", "").replace(">", "").replace("@", "").replace("!", "");
                    username = event.getJDA().retrieveUserById(userid).complete().getName();
                } else {
                    ErrorHandler.CustomEmbedError("Invalid user. Use `@Username` (ping).", event);
                    return;
                }
            } catch (Exception e) {
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

				if (end.equals(start)) {
					ErrorHandler.CustomEmbedError("`start` and `end` cannot be the same.", event);
					return;
				}
            } else {
                ErrorHandler.CustomEmbedError("Invalid end date. Use `YYYY-MM-DD`.", event);
                return;
            }

            if (args.length > 3) {
                reason = args[3];
            } else {
                ErrorHandler.CustomEmbedError("Invalid reason.", event);
                return;
            }

            boolean added = HiatusManager.addHiatusToDB(event, userid, username, reason, end, start);
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
        return Settings.prefix + commandName + " <user> <start date> <end date> <reason>";
    }

    @Override
    public String longhelp() {
        return "Adds a hiatus record to the database. Dates have to be in `YYYY-MM-DD` format. `reason` has to be inside \"s.";
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
}
