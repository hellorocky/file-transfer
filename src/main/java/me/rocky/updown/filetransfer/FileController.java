package me.rocky.updown.filetransfer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/")
public class FileController {

    @Value("${file-store-path}")
    private String fileStorePath;

    @GetMapping("/{filename}")
    public void index(@PathVariable String filename, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream"); //意思是 未知的应用程序文件 ，浏览器一般不会自动执行或询问执行。浏览器会像对待 设置了HTTP头Content-Disposition 值为 attachment 的文件一样来对待这类文件。
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8")); //设置上面的其实就不用设置该项配置了
        Path path = Paths.get(fileStorePath+filename);
        FileSystemResource fileSystemResource= new FileSystemResource(path);
        InputStream inputStream = fileSystemResource.getInputStream();
        OutputStream outputStream = response.getOutputStream();
        byte[] buff = new byte[1024];
        int length = 0;
        while ((length = inputStream.read(buff)) != -1) {
            outputStream.write(buff, 0, length);
        }
        outputStream.close();
        inputStream.close();
    }


    @GetMapping("/down/{filename}")
    public ResponseEntity<InputStreamResource> down(@PathVariable String filename, HttpServletResponse response) throws IOException {
        Path path = Paths.get(fileStorePath+filename);
        FileSystemResource fileSystemResource= new FileSystemResource(path);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(fileSystemResource.contentLength())
                .body(new InputStreamResource(fileSystemResource.getInputStream()));
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
