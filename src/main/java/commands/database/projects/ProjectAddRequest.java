package commands.database.projects;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@Deprecated
public class ProjectAddRequest {
	private final MessageReceivedEvent event;
	private final String projectid;
	private final String userid;
	private final String username;
	private final String comment;

	public ProjectAddRequest(MessageReceivedEvent event, String projectid, String userid, String username, String comment) {
		this.event = event;
		this.projectid = projectid;
		this.userid = userid;
		this.username = username;
		this.comment = comment;
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

	public String GetProjectID() {
		return projectid;
	}

	public String GetComment() {
		return comment;
	}
}
