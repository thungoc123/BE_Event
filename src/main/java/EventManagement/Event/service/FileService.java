package EventManagement.Event.service;

import EventManagement.Event.service.imp.FileServiceImp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService implements FileServiceImp {

    @Value("${path.save-file}")
    private String pathSaveFile;

    @Override
    public boolean saveFile(MultipartFile file) {
        boolean isSuccess = false;
        try{
            Path root = Paths.get(pathSaveFile);
            if(!Files.exists(root)){
                Files.createDirectories(root);
            }

            Files.copy(file.getInputStream(),root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            isSuccess = true;
        }
        catch (Exception e){
            System.out.println("Error saving file" + e.getMessage()); ;

        }


        return isSuccess;
    }
}