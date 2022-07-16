package util;

import commands.database.projects.ProjectAddRequest;
import commands.database.projects.ProjectRemoveRequest;
import org.apache.commons.lang3.SystemUtils;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;

public class Settings {
    public static final String CENTURION = "337176399532130307";
    public static final String SENIOR = "825956520374173704";
    public static final String JUNIOR = "755076726480044096";
    public static final String DISCIPLIO = "358939888386703371";
    public static final String CURIA = "546580860456009760";
    public static final String SENATE = "337170798592917506";
    public static final String CIVITATE = "337164158489591809";
    public static final String HIATUS = "363726364605677571";

    public static final String OWNER = "144439625123889152";
    public static final String ADMINCHAN = "532399694442266656";

    public static final String NODEGUILD = "318668421719916545"; //W:286194821394071552 E:318668421719916545
    public static final long STARTTIME = System.nanoTime();
    public static final String VERSION = "1.3.0";

    public static String prefix = "ed!";
    public static boolean debug = false;
    public static HashMap<String, String> zones = new HashMap<>();
    public static HashMap<String, String> zoneChannels = new HashMap<>();
    public static HashMap<String, String> procuratores = new HashMap<>();

    //Hiatus
    //public static HashMap<String, HiatusRequest> hiatusList = new HashMap<>();
    //public static String hiatusEndchan = ADMINCHAN;

    //Projects
    public static final HashMap<String, ProjectAddRequest> projectReqList = new HashMap<>();
    public static final HashMap<String, ProjectRemoveRequest> projectRemList = new HashMap<>();
    public static String projectEndchan = ADMINCHAN;

    private static Properties properties;

    public static void initSettings() throws Exception {
        Properties prop = new Properties();
        FileInputStream fis;
        if (SystemUtils.IS_OS_LINUX) {
            fis = new FileInputStream("/opt/edbotj_dbot/prop.xml");
        } else {
            fis = new FileInputStream("prop.xml");
        }
        prop.loadFromXML(fis);
        properties = prop;
    }

    public static String getTokenM() {
        return getProperty("tokenM");
    }

    public static String getDBPassword() {
        return getProperty("dbPassword");
    }

    public static String getDBHost() {
        return getProperty("dbHost");
    }

	public static String getDBName() {
		return getProperty("dbName");
	}

	public static String getDBUser() {
		return getProperty("dbUser");
	}

    private static String getProperty(String name) {
        if (Objects.isNull(properties)) {
            return null;
        }

        return properties.getProperty(name);
    }
}
