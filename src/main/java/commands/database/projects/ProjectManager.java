package commands.database.projects;

import core.DatabaseManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@Deprecated
public class ProjectManager {
    public static boolean addProject(MessageReceivedEvent event, String projectid, String projectname, String projectdesc, String projectowner) {
        String target = "/Pub%20Ed/scripts/postProject.php?projectid=" + projectid.replace(" ", "_") + "&projectname=" + projectname.replace(" ", "%20") +"&projectdesc=" + projectdesc.replace(" ", "%20") + "&projectowner=" + projectowner.replace(" ", "%20");
        return DatabaseManager.EstablishConnection(event, target);
    }

    public static boolean deleteProject(MessageReceivedEvent event, String projectid) {
        String target = "/Pub%20Ed/scripts/deleteProject.php?projectid=" + projectid.replace(" ", "_");
        return DatabaseManager.EstablishConnection(event, target);
    }

    public static boolean addUserToProject(MessageReceivedEvent event, String projectid, String userid, String username, String comment) {
        String target = "/Pub%20Ed/scripts/postUserToProject.php?projectid=" + projectid.replace(" ", "_") + "&userid=" + userid + "&username=" + username.replace(" ", "%20") + "&comment=" + comment.replace(" ", "%20");
        return DatabaseManager.EstablishConnection(event, target);
    }

    public static boolean deleteUserFromProject(MessageReceivedEvent event, String userid) {
        String target = "/Pub%20Ed/scripts/deleteUser.php?userid=" + userid;
        return DatabaseManager.EstablishConnection(event, target);
    }
}
