package commands.database.projects;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@Deprecated
public class ProjectRemoveRequest {
	private final MessageReceivedEvent event;
	private final String userid;
	private final String username;

	public ProjectRemoveRequest(MessageReceivedEvent event, String userid, String username) {
		this.event = event;
		this.userid = userid;
		this.username = username;
	}

	public MessageReceivedEvent GetEvent() {
		return event;
	}

	public String GetUserID() {
		return userid;
	}

	public String GetUsername() {
		return username;
	}
}
