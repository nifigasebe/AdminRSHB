package MassCopyModule;

import java.nio.file.*;
import java.io.IOException;

/**
 * Created by Chizhov-AS on 24.03.2015.
 */
public class MassCopy {

    private String sourceString;
    private String targetString;
    private String[] targetsString;


    MassCopy(String sourcePath, String targetPath, String[] targets){
        this.sourceString = sourcePath;
        this.targetString = targetPath;
        this.targetsString = targets;
    }

    public void startCopy(){

        Path sourcePath = Paths.get(sourceString);
        Path[] targetsPath = new Path[targetsString.length];

        String filter = "(\\D:)\\\\"; // Удаляем "?:\"

        for (int i=0; i<targetsString.length; i++){
            targetsPath[i] = Paths.get("\\\\" + targetsString[i] + "\\c$\\" + (targetString.replaceAll(filter, "")));
            System.out.println(targetsPath[i].toString());
        }

        for (Path tPath : targetsPath){
            try {
                System.out.println("Copy start" + tPath.toString());
                Files.walkFileTree(sourcePath, new MaassCopyFileVisitor(sourcePath, tPath));
                System.out.println("Copy finish" + tPath.toString());
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }
}