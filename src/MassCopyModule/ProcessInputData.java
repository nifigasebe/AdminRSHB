package MassCopyModule;

import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.*;
import java.net.InetAddress;
import java.util.*;

/**
 * Created by Chizhov-AS on 06.05.2015.
 */

public class ProcessInputData {

    private ArrayList<String> isReachable = new ArrayList<String>();
    private ArrayList<String> isUnReachable = new ArrayList<String>();

    private ArrayList<String> unknownHosts = new ArrayList<String>();

    public ArrayList<String> getIsReachable() {
        return isReachable;
    }

    public ArrayList<String> getIsUnReachable() {
        return isUnReachable;
    }

    public ArrayList<String> getUnknownHosts() {
        return unknownHosts;
    }

    public ProcessInputData (String[] targetsList){

        checkWorkstations(targetsList);
    }

    private void checkWorkstations (String[] targets){
        String[] checkedTargets = new String[targets.length];
        for (int i=0; i<targets.length;i++){
            checkedTargets[i] = targets[i].trim().toUpperCase();
        }
        Set<String> hashSet = new HashSet<String>(Arrays.asList(checkedTargets)); // Убираем дубликаты
        checkedTargets = hashSet.toArray(new String[hashSet.size()]);
        ping(checkedTargets);
    }

    private void ping (String[] targetsList) {

        for (String hostname : targetsList) {
            //GUIMassCopyController.targetsProgressBar.setProgress(0.8);
            try {
                InetAddress inetAddress = InetAddress.getByName(hostname);
                boolean isReachable = inetAddress.isReachable(3000);
                if (isReachable) {
                    this.isReachable.add(hostname);
                } else {
                    this.isUnReachable.add(hostname);
                }
            } catch (UnknownHostException uhEX) {
                unknownHosts.add(hostname);
            } catch (IOException ioEX) {
                //ignore
            }
        }
    }

    public static Path getValidPath (String path) throws InvalidPathException{
        Path testPath = Paths.get(path);
        Path validPath = testPath.toAbsolutePath();
        return validPath;
    }
}