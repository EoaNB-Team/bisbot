package commands.mariadb.projects;

import commands.interfaces.DBCommand;
import core.ErrorHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Secrets;
import util.SharedComRequirements;

import java.awt.*;

public class comRemoveUserFromProject implements DBCommand {
    private final String commandName = "prjremove";

    @Override
    public boolean called(String[] Args, MessageReceivedEvent event) {
        return SharedComRequirements.checkCuria(event);
    }

    @Override
    public void action(String[] Args, MessageReceivedEvent event) {
        try {
            String userid;
            String username;

            try {
                if (Args[0].contains("@")) {
                    userid = Args[0].replace("<", "").replace(">", "").replace("@", "").replace("!", "");
                    username = event.getJDA().retrieveUserById(userid).complete().getName();

                    if (!userid.equals(event.getMessage().getAuthor().getId()) && !event.getGuild().retrieveMemberById(event.getAuthor().getId()).complete().getRoles().contains(event.getGuild().getRoleById(Secrets.CENTURION))) {
                        ErrorHandler.CustomEmbedError("Invalid user. Only Centurions can submit project applications for other people.", event);
                        return;
                    }
                } else {
                    ErrorHandler.CustomEmbedError("Invalid user. Use `@Username` (ping).", event);
                    return;
                }
            } catch (Exception e) {
                ErrorHandler.CustomEmbedError("Invalid user.", event);
                return;
            }

            if (!event.getGuild().retrieveMemberById(event.getAuthor().getId()).complete().getRoles().contains(event.getGuild().getRoleById(Secrets.CENTURION))) {
                Secrets.projectRemList.put(userid, new ProjectRemoveRequest(event, userid, username));

                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(new Color(3, 193, 19));
                eb.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
                eb.setDescription(":white_check_mark: Your project dismissal application has been sent, " + event.getAuthor().getAsMention() +"! You will be DM'ed once your request has been reviewed by a Centurion, so do not leave the server.");
                event.getChannel().sendMessage(eb.build()).queue();

                eb.setTitle("Project dismissal application by " + event.getAuthor().getName() + ":");
                eb.setDescription("Profile: " + event.getAuthor().getAsMention());
                eb.setFooter(userid, event.getJDA().getSelfUser().getAvatarUrl());
                event.getJDA().getTextChannelById(Secrets.projectEndchan).sendMessage(eb.build()).queue();
            } else {
                PrjManager.DeleteUserFromProject(event, userid);
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
        return Secrets.prefix + commandName + " <user>";
    }

    @Override
    public String longhelp() {
        return "Removes a user from any projects.";
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
}
