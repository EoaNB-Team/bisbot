package commands.database.hiatuses;

import commands.interfaces.DBCommand;
import core.ErrorHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Settings;
import util.SharedComRequirements;

import java.awt.*;
import java.util.List;

public class RemoveHiatusCommand implements DBCommand {
    private final String commandName = "hremove";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return SharedComRequirements.checkCuria(event);
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        try {
            String userid;

            try {
                if (args[0].contains("@") && args[0].contains("<")) {
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

            boolean removed = false;
            if (!event.getGuild().retrieveMemberById(event.getAuthor().getId()).complete().getRoles().contains(event.getGuild().getRoleById(Settings.CENTURION))) {
                if (event.getAuthor().getId().equals(userid)) {
                    removed = HiatusManager.removeHiatusFromDB(event, userid);
                } else {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setColor(new Color(200, 0, 0));
                    eb.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
                    eb.setDescription("You can only delete your own hiatus record.");
                    event.getChannel().sendMessage(eb.build()).queue();
                }
            } else {
                removed = HiatusManager.removeHiatusFromDB(event, userid);
            }

            if (removed) {
                Member m = event.getGuild().retrieveMemberById(userid).complete();
                List<Role> mr = m.getRoles();
                Role r = event.getGuild().getRoleById(Settings.HIATUS);
                if (mr.contains(r)) {
                    event.getGuild().removeRoleFromMember(m.getIdLong(), r).queue();
                    ErrorHandler.CustomEmbed(":white_check_mark: " + "Removed " + r.getName() + " role.", new Color(3, 193, 19), event);
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
        return Settings.prefix + commandName + " <user>";
    }

    @Override
    public String longhelp() {
        return "Removes a hiatus record from the database. Deleting other people's records is only possible for Centurions.";
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
}
