package MassCopyModule;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.nio.file.*;

/**
 * Created by Chizhov-AS on 24.03.2015.
 */
public class GUIMassCopyController {


    private Path sourcePath;
    private Path targetPath;
    String[] targets;
    private String textInResultLabel;

    private boolean okButtonAddOutPath = false;
    private boolean okButtonAddInPath = false;
    private boolean okButtonAddTargets = false;

    @FXML TextField textFieldOutPath;
    @FXML TextField textFieldInPath;
    //------------------------------
    @FXML TextArea textAreaTargets;
    @FXML TextArea textAreaResult;
    //------------------------------
    @FXML Label labelResultPath;
    //------------------------------
    @FXML ProgressBar copyProgressBar;
    @FXML static ProgressBar targetsProgressBar;
    @FXML ProgressIndicator targetsProgressIndicator;
    //------------------------------
    @FXML Button buttonAddOutPath;
    @FXML Button buttonFChooser;
    @FXML Button buttonAddInPath;
    @FXML Button buttonStartCopy;
    @FXML Button buttonAddTargets;
    //------------------------------


    //------------------------Buttons--------------------------------


    @FXML protected void actionbuttonFChooser (ActionEvent event){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder");
        File seceltedDirectory = directoryChooser.showDialog(buttonFChooser.getScene().getWindow());
        if (seceltedDirectory != null){
            sourcePath = ProcessInputData.getValidPath(seceltedDirectory.toString());
            textFieldOutPath.setText(sourcePath.toString());
            labelResultPath.setText(setTextInResultLabel());
            okButtonAddOutPath = true; //Todo переписать
        }
    }

    @FXML protected void actionButtonAddOutPath (ActionEvent event){
        try {
            sourcePath = ProcessInputData.getValidPath(textFieldOutPath.getText());
            textFieldOutPath.setText(sourcePath.toString());
            labelResultPath.setText(setTextInResultLabel());
            okButtonAddOutPath = true;
        }catch (InvalidPathException ipex){
            textAreaResult.setText("Error! This is not a valid source path: " + ipex.getMessage());
        }
    }
    @FXML protected void actionButtonAddInPath(ActionEvent event){
        try {
            targetPath = ProcessInputData.getValidPath(textFieldInPath.getText());
            textFieldInPath.setText(targetPath.toString());
            labelResultPath.setText(setTextInResultLabel());
            okButtonAddInPath = true;
        }catch (InvalidPathException ipex){
            textAreaResult.setText("Error! This is not a valid target path: " + ipex.getMessage());
        }
    }
    @FXML protected void actionButtonAddTargets(ActionEvent event){

        String[] targets = textAreaTargets.getText().split("\n");
        ProcessInputData processInputData = new ProcessInputData(targets);
        this.targets = processInputData.getIsReachable().toArray(new String[processInputData.getIsReachable().size()]);
        textAreaResult.clear(); //ToDo delete

        for (String isReachableTarget : processInputData.getIsReachable() ){
            textAreaResult.appendText("isReachableTarget: " + isReachableTarget + "\n");
        }
        textAreaResult.appendText("\n");
        for (String isUnReachableTarget : processInputData.getIsUnReachable()){
            textAreaResult.appendText("isUnReachableTarget: " + isUnReachableTarget + "\n");
        }
        textAreaResult.appendText("\n");
        for (String unknownHosts : processInputData.getUnknownHosts()){
            textAreaResult.appendText("unknownHosts: " + unknownHosts  + "\n");
        }
        okButtonAddTargets = true;
    }
    @FXML protected void actionButtonStartCopy(ActionEvent event){
        if (okButtonAddInPath & okButtonAddOutPath & okButtonAddTargets){
            textAreaResult.clear();
            MassCopy copy = new MassCopy(this.sourcePath.toString(),this.targetPath.toString(),targets);
            copy.startCopy();
            textAreaResult.appendText("CopyFinish");
        }
        else {
            String copyingCanceled = "";

            if (!okButtonAddInPath){
                copyingCanceled = copyingCanceled + "Is not defined source path! \n";
            }
            if (!okButtonAddOutPath){
                copyingCanceled = copyingCanceled + "Is not defined target path! \n";
            }
            if (!okButtonAddTargets){
                copyingCanceled = copyingCanceled + "Is not defined target list! \n";
            }
            textAreaResult.setText(copyingCanceled);
        }
    }
    //--------------------------------------------------------------

    private String setTextInResultLabel(){
        String sp = "";
        String tp = "";
        if (sourcePath != null){
            sp = sourcePath.toString();
        }
        if (targetPath != null){
            tp = targetPath.toString();
        }
        textInResultLabel = "Source foldrer: " + sp + "  Destination folder: " + tp;
        return textInResultLabel;
    }
}