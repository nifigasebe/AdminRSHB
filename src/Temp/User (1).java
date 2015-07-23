/**
 * Created by Alexey on 06.11.2014.
 */
import java.util.ArrayList;

public class User {

    private String fio;
    private String login;
    private String email;
    private String ip;
    private String hostname;
    Converter converter;

    User() {

        fio = null;
        login = null;
        email = null;
        ip = null;
        hostname = null;

        converter = new Converter();
    }

    public void setLogin(String login) {

        this.login = login;
        this.fio = converter.loginToFIO(login);
        this.email = converter.loginToEmail(login);
    }

    public void setIp(String ip) {

        this.ip = ip;
        this.hostname = converter.ipToHostname(ip);
    }

    public void insetrInDB() {
        System.out.println("|||" + fio + " " + login + " " + email + " " + ip + " " + hostname + "|||");
    }

}