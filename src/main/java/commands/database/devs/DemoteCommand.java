package commands.database.devs;

import commands.interfaces.DBCommand;
import core.EmbedGenerator;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.Settings;
import util.SharedComRequirements;

import java.awt.*;
import java.util.List;

public class DemoteCommand implements DBCommand {
	private final String commandName = "demote";

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		return SharedComRequirements.checkCenturion(event);
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		String userid;
		try {
			if (args[0].contains("@")) {
				userid = args[0].replace("<", "").replace(">", "").replace("@", "").replace("!", "");
			} else {
				EmbedGenerator.CustomEmbedError("Invalid user. Use `@Username` (ping).", event);
				return;
			}
		} catch (Exception e) {
			EmbedGenerator.CustomEmbedError("Invalid user.", event);
			return;
		}
		Member m = event.getGuild().getMemberById(userid);
		List<Role> mr = m.getRoles();
		Role[] sr = new Role[4];
		sr[0] = event.getGuild().getRoleById(Settings.CIVITATE);
		sr[1] = event.getGuild().getRoleById(Settings.SENATE);
		sr[2] = event.getGuild().getRoleById(Settings.DISCIPLIO);
		sr[3] = event.getGuild().getRoleById(Settings.CURIA);

		//a dev
		if (mr.contains(sr[3]) && !mr.contains(sr[2])) {
			String ridf = "";
			for (String rid : Settings.procuratores.values()) {
				if (mr.contains(event.getGuild().getRoleById(rid))) {
					ridf = rid;
				}
			}
			event.getGuild().addRoleToMember(m.getIdLong(), sr[2]).queue();
			event.getGuild().removeRoleFromMember(m.getIdLong(), event.getGuild().getRoleById(ridf)).queue();
			//event.getGuild().getController().removeSingleRoleFromMember(m, event.getGuild().getRoleById(ridf)).queue();
			//event.getGuild().getController().addSingleRoleToMember(m, sr[2]).queue();
			EmbedGenerator.CustomEmbed(":white_check_mark: Procuratores removed from " + m.getEffectiveName() + ".", new Color(3, 193, 19), event);
		}
		//a disciplio
		else if (mr.contains(sr[2])) {
			String ridf = "";
			for (String rid : Settings.zones.values()) {
				if (mr.contains(event.getGuild().getRoleById(rid))) {
					ridf = rid;
				}
			}
			event.getGuild().removeRoleFromMember(m.getIdLong(), sr[2]).queue();
			event.getGuild().removeRoleFromMember(m.getIdLong(), sr[3]).queue();
			event.getGuild().removeRoleFromMember(m.getIdLong(), event.getGuild().getRoleById(ridf)).queue();
			//event.getGuild().getController().removeRolesFromMember(m, sr[2], sr[3], event.getGuild().getRoleById(ridf)).queue();
			EmbedGenerator.CustomEmbed(":white_check_mark: " + sr[2].getName() + ", " + sr[3].getName() + " and Vicarius removed from " + m.getEffectiveName() + ".", new Color(3, 193, 19), event);
			DevManager.deleteDevFromDB(event, userid);
		}
		//a senate
		else if (mr.contains(sr[1])) {
			event.getGuild().removeRoleFromMember(m.getIdLong(), sr[1]).queue();
			//event.getGuild().getController().removeRolesFromMember(m, sr[1]).queue();
			EmbedGenerator.CustomEmbed(":white_check_mark: " + sr[1].getName() + " removed from " + m.getEffectiveName() + ".", new Color(3, 193, 19), event);
		}
		//a civitate
		else if (mr.contains(sr[0])) {
			event.getGuild().removeRoleFromMember(m.getIdLong(), sr[0]).queue();
			//event.getGuild().getController().removeRolesFromMember(m, sr[0]).queue();
			EmbedGenerator.CustomEmbed(":white_check_mark: " + sr[0].getName() + " removed from " + m.getEffectiveName() + ".", new Color(3, 193, 19), event);
		}
		//reached end
		else {
			EmbedGenerator.CustomEmbedError("Cannot demote " + m.getEffectiveName() + " any further.", event);
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
		return "Demotes a user.";
	}

	@Override
	public String getCommandName() {
		return commandName;
	}
}
