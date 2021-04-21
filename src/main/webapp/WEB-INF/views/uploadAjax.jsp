<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
.uploadResult {
width:100%;
background:gray;
}
.uploadResult ul {
display:flex;
flex-flow: row;
justify-content: center;
align-items: center;
}
.uploadResult ul li {
list-style: none;
padding: 10px;
}
.uploadResult ul li img {
width: 20px;
}
</style>
<title>Insert title here</title>
</head>
<body>
<h1>Upload with Ajax</h1>

<div class='uploadDiv'>
	<input type='file' name='uploadFile' multiple>
</div>

<button id='uploadBtn'>Upload</button>
<!-- 첨부파일 이름을 목록으로 처리하는 div -->
<div class="uploadResult">
<ul>

</ul>
</div>

<!-- jquery 라이브러리의 경로 추가 : jquery cdn으로 검색 -->
<script src="https://code.jquery.com/jquery-3.3.1.min.js" 
integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" 
crossorigin="anonymous"></script>

<script>
$(document).ready(function(){
	
	var uploadResult = $(".uploadResult ul");
	
	//initialize input type='file'
	//첨부파일을 업로드하기 전에 아무 내용이 없는 <input type='file'> 객체가 포함된 <div>를 clone
	var cloneObj = $(".uploadDiv").clone();
	 	
	// 파일의 확장자나 크기의 사전 처리
	var regex = new RegExp("(.*?)\.(exe|sh|zip|alz|xml)$");
	var maxSize = 5242880; // 5MB
	
	function checkExtension(fileName, fileSize) {
		if (fileSize >= maxSize) {
			alert("파일 사이즈 초과");
			return false;
		}
		
		if (regex.test(fileName)) {
			alert("해당 확장자의 파일 업로드 불가");
			return false;
		}
		return true;
	}
	
	$("#uploadBtn").on("click", function(e){
		var formData = new FormData();
		var inputFile = $("input[name='uploadFile']");
		var files = inputFile[0].files;
		//console.log(files);
		for (var i=0; i<files.length; i++) {
			if ( !checkExtension(files[i].name, files[i].size) ) { // for 루프에서 checkExtension()을 호출해서 확장자와 파일의 크기를 체크
				return false;				
			}
			formData.append("uploadFile", files[i]);
		}
		//결과 데이터를 JavaScript를 이용해서 반환된 정보를 처리하도록 수정
		$.ajax({
			url: '/uploadAjaxAction',
			processData: false,
			contentType: false,
			data: formData,
			type: 'POST',
			dataType: 'json', // Ajax를 호출했을 때 결과 타입(dataType) : json
			success: function(result){
				
				console.log(result);
				
				showUploadedFile(result);
				
				//첨부파일을 업로드한 후에는 복사된 객체를 <div>내에 다시 추가해서 첨부파일에 대한 정보를 초기화
				$(".uploadDiv").html(cloneObj.html());
			}
		});
				
		/* 
		$.ajax({
			url: '/uploadAjaxAction',
			processData: false,
			contentType: false,
			data: formData,
			type: 'POST',
			success: function(result){
				alert("Uploaded");
			}
		});
		 */
	})
	/* 
	//사전처리 없는 경우
	//Ajax를 이용하는 파일 업로드는 FormData(가장 중요한 객체)를 이용해서 필요한 파라미터를 담아서 전송
	$("#uploadBtn").on("click", function(e){
		var formData = new FormData(); // jQuery를 이용하는 파일 업로드는 FormData라는 객체를 이용. (브라우저의 제약이 있음) 가상의 form태그 기능
		var inputFile = $("input[name='uploadFile']");
		var files = inputFile[0].files;
		//console.log(files);
		//add filedata to formdata
		
		for (var i=0; i<files.length; i++) {
			formData.append("uploadFile", files[i]); // 첨부파일 데이터를 (FormData) formData에 추가하고
		}
		
		$.ajax({
			url : '/uploadAjaxAction',
			processData : false, // false로 지정해야만 전송이 가능
			contentType : false, // false로 지정해야만 전송이 가능
			data : formData, // Ajax를 통해서 formData 자체를 전송
			type : 'POST',
			success : function(result) {
				alert("Uploaded");
			}
		});
		
	});
	 */
	 
	 function showUploadedFile(uploadResultArr) { // JSON 데이터를 받아서 해당 파일의 이름을 str에 추가. Ajax에서 showUploadedFile()을 호출
		 var str = "";
		 $(uploadResultArr).each(function(i, obj) {
			 //이미지가 아닌 일반 파일의 썸네일
			 if (!obj.image) {
				 str += "<li><img src='/resources/img/rosie.jpg'>" + obj.fileName + "</li>";
			 }
			 else {
				 str += "<li>" + obj.fileName + "</li>";	//json key명 - DTO 변수명 일치하는지 확인할 것
				 //console.log(obj); 
			 }
			
		 });
		 //console.log(str);
		 uploadResult.append(str);
	 }
	 
});
</script>

</body>
</html>