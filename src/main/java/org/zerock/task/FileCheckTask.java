package org.zerock.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.zerock.domain.BoardAttachVO;
import org.zerock.mapper.BoardAttachMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
// FileCheckTask 클래스가 정상적으로 동작하는지 확인하기 위해 root-context.xml에 FileCheckTask를 스프링의 빈으로 설정
@Log4j
@Component
public class FileCheckTask {
	//p601 파일의 목록처리
	@Setter(onMethod_ = {@Autowired})
	private BoardAttachMapper attachMapper;
	
	private String getFolderYesterDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String str = sdf.format(cal.getTime());
		return str.replace("-", File.separator);
	}
	
	@Scheduled(cron="0 * * * * *") // cron 속성을 부여해서 주기를 제어 ; cron="0 * * * * *" : 매분 0초마다 한 번씩 실행
	public void checkFiles() throws Exception { // checkFiles()는 매일 새벽 2시에 동작
		
		log.warn("File Check Task run...."); // 로그가 정상적으로 기록되는지 확인하기 위해서 log.warn() 레벨을 이용해서 실행 중에 확인
		log.warn(new Date());
		//file list in database
		List<BoardAttachVO> fileList = attachMapper.getOldFiles(); // 어제 날짜로 보관되는 모든 첨부파일의 목록을 가져옴
		
		// ready for check file in directory with database file list
		
		List<Path> fileListPaths = fileList.stream() // 데이터베이스에서 가져온 파일 목록은 BoardAttachVO 객체이므로 나중에 비교를 위해 Paths의 목록으로 변환
				.map(vo -> Paths.get("D:\\upload", vo.getUploadPath(), vo.getUuid() + "_" + vo.getFileName())).collect(Collectors.toList());
		
		// case of image file ; thumbnail file 이미지 파일의 경우 썸네일 파일도 목록에 필요하기 때문에 별도로 처리해서 목록(fileListPaths)에 추가
		fileList.stream().filter(vo -> vo.isFileType() == true)
		.map(vo -> Paths.get("D:\\upload", vo.getUploadPath(), "thumbnail_" + vo.getUuid() + "_" + vo.getFileName()))
		.forEach(p -> fileListPaths.add(p));
		
		log.warn("====================================");
		
		fileListPaths.forEach(p -> log.warn(p));
		// files in yesterday directory
		File targetDir = Paths.get("D:\\upload", getFolderYesterDay()).toFile();
		
		// 실제 폴더에 있는 파일들의 목록에서 데이터베이스에는 없는 파일을 목록에 추가
		File[] removeFiles = targetDir.listFiles(file -> fileListPaths.contains(file.toPath()) == false);
		
		log.warn("------------------------------------");
		for (File file : removeFiles) { // 삭제 대상이되는 파일들을 삭제
			log.warn(file.getAbsolutePath());
			file.delete();
		}
	}

}
