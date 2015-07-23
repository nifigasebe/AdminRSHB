package MassCopyModule;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by alexeychizhov on 04.05.2015.
 */

class MaassCopyFileVisitor implements FileVisitor{

    private Path source;
    private Path target;

    public MaassCopyFileVisitor(Path source,Path target){
        this.source = source;
        this.target = target;
    }

    @Override
    public FileVisitResult preVisitDirectory(Object dir, BasicFileAttributes attrs) throws IOException {
        Path path = (Path)dir;
        Path newDestination = target.resolve((source.relativize(path))); //Todo Разбраться нахера такой обрабатывать путь
        try {
            Files.copy(path,newDestination, StandardCopyOption.REPLACE_EXISTING);
        }catch (DirectoryNotEmptyException dneex){
            // ignore
        }
        catch (FileAlreadyExistsException faeex){
            // ignore
        }
        catch (IOException ioex){
            System.err.format("Unable to create: %s: %s%n", newDestination, ioex); //TODO Разобраться что это за хрень
            ioex.printStackTrace();
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Object file, BasicFileAttributes attrs) throws IOException {
        Path path = (Path)file;
        Path newDestination = target.resolve((source.relativize(path)));
        try {
            Files.copy(path,newDestination,StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException ioex){
            ioex.printStackTrace();
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Object file, IOException exc) throws IOException {
       return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Object dir, IOException exc) throws IOException {
        System.out.println("Finish " + dir);
        return FileVisitResult.CONTINUE;
    }
}
