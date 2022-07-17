package commands.database.hiatuses;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class HiatusRequest {
	private final MessageReceivedEvent event;
	private final String userid;
	private final String username;
	private final String reason;
	private final String comment;
	private final String start;
	private final String end;

	public HiatusRequest(MessageReceivedEvent event, String userid, String username, String reason, String comment, String start, String end) {
		this.event = event;
		this.userid = userid;
		this.username = username;
		this.reason = reason;
		this.comment = comment;
		this.start = start;
		this.end = end;
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

	public String GetReason() {
		return reason;
	}

	public String GetComment() {
		return comment;
	}

	public String GetStart() {
		return start;
	}

	public String GetEnd() {
		return end;
	}
}
