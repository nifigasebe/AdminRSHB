package MassCopyModule;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Chizhov-AS on 07.05.2015.
 */
public class NetworkAddressValidator {

    private Pattern pattern;
    private Matcher matcher;

    private static final String IPADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
             "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
             "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
             "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    /**
     * Validate ip address with regular expression
     * @param ip ip address for validation
     * @return true valid ip address, false invalid ip address
     */
    public boolean ipAdressValidate(String ip){
        pattern = Pattern.compile(IPADDRESS_PATTERN);
        matcher = pattern.matcher(ip);
        return matcher.matches();
    }


}
