package listeners;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import util.General;
import util.Settings;

import java.util.Objects;

public class ReadyListener extends ListenerAdapter {
	public void onReady(@NotNull ReadyEvent event) {
		if (!Settings.debug) {
			return;
		}

		@NotNull Guild g = Objects.requireNonNull(event.getJDA().getGuildById(Settings.NODEGUILD));
		Member m = g.retrieveMemberById(Settings.OWNER).complete();
		User owner = m.getUser();

		owner.openPrivateChannel().queue((channel) -> {
			try {
				channel.sendMessage(General.getInfoEmbed(event, null, "edbotJ Connected:").build()).queue();
			} catch (Exception ignored) {
			}
		});
	}
}
