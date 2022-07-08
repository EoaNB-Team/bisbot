package commands.mariadb.devs;

import commands.interfaces.AdminCommand;
import commands.interfaces.DBCommand;
import core.ErrorHandler;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;
import util.Secrets;
import util.SharedComRequirements;

import java.awt.*;
import java.util.List;

public class comAddAllDevs implements AdminCommand, DBCommand {
    private final String commandName = "devall";

    @Override
    public boolean called(String[] Args, MessageReceivedEvent event) {
        return SharedComRequirements.checkCenturion(event);
    }

    @Override
    public void action(String[] Args, MessageReceivedEvent event) {
        boolean ret = true;
        try {
            if (Args.length > 0) {
                ret = Boolean.parseBoolean(Args[0]);
            }
        } catch (Exception e) {
            ErrorHandler.CustomEmbedError("`hide` is not boolean. Use either `true`, `false`, `1` or `0`.", event);
            return;
        }
        DevManager.ClearDevsDB(event);
        Guild g = event.getGuild();
        Role r = g.getRoleById(Secrets.CURIA);
        //List<Member> l = new ArrayList<>();

        if (ret) {
            g.loadMembers(member -> {
                if (member.getRoles().contains(r)) {
                    work(event, member, true);
                }
            });
        } else {
            g.loadMembers(member -> {
                if (member.getRoles().contains(r)) {
                    work(event, member, false);
                }
            });
        }

        /*for (Member m : l) {
            System.out.println(m.getEffectiveName());
            List<Role> lr = m.getRoles();
            String vic = "None";
            for (Role mr : lr) {
                if (StringUtils.containsIgnoreCase(mr.getName(), "Zone")) {
                    vic = mr.getName();
                    break;
                }
            }
            Main.DevManager.AddDevToDB(event, m.getUser().getId(), m.getUser().getName(), vic, ret);
        }*/
        ErrorHandler.CustomEmbed(":white_check_mark: Task completed.", new Color(3, 193, 19), event);
    }

    public void work(MessageReceivedEvent event, Member m, boolean ret) {
        List<Role> lr = m.getRoles();
        String vic = "None";
        for (Role mr : lr) {
            if (StringUtils.containsIgnoreCase(mr.getName(), "Zone")) {
                vic = mr.getName();
                break;
            }
        }
        DevManager.AddDevToDB(event, m.getUser().getId(), m.getUser().getName(), vic, ret);
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return Secrets.prefix + commandName + " [hide]";
    }

    @Override
    public String longhelp() {
        return "Adds all devs to the dev database by clearing it first and pulling all devs after. Use with caution! `[hide]` boolean decides whether the bot should hide success messages (default `true`).";
    }

    @Override
    public String getCommandName() {
        return commandName;
    }
}
