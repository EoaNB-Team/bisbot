package commands;

import commands.database.devs.*;
import commands.database.hiatuses.AddHiatusCommand;
import commands.database.hiatuses.ListHiatusesCommand;
import commands.database.hiatuses.RemoveHiatusCommand;
import commands.database.hiatuses.UpdateHiatusCommand;
import commands.database.playtest.*;
import commands.database.vote.HoldVoteCommand;
import commands.database.vote.ListVotesCommand;
import commands.database.vote.VoteCommand;
import commands.general.*;
import commands.interfaces.Command;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandBucket {
	private final Map<String, Command> commands = new LinkedHashMap<>();

	public CommandBucket() {
		Stream.of(
			new HelpCommand(),
			new InfoCommand(),
			new AddHiatusCommand(),
			new RemoveHiatusCommand(),
			new UpdateHiatusCommand(),
			new ListHiatusesCommand(),
			new ZonesCommand(),
			new ProcuratoresCommand(),
			new ReqReportCommand(),
			new ReqAllReportsCommand(),
			new RemReportCommand(),
			new AddReportCommand(),
			new ReqAllRequestsCommand(),
			new RemRequestCommand(),
			new AddRequestCommand(),
			new AdminHelpCommand(),
			new SayCommand(),
			new DebugCommand(),
			new AddDevCommand(),
			new RemoveDevCommand(),
			new AddAllDevsCommand(),
			new ClearDevsCommand(),
			new ListVotesCommand(),
			new HoldVoteCommand(),
			new VoteCommand()
		).collect(Collectors.toList()).forEach(c -> commands.put(c.getCommandName(), c));
	}

	public Map<String, Command> getCommands() {
		return commands;
	}
}
