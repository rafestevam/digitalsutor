package br.com.intelliapps.digitalsutor.components;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.com.intelliapps.digitalsutor.models.UploadFileResponse;
import br.com.intelliapps.digitalsutor.services.FileStorageService;

@Component
public class FileComponent {

	@Autowired
	private FileStorageService fileStorageService;
	
	public UploadFileResponse uploadFile(MultipartFile file) {
		String fileName = fileStorageService.storeFile(file);
		
//		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//				.path("/donwloadFile/")
//				.path(fileName)
//				.toUriString();
		
		return new UploadFileResponse(fileName, file.getContentType(), file.getSize());
	}
	
	public List<UploadFileResponse> uploadMultipleFiles(MultipartFile[] files){
		return Arrays.asList(files)
				.stream()
				.map(file -> uploadFile(file))
				.collect(Collectors.toList());
	}
	
	public Resource getFileResource(String fileName) {
		return fileStorageService.loadFileAsService(fileName);
	}
	
}
