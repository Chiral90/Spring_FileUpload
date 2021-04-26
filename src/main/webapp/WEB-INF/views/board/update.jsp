<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp" %>
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Board Detail (Update)</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Board Update Page
                        </div>
                        <%-- <div><%=request.getParameter("no") %></div> --%>
                        <!-- /.panel-heading -->
                        
                        <div class="panel-body">
                        	<form role="form" action="/board/update" method="post">
                        		<!-- pageNum, amount를 받아오기 위해 추가 -->
                        		<input type='hidden' name='pageNum' value='<c:out value="${cri.pageNum }" />'>
                        		<input type='hidden' name='amount' value='<c:out value="${cri.amount }" />'>
                        		<!-- 검색 조건 받아오기 -->
                        		<input type='hidden' name='type' value='<c:out value="${cri.type }" />'>
                        		<input type='hidden' name='keyword' value='<c:out value="${cri.keyword }" />'>
                        		
                        		<!-- 글 번호 -->
                        		<c:forEach items="${board }" var="board">
                        		<div class="form-group">
                        			<label>No</label><input class="form-control" name="no" id="no"
                        			value='<c:out value="${board.no }" />' readonly="readonly">
                        		</div>
                        		<div class="form-group">
                        			<label>Title</label><input class="form-control" name="title"
                        			value="<c:out value='${board.title }' />">
                        		</div>
                        		<div class="form-group">
                        			<label>Text Area</label>
                        			<textarea class="form-control" rows="3" name="content"><c:out value='${board.content }' />
                        			</textarea>
                        		</div>
                        		<div class="form-group">
                        			<label>Writer</label><input class="form-control" name="writer"
                        			value="<c:out value='${board.writer }' />" readonly="readonly">
                        		</div>
                        		<div class="form-group">
                        			<label>RegDate</label>
                        			<input class="form-control" name="regdate" value='<c:out value="${board.regdate }" />' readonly="readonly">
                        		</div>
                        		<div class="form-group">
                        			<label>UpdateDate</label>
                        			<input class="form-control" name="updatedate" value='<c:out value="${board.updateDate }" />' readonly="readonly">
                        		</div>
                        		<button data-oper="update" class="btn btn-default"
                        		type="submit">Update(Modify)</button>
                        		<button data-oper="delete" class="btn btn-danger"
                        		type="submit">Delete</button>
                        		<button data-oper="list" class="btn btn-info"
                        		type="submit">List</button>
                        		</c:forEach>
                        	</form>
                            
                        </div>
                       
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-6 -->
            </div>
            <!-- /.row -->
            
            <!-- 원본 이미지를 보여주는 부분 -->
            <div class='bigPictureWrapper'>
            	<div class='bigPicture'>
            	</div>
            </div>
            <style>
            .uploadResult {width: 100%;background:gray;}
            .uploadResult ul {display:flex;flex-flow:row;justify-content:center;text-align:center;}
            .uploadResult ul li {list-style: none;padding: 10px;align-content:center;text-align:center;}
            .uploadResult ul li img {width:100px;}
            .uploadResult ul li span { color:white;}
            .bigPictureWrapper { position:absolute; display:none; justify-content:center;align-items:center;top:0%;width:100%;height:100%;background-color:gray;z-index:100;background:rgba(255, 255, 255, 0.5);}
            .bigPicture {position:relative;display:flex;justify-content:center;align-items:center;}
            .bigPicture img {width:600px;}
            </style>
            
            <div class='row'>
            	<div class="col-lg-12">
            		<div class="panel panel-default">
            			<div class="panel-heading">Files</div>
            			<div class="panel-body">
            				<div class="form-group uploadDiv">
                        		<input type="file" name='uploadFile' multiple="multiple">
                        	</div>
            				<!-- 첨부파일의 목록을 보여주는 부분 -->
            				<div class='uploadResult'>
            					<ul>
            					
            					</ul>
            				</div>
            			</div>
            		</div>
            	</div>
            </div>
            
            <!-- 첨부파일 보여주는 작업 -->
            <script>
            $(document).ready(function() {
            	var bno =  $("#no").val();

            	$.getJSON("/board/getAttachList", {bno: bno}, function(arr) {
            		//console.log(arr);
            		var str = "";
            		$(arr).each(function(i, attach) {
            			if (attach.fileType) {//image type
    						var fileCallPath = encodeURIComponent(attach.uploadPath + "/thumbnail_" + attach.uuid + "_" + attach.fileName);
    						
    						str += "<li data-path='" + attach.uploadPath+"' data-uuid='" + attach.uuid + "' data-filename='" + attach.fileName + "' data-type='"
    							+ attach.fileType + "' ><div>";
    							
    						str += "<span> " + attach.fileName + "</span>";
    						str += "<button type='button' data-file=\'" + fileCallPath + " \'data-type='image' ";
    						str += "class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
    						
    						str += "<img src='/display?fileName=" + fileCallPath + "'>";
    						str += "</div></li>";
    					} else {
    						str += "<li data-path='" + attach.uploadPath+"' data-uuid'" + attach.uuid + "' data-filename='" + attach.fileName + "' data-type='"
								+ attach.fileType + "' ><div>";
    						str += "<span> " + attach.fileName + "</span><br/>";
    						
    						str += "<button type='button' data-file=\'" + fileCallPath + " \'data-type='image' ";
    						str += "class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
    						
    						str += "<img src='/resources/img/roseicon.jpg'>";
    						str += "</div></li>";
    					}
    				});
    				$(".uploadResult ul").html(str);
            		});
            	
            	// x버튼 클릭 시 사용자의 확인을 거쳐 화면상에서만 사라지도록 하는 작업
            	$(".uploadResult").on("click", "button", function(e) {
            		console.log("delete file");
            		if(confirm("Remove this file? ")) {
            			var targetLi = $(this).closest("li");
            			targetLi.remove();
            		}
            		
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
            	
            	});
            
            </script>
            
            <!-- 버튼에 따라 다른 동작 할 수 있게 -->
            
            <script type="text/javascript">
            $(document).ready(function() {
            	var formObj = $("form");
            	
            	$('button').on("click", function(e) {
            		e.preventDefault();
            		
            		var operation = $(this).data("oper");
                	
                	console.log(operation);
                	
                	if (operation === 'delete') {
                		formObj.attr("action", "/board/delete");
                	} else if (operation === 'list') { // List버튼을 클릭하면
                		//move to list
                		//self.location = "/board/list" //
                		//form을 통한 이동
                		//list 버튼을 클릭할 경우 action속성과 method 속성을 변경
                		formObj.attr("action", "/board/list").attr("method", "get");
                		//"/board/list"로의 이동은 아무런 파라미터가 없기 때문에 form의 모든 내용은 삭제하고 submit()을 진행
                		
                		// form 태그에서 필요한 부분만 잠시 복사해서 보관하고
                		var pageNumTag = $("input[name='pageNum']").clone();
                		var amountTag = $("input[name='amount']").clone();
                		// update.jsp에서 list.jsp로 이동하는 경우 필요한 파라미터만 전송하기 위해 form 태그의 모든 내용을 지우고 다시 추가하는 방식을 이용했으므로 type, keyword를 별도로 저장
                		var type = $("input[name='type']").clone();
                		var keyword = $("input[name='keyword']").clone();
                		
                		formObj.empty(); // form 태그 내의 모든 내용은 지운다
                		formObj.append(pageNumTag);
                		formObj.append(amountTag);
                		//
                		formObj.append(type);
                		formObj.append(keyword);
                		//이후에 코드가 실행되지 않도록 return을 통해서 제어 ??? return 어디?
                	}
                	//수정 시 첨부파일 정보까지 전송
                	else if (operation === 'update') {
                		console.log("update submit clicked");
                		var str = "";
                		$(".uploadResult ul li").each(function(i, obj) {
                			var jobj = $(obj);
                			console.dir(jobj);
                			
                			//BoardAttachVO의 변수와 일치
                			str += "<input type='hidden' name='attachList[" + i + "].fileName' value='" + jobj.data("filename") + "'>";
                			str += "<input type='hidden' name='attachList[" + i + "].uuid' value='" + jobj.data("uuid") + "'>";
                			str += "<input type='hidden' name='attachList[" + i + "].uploadPath' value='" + jobj.data("path") + "'>";
                			str += "<input type='hidden' name='attachList[" + i + "].fileType' value='" + jobj.data("type") + "'>";
                			//console.log(str);
                		});
                		formObj.append(str).submit();
                	}
                	formObj.submit();
            	});
            });
            </script>
            
<%@ include file="../includes/footer.jsp" %>