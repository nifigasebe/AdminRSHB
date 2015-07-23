/**
 * Created by Alexey on 10.11.2014.
 */

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;

public class XmlSAXParser extends DefaultHandler {

    File mcafeeFile;
    DataBase dataBase;
    User user;

    public XmlSAXParser(DataBase dataBase) {

        try {
            //this.mcafeeFile = new File("c:\\Temp\\Mdata.xml");
            this.mcafeeFile = new File("\\\\sgo-ap066\\migration\\-McAfeeUsersDATA-\\Report-UserName-IP.xml");
            this.dataBase = dataBase;
        }

        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public XmlSAXParser(File file,DataBase dataBase){

        try {
            this.mcafeeFile = file;
            this.dataBase = dataBase;
        }

        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    boolean tagIsProperty;
    boolean attributIsIP;
    boolean attributIsLogin;

    @Override
    public void startDocument() throws SAXException {

        tagIsProperty = false;
        attributIsIP = false;
        attributIsLogin = false;

        user = new User();

        System.out.println("Start parse XML");
    }

    @Override
    public void startElement(String URI, String localName, String qName, Attributes attributes) throws SAXException {

        if (qName.equals("property")) {

            tagIsProperty = true;

            if (attributes.getValue("name").equals("EPOComputerProperties.IPV6")) {
                attributIsIP = true;
            }

            if (attributes.getValue("name").equals("EPOComputerProperties.UserName")) {
                attributIsLogin = true;
            }
        }
    }

    @Override
    public void characters(char characters[], int start, int length) throws SAXException {

        if (tagIsProperty & attributIsIP) {

            String ip = "";

            for (int i = start; i < start + length; ++i) {
                String c = Character.toString(characters[i]);
                ip += c;
            }

            tagIsProperty = false;
            attributIsIP = false;

            System.out.println(ip);
            user.setIp(ip);
        }

        if (tagIsProperty & attributIsLogin) {

            String login = "";

            for (int i = start; i < start + length; ++i) {
                String c = Character.toString(characters[i]);
                login += c;
            }

            tagIsProperty = false;
            attributIsLogin = false;

            System.out.println(login);
            user.setLogin(login);
            user.insetrInDB();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //System.out.println("End Element :" + qName);

    }

    @Override
    public void endDocument() {
        System.out.println("Stop parse XML");
    }

    public void parse (){

        try {

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XmlSAXParser handler = new XmlSAXParser(dataBase);
            parser.parse(mcafeeFile,handler);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
















/*


    public static void main(String[] args) throws Exception {                                                           // TODO Удалить
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XmlSAXParser handler = new XmlSAXParser();
        parser.parse(new File("c:\\Temp\\Mdata.xml"),handler);
    }
}




 Найти все объекты у котрых EPOComputerProperties.UserName = X
 показать у них поле EPOComputerProperties.IPV6

<object index="4">
<property name="count">2</property>
<property name="EPOComputerProperties.IPV6">10.10.65.8</property>
<property name="EPOComputerProperties.UserName">emdin</property>
</object>


<reports><report<results<object

*/