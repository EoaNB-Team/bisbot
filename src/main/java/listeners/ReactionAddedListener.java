package listeners;

import commands.mariadb.projects.PrjManager;
import commands.mariadb.projects.ProjectAddRequest;
import commands.mariadb.projects.ProjectRemoveRequest;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import util.Secrets;

import java.awt.*;

public class ReactionAddedListener extends ListenerAdapter { // WTF IS HAPPENING HERE TOO MUCH TEXT
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        //adding a project request
        String react = event.getReactionEmote().getName();
        if (event.getChannel().getId().equals(Secrets.projectEndchan) && (event.getMember().getRoles().contains(event.getGuild().getRoleById(Secrets.CENTURION))) && react.equals("✅") || react.equals("❎")) {
            String messageid = event.getReaction().getMessageId();
            Message m = event.getChannel().getHistory().getMessageById(messageid);//.complete();

            if (!m.getEmbeds().isEmpty()) {
                if (!m.getEmbeds().get(0).getFooter().getText().equals("") && !m.getEmbeds().get(0).getFooter().getText().equals("edbotJ")) {
                    ProjectAddRequest req = Secrets.projectReqList.get(m.getEmbeds().get(0).getFooter().getText());
                    ProjectRemoveRequest reqRemove = Secrets.projectRemList.get(m.getEmbeds().get(0).getFooter().getText());

                    if (req != null) {
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
                        if (react.equals("✅")) {
                            if (PrjManager.AddUserToProject(req.GetEvent(), req.GetProjectID(), req.GetUserID(), req.GetUsername(), req.GetComment())) {
                                eb.setColor(new Color(3, 193, 19));
                                eb.setDescription("Hey, " + req.GetEvent().getAuthor().getName() + "! Your project application has been reviewed and accepted by a Centurion.");
                            } else {
                                eb.setColor(new Color(200, 0, 0));
                                eb.setDescription("Hey, " + req.GetEvent().getAuthor().getName() + "! You seem to already be assigned to another project. You can only be a part of one project at a time.\nIf you want to request to be removed from your current project, use the `" + Secrets.prefix + "prjremove` command.");
                            }

                            req.GetEvent().getAuthor().openPrivateChannel().queue((channel) -> channel.sendMessage(eb.build()).queue());

                            eb.setColor(new Color(193, 114, 0));
                            eb.setDescription("Project application for " + req.GetUsername() + " accepted by " + event.getUser().getName() + ".");

                            m.editMessage(eb.build()).override(true).queue();

                            Secrets.projectReqList.remove(m.getEmbeds().get(0).getFooter().getText());
                        } else if (react.equals("❎")) {
                            eb.setColor(new Color(200, 0, 0));
                            eb.setDescription("Hey, " + req.GetEvent().getAuthor().getName() + "! Your project application has been denied. Maybe you already have submitted one before?\nIf you want to request to be removed from your current project, use the `" + Secrets.prefix + "prjremove` command.");

                            req.GetEvent().getAuthor().openPrivateChannel().queue((channel) -> channel.sendMessage(eb.build()).queue());

                            eb.setColor(new Color(193, 114, 0));
                            eb.setDescription("Project dismissal application for " + req.GetUsername() + " denied by " + event.getUser().getName() + ".");

                            m.editMessage(eb.build()).override(true).queue();

                            Secrets.projectReqList.remove(m.getEmbeds().get(0).getFooter().getText());
                        }
                    }

                    if (reqRemove != null) {
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
                        if (react.equals("✅")) {
                            if (PrjManager.DeleteUserFromProject(reqRemove.GetEvent(), reqRemove.GetUserID())) {
                                eb.setColor(new Color(3, 193, 19));
                                eb.setDescription("Hey, " + reqRemove.GetEvent().getAuthor().getName() + "! Your project dismissal application has been reviewed and accepted by a Centurion.");
                            } else {
                                eb.setColor(new Color(200, 0, 0));
                                eb.setDescription("Hey, " + reqRemove.GetEvent().getAuthor().getName() + "! You don't seem to be a part of any projects.\nIf you want to request to be added to a project, use the `" + Secrets.prefix + "prjadd` command.");
                            }

                            reqRemove.GetEvent().getAuthor().openPrivateChannel().queue((channel) -> channel.sendMessage(eb.build()).queue());

                            eb.setColor(new Color(193, 114, 0));
                            eb.setDescription("Project application for " + reqRemove.GetUsername() + " accepted by " + event.getUser().getName() + ".");

                            m.editMessage(eb.build()).override(true).queue();

                            Secrets.projectRemList.remove(m.getEmbeds().get(0).getFooter().getText());
                        } else if (react.equals("❎")) {
                            eb.setColor(new Color(200, 0, 0));
                            eb.setDescription("Hey, " + reqRemove.GetEvent().getAuthor().getName() + "! Your project dismissal application has been denied. Maybe you've just been assigned to one?");

                            reqRemove.GetEvent().getAuthor().openPrivateChannel().queue((channel) -> channel.sendMessage(eb.build()).queue());

                            eb.setColor(new Color(193, 114, 0));
                            eb.setDescription("Project dismissal application for " + reqRemove.GetUsername() + " denied by " + event.getUser().getName() + ".");

                            m.editMessage(eb.build()).override(true).queue();

                            Secrets.projectRemList.remove(m.getEmbeds().get(0).getFooter().getText());
                        }
                    }
                }
            }
        }
    }
}
