package org.zerock.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.AttachFileDTO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
public class UploadController {
	
	//GET방식으로 첨부파일을 업로드할 수 있는 화면 처리
	@GetMapping("/uploadForm")
	public void uploadForm() {
		log.info("upload form");
	}
	
	//form태그 방식의 파일 업로드
	@PostMapping("/uploadFormAction")
	public void uploadFormPost(MultipartFile[] uploadFile, Model model) { // MultipartFile : 스프링에서 제공하는 타입. 첨부파일을 여러 개 선택할 수 있으므로 배열 타입으로 설정
		
		String uploadFolder = "D:\\01-STUDY\\upload";
		
		for (MultipartFile multipartFile : uploadFile ) {
			log.info("--------------------------");
			log.info("Upload File Name : " + multipartFile.getOriginalFilename());
			log.info("Upload File Size : " + multipartFile.getSize());
			
			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename()); //File 객체에 파일 저장 위치, 파일이름 저장
			
			try {
				multipartFile.transferTo(saveFile); // transferTo()의 파라미터 : java.io.File의 객체
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
	}
	
	//Ajax를 이용하는 파일 업로드.
	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		log.info("upload ajax");
	}
	
	//AttachFileDTO의 리스트를 반환하는 메서드
	@PostMapping(value="/uploadAjaxAction", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {
		List<AttachFileDTO> list = new ArrayList<>();
		String uploadFolder = "D:\\01-STUDY\\upload";
		
		String uploadFolderPath = getFolder();
		// make folder
		File uploadPath = new File(uploadFolder, uploadFolderPath); //getFolder()호출
		
		if (uploadPath.exists() == false) { // 해당 경로가 있는지 검사하고 폴더를 생성하고 생성된 폴더로 파일을 저장
			uploadPath.mkdirs();
		}
		// make yyyy/MM/dd folder
		
		for (MultipartFile multipartFile : uploadFile) {
			AttachFileDTO attachDTO = new AttachFileDTO();
			
			String uploadFileName = multipartFile.getOriginalFilename();
			
			// IE has file path
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1); // IE의 경우에는 전체 파일 경로가 전송되므로, 마지막 '\'를 기준으로 잘라낸 문자열이 실제 파일 이름
			log.info("only file name : " + uploadFileName);
			attachDTO.setFileName(uploadFileName);
			
			//중복 방지를 위한 UUID 적용
			//첨부파일은 randomUUID()를 이용해서 임의의 값을 생성. 그 값은 원래의 파일 이름과 구분하도록 중간에 '_'를 추가
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			//File saveFile = new File(uploadFolder, uploadFileName);
			try {
				//날짜 경로적용
				File saveFile = new File(uploadPath, uploadFileName);
				multipartFile.transferTo(saveFile);
				
				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadPath(uploadFolderPath);
				
				// 이미지 파일 여부 확인 후 썸네일 생성
				if (checkImageType(saveFile)) {
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "thumbnail_" + uploadFileName));
					//Thumbnailator는 InputStream과 java.io.File 객체를 이용해서 파일을 생성 가능
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100); // 숫자 : 사이즈에 대한 파라미터
					
					thumbnail.close();
				}
				
				//add to List
				list.add(attachDTO);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	/*
	//AttachFileDTO 적용 전
	//Ajax를 이용하는 파일 업로드. form태그 방식의 업로드와 동일하게 MultipartFile 타입을 이용해서 첨부파일 데이터를 처리
	@PostMapping("/uploadAjaxAction")
	public void uploadAjaxPost(MultipartFile[] uploadFile) {
		//log.info("update ajax post....");
		String uploadFolder = "D:\\01-STUDY\\upload";
		
		// make folder
		File uploadPath = new File(uploadFolder, getFolder()); //getFolder()호출
		log.info("upload path : " + uploadPath);
		
		if (uploadPath.exists() == false) { // 해당 경로가 있는지 검사하고 폴더를 생성하고 생성된 폴더로 파일을 저장
			uploadPath.mkdirs();
		}
		// make yyyy/MM/dd folder
		
		for (MultipartFile multipartFile : uploadFile) {
			log.info("-------------------------");
			log.info("Upload File Name : " + multipartFile.getOriginalFilename());
			log.info("Upload File Size : " + multipartFile.getSize());
			
			String uploadFileName = multipartFile.getOriginalFilename();
			
			// IE has file path
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1); // IE의 경우에는 전체 파일 경로가 전송되므로, 마지막 '\'를 기준으로 잘라낸 문자열이 실제 파일 이름
			log.info("only file name : " + uploadFileName);
			
			//중복 방지를 위한 UUID 적용
			//첨부파일은 randomUUID()를 이용해서 임의의 값을 생성. 그 값은 원래의 파일 이름과 구분하도록 중간에 '_'를 추가
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			//File saveFile = new File(uploadFolder, uploadFileName);
			try {
				//날짜 경로적용
				File saveFile = new File(uploadPath, uploadFileName);
			
				multipartFile.transferTo(saveFile);
				
				// 이미지 파일 여부 확인 후 썸네일 생성
				if (checkImageType(saveFile)) {
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "thumbnail_" + uploadFileName));
					//Thumbnailator는 InputStream과 java.io.File 객체를 이용해서 파일을 생성 가능
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100); // 숫자 : 사이즈에 대한 파라미터
					
					thumbnail.close();
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
	}
	*/
	// 오늘 날짜의 경로를 문자열로 생성(년/월/일) 폴더의 생성하는 메서드
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-", File.separator); // 생성된 경로는 폴더 경로를 수정
	}
	
	// 이미지 파일의 판단 - 특정한 파일이 이미지 타입인지 검사하는 별도의 메서드
	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			
			return contentType.startsWith("image");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//썸네일 데이터 전송
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName) { // 파일의 경로가 포함된 fileName을 파라미터로 받고 byte[]를 전송. byte[]로 이미지 파일의 데이터를 전송할 때 주의할 것은 브라우저에 보내는 MIME 타입이 파일의 종류에 따라 달라진다는 점이다
		log.info("fileName : " + fileName);
		File file = new File("D:\\01-STUDY\\upload");
		log.info("file : " + file);
		ResponseEntity<byte[]> result = null;
		
		try {
			HttpHeaders header = new HttpHeaders();
			
			// byte[]로 이미지 파일의 데이터를 전송할 때 주의할 것은 브라우저에 보내는 MIME 타입이 파일의 종류에 따라 달라진다는 점을 해결하기위해 probeContentType()을 통해 적절한 MIME 타입 데이터를 Http의 헤더메시지에 포함되도록 처리
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
