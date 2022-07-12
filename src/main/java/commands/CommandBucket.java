package commands;

import commands.general.*;
import commands.interfaces.Command;
import commands.mariadb.devs.*;
import commands.mariadb.hiatuses.AddHiatusCommand;
import commands.mariadb.hiatuses.RemoveHiatusCommand;
import commands.mariadb.hiatuses.UpdateHiatusCommand;
import commands.mariadb.playtest.*;
import commands.mariadb.projects.AddProjectCommand;
import commands.mariadb.projects.AddUserToProjectCommand;
import commands.mariadb.projects.DeleteProjectCommand;
import commands.mariadb.projects.RemoveUserFromProjectCommand;

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
            new ZonesCommand(),
            new ProcuratoresCommand(),
            new AddUserToProjectCommand(),
            new RemoveUserFromProjectCommand(),
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
            new AddProjectCommand(),
            new DeleteProjectCommand()
        ).collect(Collectors.toList()).forEach(c -> commands.put(c.getCommandName(), c));
    }

    public Map<String, Command> getCommands() {
        return commands;
    }
}
