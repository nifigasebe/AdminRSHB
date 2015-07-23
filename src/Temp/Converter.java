/**
 * Created by Alexey on 06.11.2014.
 */

import java.net.InetAddress;

public class Converter {

    public String ipToHostname (String ip){
        try {
            InetAddress inetAddress = InetAddress.getByName(ip);
            if (!inetAddress.getHostName().equals(ip)) {
                return inetAddress.getHostName();
            }
            else {
                return null;
            }
        }
        catch (Exception e){ //TODO Разобраться с Exeption в методе inetAddress.getHostName()
            e.printStackTrace();
            return null;
        }
    }

    public String loginToFIO (String login) {
        return null;
    }

    public String loginToEmail (String login) {
        return null;
    }
}


