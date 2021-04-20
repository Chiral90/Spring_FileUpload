<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp" %>

            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Board Detail</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Board Detail Page
                        </div>
                        <%-- <div><%=request.getParameter("no") %></div> --%>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        	<!-- <form role="form" action="#" method="post"> -->
                        		<!-- 글 번호 -->
                        		<c:forEach items="${board }" var="board">
                        		<div class="form-group">
                        			<label>No</label><input class="form-control" name="no"
                        			value='<c:out value="${board.no }" />' readonly="readonly">
                        		</div>
                        		<div class="form-group">
                        			<label>Title</label><input class="form-control" name="title"
                        			value="<c:out value='${board.title }' />" readonly="readonly">
                        		</div>
                        		<div class="form-group">
                        			<label>Text Area</label>
                        			<textarea class="form-control" rows="3" name="content" readonly="readonly"><c:out value='${board.content }' />
                        			</textarea>
                        		</div>
                        		<div class="form-group">
                        			<label>Writer</label><input class="form-control" name="writer"
                        			value="<c:out value='${board.writer }' />" readonly="readonly">
                        		</div>
                        		
                        		<!-- 버튼에 직접 링크 처리 -->
                        		<%-- 
                        		<button data-oper="update" class="btn btn-default">
                        		onclick="location.href='/board/update?no=<c:out value="${board.no }" />'">Update(Modify)</button>
                        		<button data-oper="list" class="btn btn-info"
                        		onclick="location.href='/board/list'">List</button>
                        		--%>
                        		<!-- 다양한 상황을 처리하기 위해 <form> 태그 사용 -->
                        		<button data-oper="update" class="btn btn-default">Update(Modify), Delete</button>
                        		<button data-oper="list" class="btn btn-info">List</button>
                        		<!-- 브라우저에서는 <form> 태그의 내용은 보이지 않고 버튼만 보임 -->
                        		<form id='operForm' action="/board/update" method="get">
                        			<input type='hidden' id='no' name='no' value='<c:out value="${board.no}" />'>
                        			<input type='hidden' id='pageNum' name='pageNum' value='<c:out value="${cri.pageNum }" />'>
                        			<input type='hidden' id='amount' name='amount' value='<c:out value="${cri.amount }" />'>
                        			<!-- 조회 페이지에서 Criteria의 type과 keyword에 대한 처리 -->
                        			<input type='hidden' id='type' name='type' value='<c:out value="${cri.type }" />'>
                        			<input type='hidden' id='keyword' name='keyword' value='<c:out value="${cri.keyword }" />'>
                        		</form>
                        	
                        		</c:forEach>
                        	<!-- </form> -->	                           
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                    
                    <!-- 댓글 목록 처리 -->
                    <div class='panel panel-default'>
                    	<!-- <div class="panel-heading">
                    		<i class="fa fa-comments fa-fw"></i> Reply
                    	</div> -->
                    	<div class="panel-heading">
                    		<i class="fa fa-comments fa-fw"></i> Reply
                    		<button id='addReplyBtn' class='btn btn-primary btn-xs pull-right'>New Reply</button>
                    	</div>
                    	
                    	
                    	<div class="panel-body">
                    		<ul class="chat">
                    			<!-- start reply -->
                    			<!-- <li class="left clearfix" data-rno='12'>
                    				<div>
                    					<div class="header">
                    						<strong class="primary-font">replyer</strong>
                    						<small class="pull-right text-muted">date</small>
                    					</div>
                    					<p>reply</p>
                    				</div>
                    			</li> -->
                    			<!-- end reply -->
                    		</ul>
                    		<!-- end ul -->
                    		<!-- 댓글 페이지 번호 출력 -->
                    		<div class="panel-footer">
                    			
                    		</div>
                    	</div>
                    	
                    </div>
                    
                </div>
                <!-- /.col-lg-6 -->
            </div>
            <!-- /.row -->
            
            <!-- Modal(댓글 작성란) 추가 -->
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
            aria-hidden="true">
            	<div class="modal-dialog">
            		<div class="modal-content">
            			<div class="modal-header">
            				<button type="button" class="close" data-dismiss="modal"
            				aria-hidden="true">&times;</button>
            				<h4 class="modal-title" id="myModalLabel">Reply Modal</h4>
            			</div>
            			<div class="modal-body">
            				<div class="form-group">
            					<label>Reply</label>
            					<input class="form-control" name='reply' value='New Reply!!!'>
            				</div>
            				<div class="form-group">
            					<label>Replyer</label>
            					<input class="form-control" name='replyer' value='replyer'>
            				</div>
            				<div class="form-group">
            					<label>Reply Date</label>
            					<input class="form-control" name='replyDate' value=''>
            				</div>
            			</div>
            			<div class="modal-footer">
            				<button id="modalModBtn" type="button" class="btn btn-warning" data-dismiss="modal">Modify</button>
            				<button id="modalRemoveBtn" type="button" class="btn btn-danger">Remove</button>
            				<button id="modalRegisterBtn" type="button" class="btn btn-default">Register</button>
            				<button id="modalCloseBtn" type="button" class="btn btn-defualt">Close</button>
             			</div>
             		</div>
             	</div>
            
        	</div>
        	<!-- end Modal Window -->
            
            <!-- 댓글 관련 JavaScript 모듈 -->
            <script type="text/javascript" src="/resources/js/reply.js"></script>
            
            <script>
            $(document).ready(function(){
	    		
	    		
	    		var bnoValue = $("#no").val();
	    		
	    		var replyUL = $(".chat");
	    		
	    		showList(1);
	    		
	    		function showList(page) { // 페이지 번호를 파라미터로 받는다
	    			console.log("show list " + page);
	    		
	    			replyService.getList({bno:bnoValue, page:page||1}, function(replyCnt, list) { //해당 게시물 댓글 개수, 전체 댓글 같이 받아옴
	    				console.log("replyCnt : " + replyCnt);
	    				console.log("list : " + list);
	    				console.log(list);
	    				
	    				if (page == -1) { // 페이지 번호가 '-1'로 전달되면 마지막 페이지를 찾아서 다시 호출. 사용자가 새로운 댓글을 추가하면 showList(-1)을 호출해서 먼저 전체 댓글의 숫자를 파악하고 다시 마지막 페이지를 호출해서 이동
	    					pageNum = Math.ceil(replyCnt/10.0);
	    					showList(pageNum);
	    					return;
	    				}
	    				
	    				var str="";
	    				if(list == null || list.length==0) {
	    					return;
	    				}
	    				for (var i = 0, len = list.length || 0; i < len; i++) {
	    					str +="<li class='left clearfix' data-rno='"+list[i].rno+"'>";
	    					str +="		<div><div class='header'><strong class='primary-font'>"+list[i].replyer+"</strong>";
	    					str +="			<small class='pull-right text-muted'>"+replyService.displayTime(list[i].replyDate)+"</small></div>";
	    					str +="			<p>"+list[i].reply+"</p></div></li>";
	    				}
	    				replyUL.html(str);
	    				showReplyPage(replyCnt);
	    			}); // end function
	    		/*
	    		//페이징 고려x
	    			replyService.getList({bno:bnoValue, page:page||1}, function(list) { // 만일 파라미터가 없는 경우 자동으로 1페이지가 되도록 설정
	    				var str="";
	    				if(list == null || list.length==0) {
	    					replyUL.html("");
	    					return;
	    				}
	    				for (var i = 0, len = list.length || 0; i<len;i++) {
	    					str +="<li class='left clearfix' data-rno='"+list[i].rno+"'>";
	    					str +="		<div><div class='header'><strong class='primary-font'>"+list[i].replyer+"</strong>";
	    					str +="			<small class='pull-right text-muted'>"+replyService.displayTime(list[i].replyDate)+"</small></div>";
	    					str +="			<p>"+list[i].reply+"</p></div></li>";
	    				}
	    				replyUL.html(str);
	    				}); // end function
	    		 */			
	    		}// end showList
	    		
	    		//test
	    		/* 
	    		//18번 댓글 조회
	    		replyService.get(18, function(data){
	    			console.log(data);
	    		})
	    		 */
	    		/* 
	    		//18번 댓글 수정
	    		replyService.update({
	    			rno : 18,
	    			bno : bnoValue,
	    			reply : "Modified Reply..."
	    		}, function(result) {
	    			alert("수정 완료");
	    		})
	    		 */
	    		/* 
	    		//19번 댓글 삭제 테스트
	    		replyService.remove(19, function(count) {
	    			console.log(count);
	    			
	    			if (count === "success") alert("REMOVED");
	    		}, function (err) {
	    			alert("ERROR....");
	    		}
	    		);
	    		 */
	    		/* 
	    		//for replyService getList test
	    		// select.jsp 내부에서 Ajax 호출은 replyService라는 이름의 객체에 감춰져 있으므로 필요한 파라미터들만 전달하는 형태로 간결해진다
	    		replyService.getList({bno:bnoValue, page:1}, function(list) {
	    				// JavaScript의 객체 타입으로 만들어서 전송해주고, Ajax 전송 결과를 처리하는 함수를 파라미터로 같이 전달
	    					for (var i = 0, len = list.length || 0; i <len; i++) {
	    						console.log(list[i]);
	    					}
	    				});
	    		 */
	    		/* 
	    		//for replyService add test
	    		//add()에 던져야하는 파라미터는 JavaScript의 객체 타입으로 만들어서 전송해주고, Ajax 전송 결과를 처리하는 함수를 파라미터로 같이 전달
	    		replyService.add( // select.jsp 내부에서 Ajax 호출은 replyService라는 이름의 객체에 감춰져 있으므로 필요한 파라미터들만 전달하는 형태로 간결해진다
	    				{reply:"JS Test", replyer:"tester01", bno:bnoValue}, // JavaScript의 객체 타입으로 만들어서 전송해주고, Ajax 전송 결과를 처리하는 함수를 파라미터로 같이 전달
	    				function(result) {
	    					alert("RESULT : " + result);
	    				}
	    		);
	    		 */
	    		 
	    		 // New Reply 버튼 작동
	    		 var modal = $(".modal");
	    		 var modalInputReply = modal.find("input[name='reply']");
	    		 var modalInputReplyer = modal.find("input[name='replyer']");
	    		 var modalInputReplyDate = modal.find("input[name='replyDate']");
	    		 
	    		 var modalModBtn = $("#modalModBtn");
	    		 var modalRemoveBtn = $("#modalRemoveBtn"); 
	    		 var modalRegisterBtn = $("#modalRegisterBtn");
	    		 
	    		 $("#addReplyBtn").on("click", function(e){
	    			 modal.find("input").val("");
	    			 modalInputReplyDate.closest("div").hide();
	    			 modal.find("button[id!='modalCloseBtn']").hide();
	    			 
	    			 modalRegisterBtn.show();
	    			 $(".modal").modal("show");
	    		 });
	    		 // 모달 창 내에서 Register 버튼 작동
	    		 modalRegisterBtn.on("click", function(e){
	    			var reply = {
	    					reply: modalInputReply.val(),
	    					replyer: modalInputReplyer.val(),
	    					bno: bnoValue
	    			};
	    			replyService.add(reply, function(result){
	    				alert(result);
	    				
	    				modal.find("input").val("");
	    				modal.modal("hide");
	    				// 댓글이 처리 된 후 댓글 목록 갱신 (새로 등록된 댓글)
	   	    		 	//showList(1);
	    				// 댓글 등록 시 마지막 페이지 호출
	    				showList(-1);
	    			})
	    			 
	    		 });
	    		 
	    		 //댓글 클릭 이벤트 처리
	    		 $(".chat").on("click", "li", function(e){ // 이미 존재하는 DOM 요소에 이벤트를 처리. 실제 이벤트의 대상은 <li>태그
	    			var rno = $(this).data("rno");
	    			console.log(rno);
	    			// 특정 댓글 조회 클릭 이벤트 처리
	    			replyService.get(rno, function(reply){
	    				modalInputReply.val(reply.reply);
	    				modalInputReplyer.val(reply.replyer);
	    				modalInputReplyDate.val(replyService.displayTime( reply.replyDate)).attr("readonly", "readonly");
	    				modal.data("rno", reply.rno);
	    				
	    				modal.find("button[id!='modalCloseBtn']").hide();
	    				modalModBtn.show();
	    				modalRemoveBtn.show();
	    				
	    				$(".modal").modal("show");
	    				
	    			});
	    		 });
	    		 
	    		 //댓글 수정 버튼 동작
	    		 modalModBtn.on("click", function(e){
	    			var reply = {rno:modal.data("rno"), reply:modalInputReply.val()};
	    			
	    			replyService.update(reply, function(result){
	    				alert(result);
	    				modal.modal("hide");
	    				//showList(1);
	    				showList(pageNum); // 댓글의 수정 시 현재 댓글이 포함된 페이지로 이동
	    				
	    			});
	    			 
	    		 });
	    		 //댓글 삭제
	    		 modalRemoveBtn.on("click", function(e){
		    			var reply = modal.data("rno");
		    			
		    			replyService.remove(reply, function(result){
		    				alert(result);
		    				modal.modal("hide");
		    				//showList(1);
		    				showList(pageNum); // 댓글의 삭제 시 현재 댓글이 포함된 페이지로 이동
		    			});
		    			 
		    		 });
	    		 
	    		 //div class='panel-footer'에 댓글 페이지 번호를 출력하는 로직
	    		 var pageNum = 1;
	    		 var replyPageFooter = $(".panel-footer");
	    		 
	    		 function showReplyPage(replyCnt) {
	    			 var endNum = Math.ceil(pageNum / 10.0) * 10;
	    			 var startNum = endNum - 9;
	    			 
	    			 var prev = startNum != 1;
	    			 var next = false;
	    			 
	    			 if (endNum * 10 >= replyCnt) {
	    				 endNum = Math.ceil(replyCnt / 10.0);
	    			 }
	    			 if (endNum * 10 < replyCnt) {
	    				 next = true;
	    			 }
	    			 var str = "<ul class='pagination pull-right'>";
	    			 
	    			 if (prev) {
	    				 str += "<li class='page-item'><a class='page-link' href='"+(startNum - 1) + "'>Previous</a></li>";
	    			 }
	    			 
	    			 for (var i = startNum ; i <= endNum; i++) {
	    				 var active = pageNum == i ? "active" : "";
	    				 str += "<li class='page-item " + active + " '><a class='page-link' href='" + i + "'>"+ i + "</a></li>";
	    			 }
	    			 if (next) {
	    				 str += "<li class='page-item'><a class='page-link' href='"+(endNum + 1) + "'>Next</a></li>";
	    			 }
	    			 str += "</ul></div>";
	    			 console.log(str);
	    			 replyPageFooter.html(str);
	    		 }
	    		 // 페이지의 번호를 클릭했을 때 새로운 댓글을 가져오도록 하는 부분
	    		 replyPageFooter.on("click", "li a", function(e) { // 댓글의 페이지 번호는 a 태그 내에 존재
	    			 e.preventDefault();
	    			 console.log("page click");
	    			 
	    			 var targetPageNum = $(this).attr("href"); // 페이지 번호 클릭 시 댓글 페이지 번호를 변경
	    			 console.log("targetPageNum : " + targetPageNum);
	    			 pageNum = targetPageNum;
	    			 showList(pageNum);
	    		 });

            }); 
	    		
            </script>
         
            <!-- 버튼을 클릭하면 operForm action 실행 -->
            <script type="text/javascript">
            	$(document).ready(function(){
            		//reply.js의 실행 확인
    	    		//console.log(replyService);
            		
            		var operForm = $("#operForm");
            		
            		$("button[data-oper='update']").on("click", function(e) {
            			
            			operForm.attr("action", "/board/update").submit();
            			
            		});
            		
            		$("button[data-oper='list']").on("click", function(e) {
            			
            			operForm.find("#no").remove(); // list로 이동하는 경우에는 아무런 데이터도 필요하지 않으므로 form 내의 no태그를 삭제
            			
            			operForm.attr("action", "/board/list").submit();
            		})
            		
            	})
            </script>
<%@ include file="../includes/footer.jsp" %>