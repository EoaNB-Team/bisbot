package core;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class EmbedGenerator {
	public static void CustomEmbedError(String desc, MessageReceivedEvent event) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setColor(new Color(200, 0, 0));
		eb.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
		eb.setDescription(":x: " + desc);

		event.getChannel().sendMessage(eb.build()).queue();
	}

	public static void CustomEmbed(String desc, Color color, MessageReceivedEvent event) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setColor(color);
		eb.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
		eb.setDescription(desc);

		event.getChannel().sendMessage(eb.build()).queue();
	}

	public static void SuccessEmbed(String desc, MessageReceivedEvent event) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setColor(new Color(3, 193, 19));
		eb.setFooter("edbotJ", event.getJDA().getSelfUser().getAvatarUrl());
		eb.setDescription(":white_check_mark: " + desc);

		event.getChannel().sendMessage(eb.build()).queue();
	}
}
