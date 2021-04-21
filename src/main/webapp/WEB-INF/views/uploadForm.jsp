<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> <!-- 필수. 안넣으면 파일 인식 못함 -->
<title>Insert title here</title>
</head>
<body>

<form action="uploadFormAction" method="post" enctype="multipart/form-data"> <!-- 가장 신경써야 하는 부분 : enctype -->
<input type='file' name='uploadFile' multiple>
<button>Submit</button>

</form>
</body>
</html>