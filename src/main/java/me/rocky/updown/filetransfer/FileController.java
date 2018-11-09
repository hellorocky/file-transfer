package me.rocky.updown.filetransfer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/")
public class FileController {

    @Value("${file-store-path}")
    private String fileStorePath;

    @GetMapping("/{filename}")
    public String index(@PathVariable String filename, HttpServletResponse response){
        Path path = Paths.get(fileStorePath+filename);
        FileSystemResource fileSystemResource= new FileSystemResource(path);
        fileSystemResource.getInputStream();
        response.
        return "Hello, World!";
    }

    @PutMapping("/{filename}")
    public String upload(@PathVariable String filename, @RequestBody byte[] data){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileStorePath + filename);
            fileOutputStream.write(data);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "OK";
    }
}
