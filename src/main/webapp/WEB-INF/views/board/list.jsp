<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp" %>
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Tables</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Board List Page
                            <!-- 리스트 화면에서 글 등록으로 가는 버튼 -->
                            <button id='regBtn' type="button" class="btn btn-xs pull-right">Register New Board</button>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                    <tr>
                                        <th>번호</th>
                                        <th>제목</th>
                                        <th>작성자</th>
                                        <th>작성일</th>
                                        <th>수정일</th>
                                    </tr>
                                </thead>
                                
                         		<!-- '/board/list' 실행결과(select)를 BoardController는 Model을 이용해서 게시물의 목록을 'list'라는 이름의 변수로 전달 -->
                         		<!-- Model 출력 부위 -->
                                <!-- list.jsp에서 이를 출력. jstl을 이용해서 처리 -->
                                <!-- c:forEach : list에 저장되어있는 0번째를 board에 저장해서 출력, 1번째를 board에 저장해서 출력, ... -->
                                <c:forEach items="${list}" var="board">
                                	<tr>
                                		<td><c:out value="${board.no }" /></td> <%-- 출력할 때 c:out 안쓰고 ${board.xx} 만 써도 됨 --%>
                                		<!-- 상세 페이지를 새 창으로 띄울 때 a 태그의 속성 target을 target='_blank' 으로 지정-->
                                		<%-- <td><a href='/board/select?no=<c:out value="${board.no }" />'> --%>
                                		<!-- 직접 링크로 연결된 경로를 form 태그를 이용해서 처리할 예정. href 속성을 게시물의 번호만을 가지도록 수정 -->
                                		<td><a class="move" href='<c:out value="${board.no }" />'>
                                		<c:out value="${board.title }" /></a></td>
                                		<td><c:out value="${board.writer }" /></td>
                                		<td><c:out value="${board.regdate }" /></td>
                                		<td><c:out value="${board.updateDate }" /></td>                                		
                                		<%-- 
                                		<td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.regdate }"></fmt:formatDate></td>
                                		<td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.updateDate }"></fmt:formatDate></td>
                                		 --%>
                                	</tr>
                                </c:forEach>
                                </table>
                                
                                <!-- 검색 조건과 키워드가 들어 갈 수 있게 HTML을 수정 -->
                                <%-- 
                                <div class='row'>
                                	<div class="col-lg-12">
                                		<form id='searchForm' action="/board/list" method='get'>
                                			<select name='type'>
                                				<option value="">--------</option>
                                				<option value="T">제목</option>
                                				<option value="C">내용</option>
                                				<option value="W">작성자</option>
                                				<option value="TC">제목 or 내용</option>
                                				<option value="TW">제목 or 작성자</option>
                                				<option value="CW">내용 or 작성자</option>
                                			</select>
                                			<input type="text" name="keyword">
                                			<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum }">
                                			<input type="hidden" name="amount" value="${pageMaker.cri.amount }">
                                			<button class="btn btn-default">Search</button>
                                		</form>
                                	</div>
                                </div>
                                 --%>
                                <!-- 검색 후 검색 조건과 키워드를 보여주는 코드 -->
                                <div class='row'>
                                	<div class="col-lg-12">
                                		<form id='searchForm' action="/board/list" method='get'>
                                			<select name='type'>
                                				<option value=""
                                				<c:out value="${pageMaker.cri.type == null?'selected':'' }" />>------</option>
	                                			<option value="T"
	                                				<c:out value="${pageMaker.cri.type eq 'T'?'selected':'' }" />>제목</option>
	                                			<option value="C"
	                                				<c:out value="${pageMaker.cri.type eq 'C'?'selected':'' }" />>내용</option>
	                                			<option value="W"
	                                				<c:out value="${pageMaker.cri.type eq 'W'?'selected':'' }" />>작성자</option>	
	                                			<option value="TC"
	                                				<c:out value="${pageMaker.cri.type eq 'TC'?'selected':'' }" />>제목 or 내용</option>
	                                			<option value="TW"
	                                				<c:out value="${pageMaker.cri.type eq 'TW'?'selected':'' }" />>제목 or 작성자</option>
	                                			<option value="CW"
	                                				<c:out value="${pageMaker.cri.type eq 'CW'?'selected':'' }" />>내용 or 작성자</option>
	                                			<option value="TCW"
	                                				<c:out value="${pageMaker.cri.type eq 'TCW'?'selected':'' }" />>제목 or 내용 or 작성자</option>
                                			</select>
                                			<input type="text" name="keyword" value="${pageMaker.cri.keyword }">
                                			<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum }">
                                			<input type="hidden" name="amount" value="${pageMaker.cri.amount }">
                                			<button class="btn btn-default">Search</button>
                                		</form>
                                	</div>
                                </div>
                                
                                
                                <!-- JSP에서 페이지 번호 출력 -->
                                <!-- Q) href 속성값을 c:out으로 쓰면? -->
                                <!-- line 65 : pageNum이 현재 페이지 번호와 같으면 class="paginate_button active" 아마 하이라이트? -->
                                <div class='pull-right'>
                                	<ul class="pagination">
                                		<c:if test="${pageMaker.prev }">
                                			<li class="paginate_button previous"><a href="${pageMaker.startPage - 1 }">Prev</a></li>
                                		</c:if>
                                		
                                		<c:forEach var="num" begin="${pageMaker.startPage }"
                                		end="${pageMaker.endPage }">
                                		
                                		<li class="paginate_button ${pageMaker.cri.pageNum == num ? 'active' : '' }">
                                		<a href="${num }">${num }</a></li>
                                		</c:forEach>
                                		
                                		<c:if test="${pageMaker.next }">
                                			<li class="paginate_button next"><a href="${pageMaker.endPage + 1 }">Next</a></li>
                                		</c:if>
                                	</ul>
                                </div>
                                
                                <!-- 페이지를 클릭하면 실제로 동작을 하는 부분, js로 버튼과 아래 인풋 연결 -->
                                <form id="actionForm" action="/board/list" method="get">
                                	<!-- 페이지 이동 시에도 동일한 검색 사항들을 유지 -->
                                	<input type="hidden" name="type" value="<c:out value='${pageMaker.cri.type }' />">
                                	<input type="hidden" name="keyword" value="<c:out value='${pageMaker.cri.keyword }' />">
                                	
                                	<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum }">
                                	<input type="hidden" name="amount" value="${pageMaker.cri.amount }">
                                </form>
                                <!-- end pagination -->
                                
                                <!-- Modal(게시물 등록 시 알림창) 추가 -->
                                <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                                aria-hidden="true">
                                	<div class="modal-dialog">
                                		<div class="modal-content">
                                			<div class="modal-header">
                                				<button type="button" class="close" data-dismiss="modal"
                                				aria-hidden="true">&times;</button>
                                				<h4 class="modal-title" id="myModalLabel">Modal title</h4>
                                			</div>
                                			<div class="modal-body">처리가 완료되었습니다.</div>
                                			<div class="modal-footer">
                                				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                				<button type="button" class="btn btn-primary">Save changes</button>
	                                		</div>
	                                	</div>
	                                </div>
                                
                            	</div>
                            	<!-- end Modal Window -->
                            <!-- /.table-responsive -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-6 -->
            </div>
            <!-- /.row -->
            
            <script type="text/javascript">
            $(document).ready(function(){
            	//modal창 보여주는 작업, redirect의 결과 확인
            	var result = '<c:out value="${result}"/>'; // Controller에 service.getNewestNo()를 추가해서 추가될 번호를 넣어줌
            	//console.log(result); // 등록 후 게시물 번호 안뜨는 문제 확인하는 코드
            	checkModal(result);
            	//글 등록 후 리스트로 오면 history.state=null, 글 조회 후 리스트로 오면 history.state={}
            	//뒤로 가기 문제로 인한 window.history 객체 조작
            	history.replaceState({}, null, null);
            	
            	function checkModal(result) {
            		//if (result === '') {
            		
            		//브라우저를 통한 이동을 하면 history.state는 {}
            		if (result === '' || history.state) { // 뒤로 가기 문제로 인해 history.state를 체크
            			//브라우저를 통해 이동할 때 여기를 거침
            			//console.log(history.state);
            			return;
            		}
            		
            		if(parseInt(result) > 0) {
            			$(".modal-body").html("게시글 " + parseInt(result) + " 번이 등록되었습니다.");
            		}
            		
            		$("#myModal").modal("show");
            	}
            	//글 등록버튼 이동 이벤트
            	$("#regBtn").on("click", function(){
            		self.location = "/board/register";
            	});
            	
            	//4/12 url 다시 확인할 것
            	//페이지 버튼 동작
            	var actionForm = $("#actionForm");
            	$(".paginate_button a").on("click", function(e) { // class="paginate_button"의 a 태그가 클릭되면
            		e.preventDefault(); // 원래 기능 제거하고
            		
            		console.log('click'); // 콘솔에 문자열 표시하고
            		
            		// actionForm에 input[name='pageNum']를 찾고 value 값을 "클릭 당한 a태그의 href값"으로 설정
            		actionForm.find("input[name='pageNum']").val($(this).attr("href"));
            		//form을 submit
            		actionForm.submit();
            	})
            	
            	//게시물 제목 클릭 했을 때 이동 (detail에서 list 갈 때 Criteria 정보 가지도록 설정)
            	$(".move").on("click", function(e){
            		e.preventDefault();
            		actionForm.append("<input type='hidden' name='no' value='"+$(this).attr("href")+"'>");
            		actionForm.attr("action", "/board/select");
            		actionForm.submit();
            		})
          
            	//검색 버튼을 클릭하면 검색결과는 1페이지부터
            	//화면에 select, input에 검색 조건과 키워드가 보이도록
            	var searchForm = $("#searchForm");
            	$("#searchForm button").on("click", function(e) {
            		if (!searchForm.find("option:selected").val()) {
            			alert("검색 종류를 선택하세요");
            			return false;
            		}
            		if (!searchForm.find("input[name='keyword']").val()) {
            			alert("키워드를 입력하세요");
            			return false;
            		}
            		
            		searchForm.find("input[name='pageNum']").val("1");
            		e.preventDefault();
            		
            		searchForm.submit();
            	});
            	}
            )
            </script>
<%@ include file="../includes/footer.jsp" %>