package core;

import commands.CommandBucket;
import commands.database.devs.ProcuratoresCommand;
import commands.database.devs.ZonesCommand;
import commands.interfaces.AdminCommand;
import commands.interfaces.Command;
import commands.interfaces.DBCommand;
import commands.interfaces.GeneralCommand;
import listeners.CommandListener;
import listeners.ReactionAddedListener;
import listeners.ReadyListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Settings;

import java.util.List;
import java.util.Objects;

public class Main {
	private static JDA jda;

	public static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] arguments) throws Exception {
		Settings.initSettings();

		ZonesCommand.initZones();
		ProcuratoresCommand.initProcuratores();

		DatabaseManager.init();

		String token = Settings.getTokenM();
		JDABuilder builder = JDABuilder.createDefault(token)
			.setStatus(OnlineStatus.ONLINE)
			.setAutoReconnect(true)
			.setActivity(Activity.listening(Settings.prefix + "help | v" + Settings.VERSION))

			.setChunkingFilter(ChunkingFilter.ALL)
			.enableIntents(GatewayIntent.GUILD_MEMBERS)
			.setMemberCachePolicy(MemberCachePolicy.ALL);

		jda = builder.build();

		addListeners();
		initCommands();
	}

	public static void addListeners() {
		jda.addEventListener(new ReadyListener());
		jda.addEventListener(new CommandListener());
		jda.addEventListener(new ReactionAddedListener());
	}

	public static void initCommands() {
		CommandBucket cb = new CommandBucket();
		CommandHandler.commandBucket = cb;

		for (Command c : cb.getCommands().values()) {
			boolean isAdmin = c instanceof AdminCommand;
			boolean isGeneral = c instanceof GeneralCommand;
			boolean isDB = c instanceof DBCommand;

			String permLevel = isAdmin ? "Admin" : "Dev";
			String pageLevel = isDB ? "DB" : (isGeneral ? "General" : null);
			if (Objects.isNull(pageLevel)) continue;

			List<String> commandHelpRepo = CommandHandler.commandHelpRepos.get(permLevel).get(pageLevel);
			commandHelpRepo.add("```" + c.help() + "```: " + c.longhelp() + "\n");
		}
	}
}
