package pl.wpulik.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {
	
	public void saveFile(MultipartFile file, String fileUuidName) {
		String filename = "src/main/resources/static/images/" + fileUuidName;
		try {
			byte[] bytes = file.getBytes();
			File fileUploaded = new File(filename);
			fileUploaded.createNewFile();
			BufferedOutputStream stream = 
					new BufferedOutputStream(new FileOutputStream(fileUploaded));
			stream.write(bytes);
			stream.close();
		}catch(Exception e) {
			System.err.println(e);
		}
		
		
	}

}
