package commands.mariadb.devs;

import core.Main;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Secrets;

import java.awt.*;
import java.util.List;

public class comDemote implements commands.Command{
    @Override
    public boolean called(String[] Args, MessageReceivedEvent event) {
        if (!event.getMessage().getAuthor().getId().equals(event.getJDA().getSelfUser().getId())) {
            if (!event.getGuild().retrieveMemberById(event.getAuthor().getId()).complete().getRoles().contains(event.getGuild().getRoleById("337176399532130307")) && !event.getGuild().retrieveMemberById(event.getAuthor().getId()).complete().getRoles().contains(event.getGuild().getRoleById("489942850725871636"))) {
                Main.ErrorHandler.CustomEmbedError("You have to be a Centurion or Quaestor to be able to execute this command.", event);
                return true;
            }
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public void action(String[] Args, MessageReceivedEvent event) {
        String userid;
        try {
            if (Args[0].contains("@")) {
                userid = Args[0].replace("<", "").replace(">", "").replace("@", "").replace("!", "");
            } else {
                Main.ErrorHandler.CustomEmbedError("Invalid user. Use `@Username` (ping).", event);
                return;
            }
        }
        catch (Exception e) {
            Main.ErrorHandler.CustomEmbedError("Invalid user.", event);
            return;
        }
        Member m = event.getGuild().getMemberById(userid);
        List<Role> mr = m.getRoles();
        Role[] sr = new Role[4];
        sr[0] = event.getGuild().getRoleById("337164158489591809"); //Civitate
        sr[1] = event.getGuild().getRoleById("337170798592917506"); //Senate
        sr[2] = event.getGuild().getRoleById("358939888386703371"); //Disciplio
        sr[3] = event.getGuild().getRoleById("546580860456009760"); //Curia

        //a dev
        if (mr.contains(sr[3]) && !mr.contains(sr[2])) {
            String ridf = "";
            for (String rid : Secrets.procuratores.values()) {
                if (mr.contains(event.getGuild().getRoleById(rid))) {
                    ridf = rid;
                }
            }
            event.getGuild().addRoleToMember(m.getIdLong(), sr[2]).queue();
            event.getGuild().removeRoleFromMember(m.getIdLong(), event.getGuild().getRoleById(ridf)).queue();
            //event.getGuild().getController().removeSingleRoleFromMember(m, event.getGuild().getRoleById(ridf)).queue();
            //event.getGuild().getController().addSingleRoleToMember(m, sr[2]).queue();
            Main.ErrorHandler.CustomEmbed(":white_check_mark: Procuratores removed from " + m.getEffectiveName() + ".", new Color(3, 193, 19), event);
        }
        //a disciplio
        else if (mr.contains(sr[2])) {
            String ridf = "";
            for (String rid : Secrets.vicari.values()) {
                if (mr.contains(event.getGuild().getRoleById(rid))) {
                    ridf = rid;
                }
            }
            event.getGuild().removeRoleFromMember(m.getIdLong(), sr[2]).queue();
            event.getGuild().removeRoleFromMember(m.getIdLong(), sr[3]).queue();
            event.getGuild().removeRoleFromMember(m.getIdLong(), event.getGuild().getRoleById(ridf)).queue();
            //event.getGuild().getController().removeRolesFromMember(m, sr[2], sr[3], event.getGuild().getRoleById(ridf)).queue();
            Main.ErrorHandler.CustomEmbed(":white_check_mark: "+ sr[2].getName() + ", " + sr[3].getName() + " and Vicarius removed from " + m.getEffectiveName() + ".", new Color(3, 193, 19), event);
            DevManager.DeleteDevFromDB(event, userid);
        }
        //a senate
        else if (mr.contains(sr[1])) {
            event.getGuild().removeRoleFromMember(m.getIdLong(), sr[1]).queue();
            //event.getGuild().getController().removeRolesFromMember(m, sr[1]).queue();
            Main.ErrorHandler.CustomEmbed(":white_check_mark: " + sr[1].getName() + " removed from " + m.getEffectiveName() + ".", new Color(3, 193, 19), event);
        }
        //a civitate
        else if (mr.contains(sr[0])) {
            event.getGuild().removeRoleFromMember(m.getIdLong(), sr[0]).queue();
            //event.getGuild().getController().removeRolesFromMember(m, sr[0]).queue();
            Main.ErrorHandler.CustomEmbed(":white_check_mark: " + sr[0].getName() + " removed from " + m.getEffectiveName() + ".", new Color(3, 193, 19), event);
        }
        //reached end
        else {
            Main.ErrorHandler.CustomEmbedError("Cannot demote " + m.getEffectiveName() + " any further.", event);
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return Secrets.prefix + "demote <user>";
    }

    @Override
    public String longhelp() {
        return "Demotes a user.";
    }
}
