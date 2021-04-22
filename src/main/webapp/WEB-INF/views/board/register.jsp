<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp" %>
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Board Register</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Board Register
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        	<form role="form" action="/board/insert" method="post">
                        		<div class="form-group">
                        			<label>Title</label><input class="form-control" name="title">
                        		</div>
                        		<div class="form-group">
                        			<label>Text Area</label>
                        			<textarea class="form-control" rows="3" name="content"></textarea>
                        		</div>
                        		<div class="form-group">
                        			<label>Writer</label><input class="form-control" name="writer">
                        		</div>
                        		<button type="submit" class="btn btn-default">Submit Button</button>
                        		<button type="reset" class="btn btn-default">Reset Button</button>
                        	</form>
                            
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-6 -->
            </div>
            <!-- /.row -->
            
            <!-- 첨부파일 등록 처리 -->
            <div class='row'>
            	<div class='col-lg-12'>
            		<div class='panel panel-default'>
            			<div class='panel-heading'>File Attach</div>
            			<!-- /.panel-heading -->
            			<div class='panel-body'>
            				<div class='form-group uploadDiv'>
            					<input type='file' name='uploadFile' multiple>
            				</div>
            				
            				<div class='uploadResult'>
            					<ul>
            					
            					</ul>
            				</div>
            				<!-- end panel-body -->
            			</div>
            		</div>
            	</div>
            </div>
            <!-- 파일을 선택하거나 Submit Button을 클릭했을 때 JavaScript 처리 -->
            <script>
            $(document).ready(function(e){
            	var formObj = $("form[role='form']"); //line 21
            	//첨부파일 정보 포함하는 전송
            	$("button[type='submit']").on("click", function(e){
            		e.preventDefault();
            		console.log("submit clicked");
            		var str = "";
            		$(".uploadResult ul li").each(function(i, obj){
            			var jobj = $(obj);
            			console.dir(jobj);
            			
            			//BoardAttachVO의 변수와 일치
            			str += "<input type='hidden' name='attachList[" + i + "].fileName' value='" + jobj.data("filename") + "'>";
            			str += "<input type='hidden' name='attachList[" + i + "].uuid' value='" + jobj.data("uuid") + "'>";
            			str += "<input type='hidden' name='attachList[" + i + "].uploadPath' value='" + jobj.data("path") + "'>";
            			str += "<input type='hidden' name='attachList[" + i + "].fileType' value='" + jobj.data("type") + "'>";
            			//console.log(str);
            		});
            		//form태그가 submit될 때 같이 전송
            		formObj.append(str).submit();
            	});
            	
            	var regex = new RegExp("(.*?)\.(exe|sh|zip|alz|xml)$");
            	var maxSize = 5242880; // 5MB
            	
            	function checkExtension(fileName, fileSize) {
            		if (fileSize >= maxSize) {
            			alert("파일 사이즈 초과");
            			return false;
            		}
            		if (regex.test(fileName)) {
            			alert("해당 종류의 파일은 업로드 불가");
            			return false;
            		}
            		return true;
            	}
            	// 파일의 업로드는 별도의 업로드 버튼을 두지 않고 <input type='file'>의 내용이 변경되는 것을 감지해서 처리
            	$("input[type='file']").change(function(e){
            		var formData = new FormData();
            		var inputFile = $("input[name='uploadFile']");
            		var files = inputFile[0].files;
            		for (var i=0; i<files.length; i++) {
            			if (!checkExtension(files[i].name, files[i].size)) {
            				return false;
            			}
            			formData.append("uploadFile", files[i]);
            		}
            		$.ajax({
            			url: '/uploadAjaxAction',
            			processData: false,
            			contentType: false,
            			data: formData,
            			type:'POST',
            			dataType: 'json',
            			success: function(result) {
            				console.log(result);
            				//showUploadResult() 생성 후 활성화
            				showUploadResult(result); // 업로드 결과 처리 함수
            			}
            		});
            	});
            	
            	function showUploadResult(uploadResultArr) {
            		if(!uploadResultArr || uploadResultArr.length == 0 ) {return;}
            		var uploadUL = $(".uploadResult ul");
            		var str ="";
            		$(uploadResultArr).each(function(i, obj) {
            			//image type
            			if (obj.image) {
            				var fileCallPath = encodeURIComponent(obj.uploadPath + "/thumbnail_"
            						+ obj.uuid + "_" + obj.fileName);
            				//첨부파일과 관련된 정보 data-uuid, data-filename, data-type 추가
            				str += "<li data-path='" + obj.uploadPath + "' data-uuid='" + obj.uuid + "' data-filename='" + obj.fileName + "' data-type='" + obj.image +"'><div>";
            				str += "<span> " + obj.fileName + "</span>";
            				//첨부파일 삭제를 위해 data-file, data-type 추가
            				str += "<button type='button' data-file=\'" + fileCallPath + "\' data-type='image' class='btn btn-warning btn-circle'>" +
            						"<i class='fa fa-times'></i></button><br>";
            				str += "<img src='/display?fileName=" +fileCallPath + "'>";
            				str += "</div></li>";
            			} else {
            				var fileCallPath = encodeURIComponent(obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName);
            				var fileLink = fileCallPath.replace(new RegExp(/\\/g), "/");
            				//첨부파일과 관련된 정보 data-uuid, data-filename, data-type 추가
            				str += "<li data-path='" + obj.uploadPath + "' data-uuid='" + obj.uuid + "' data-filename='" + obj.fileName + "' data-type='" + obj.image +"'><div>";
            				str += "<span> " + obj.fileName + "</span>";
            				//첨부파일 삭제를 위해 data-file, data-type 추가
            				str += "<button type='button' data-file=\'" + fileCallPath + "\' data-type='file' class='btn btn-warning btn-circle'>" +
            						"<i class='fa fa-times'></i></button><br>";
            				str += "<img src='/resources/img/roseicon.jpg'></a>";
            				str += "</div></li>";
            			}
            		});
            		uploadUL.append(str);
            	}
            	//x버튼 클릭 이벤트
            	$(".uploadResult").on("click", "button", function(e){
            		console.log("delete file");
            		var targetFile = $(this).data("file"); //fileCallPath
            		var type = $(this).data("type");
            		var targetLi = $(this).closest("li");
            		$.ajax({
            			url: '/deleteFile',
            			data: {fileName: targetFile, type: type},
            			dataType: 'text',
            			type: 'POST',
            			success: function(result) {
            				alert(result);
            				targetLi.remove();
            			}
            		});
            	});
            });
            </script>
<%@ include file="../includes/footer.jsp" %>