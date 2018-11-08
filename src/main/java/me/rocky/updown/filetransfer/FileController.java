package me.rocky.updown.filetransfer;

import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/")
public class FileController {
    @GetMapping
    public String index(){
        return "Hello, World!";
    }

    @PutMapping("/{filename}")
    public String upload(@PathVariable String filename,@RequestBody byte[] data){
        System.out.println(filename);
        System.out.println(data);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("/Users/rocky/tmp/up.jpg");
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
