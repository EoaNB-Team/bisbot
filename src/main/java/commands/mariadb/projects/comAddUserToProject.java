package commands.mariadb.projects;

import commands.interfaces.DBCommand;
import core.ErrorHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Settings;
import util.SharedComRequirements;

import java.awt.*;

public class comAddUserToProject implements DBCommand {
    private final String commandName = "prjadd";

    @Override
    public boolean called(String[] Args, MessageReceivedEvent event) {
        return SharedComRequirements.checkCuria(event);
    }

    @Override
    public void action(String[] Args, MessageReceivedEvent event) {
        try {
            String projectid;
            String userid;
            String username;
            String comment = "";

            try {
                if (Args[0].contains("@")) {
                    userid = Args[0].replace("<", "").replace(">", "").replace("@", "").replace("!", "");
                    username = event.getJDA().retrieveUserById(userid).complete().getName();

                    if (!userid.equals(event.getMessage().getAuthor().getId()) && !event.getGuild().retrieveMemberById(event.getAuthor().getId()).complete().getRoles().contains(event.getGuild().getRoleById(Settings.CENTURION))) {
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
            if (Args.length > 1) {
                projectid = Args[1];
            } else {
                ErrorHandler.CustomEmbedError("Invalid project ID.", event);
                return;
            }
            if (Args.length > 2) {
                comment = Args[2];
            }

            if (!event.getGuild().retrieveMemberById(event.getAuthor().getId()).complete().getRoles().contains(event.getGuild().getRoleById(Settings.CENTURION))) {
                Settings.projectReqList.put(userid, new ProjectAddRequest(event, projectid, userid, username, comment));

                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(new Color(3, 193, 19));
                eb.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
                eb.setDescription(":white_check_mark: Your project application has been sent, " + event.getAuthor().getAsMention() +"! You will be DM'ed once your request has been reviewed by a Centurion, so do not leave the server.");
                event.getChannel().sendMessage(eb.build()).queue();

                eb.setTitle("Project application by " + event.getAuthor().getName() + ":");
                eb.setDescription("Profile: " + event.getAuthor().getAsMention());
                eb.setFooter(userid, event.getJDA().getSelfUser().getAvatarUrl());
                eb.addField("Project ID", "\"" + projectid + "\"", false);
                if (!comment.equals("")) {
                    eb.addField("Comment", "\"" + comment + "\"", false);
                }
                event.getJDA().getTextChannelById(Settings.projectEndchan).sendMessage(eb.build()).queue();
            } else {
                PrjManager.AddUserToProject(event, projectid, userid, username, comment);
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
        return Settings.prefix + commandName + " <user> <projectid> [comment]";
    }

    @Override
    public String longhelp() {
        return "Adds a user to a project. `comment` has to be inside \"s. Note that `projectid` is to be specified without the `p_` prefix.";
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
}
