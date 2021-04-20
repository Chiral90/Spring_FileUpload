/**
 * 
 */

//console.log("Reply Module....");
//JavaScript의 즉시 실행함수는 () 안에 함수를 선언하고 바깥쪽에서 실행 (function())(); 형태
//즉시 실행함수는 함수의 실행 결과가 바깥쪽에 선언된 변수에 할당
var replyService = (function(){
	//replyService라는 변수에  name 속성에 'AAAA'라는 속성값을 가진 객체가 할당
	//return {name:"AAAA"}
	/*
	function add(reply, callback) {
		consol.log("reply....");
	}
	return {add:add};
	//결과 : replyService객체의 내부에는 add라는 메서드가 존재하는 형태
	*/
	
	// Ajax를 이용해서 POST 방식으로 호출하는 코드
	// 만약 Ajax 호출이 성공하고 callback 값으로 적절한 함수가 존재하면 해당 함수를 호출해서 결과를 반영
	
	function add(reply, callback, error) { // 파라미터로 callback과 error를 지정
		console.log("add reply....");
		
		$.ajax({
			type : 'post',
			url : '/replies/new',
			data : JSON.stringify(reply), // reply = jsp의 var reply
			contentType : "application/json; charset=utf-8", // 데이터 전송 타입
			success : function(result, status, xhr) { // 성공했을 때
				if (callback) {
					callback(result);
				}
			},
			error : function(xhr, status, er) { // 실패했을 때
				if (error) {
					error(er);
				}
			}
		})
	}
	
	function getList(param, callback, error) { // param이라는 객체를 통해서 필요한 파라미터를 전달받아 json 목록을 호출
		// callback : 함수 실행 후 액션
		var bno = param.bno;
		
		var page = param.page || 1;
		// get방식을 별도의 속성값 없이 처리 가능
		$.getJSON("/replies/pages/" + bno + "/" + page + ".json",
				function(data) { // controller의 service.get(rno)의 데이터 = data
			//console.log(data);
					if (callback) {
						//callback(data); // 댓글 목록만 가져오는 경우
						callback(data.replyCnt, data.list); // 댓글 숫자와 목록을 가져오는 경우
					}
		}).fail(function(xhr, status, err) {
			if (error) {
				error();
			}
		});
		
	}
	
	function remove(rno, callback, error) {
		$.ajax({
			type : 'delete',
			url : '/replies/' + rno,
			success : function(deleteResult, status, xhr) {
				if (callback) {
					callback(deleteResult);
				}
			},
			error : function(xhr, status, er) {
				if (error) {
					error(er);
				}
			}
			
		})
	}
	
	function update(reply, callback, error) {
		//console.log("RNO : " + reply.rno);
		
		$.ajax({
			type : 'put',
			url : '/replies/' + reply.rno,
			data : JSON.stringify(reply),
			contentType : "application/json; charset=utf-8",
			success : function(result, status, xhr) {
				if (callback) {
					callback(result);
				}},
			error : function(xhr, status, er) {
				if (error) {
					error(er);
				}
			}
		});
	}
	
	function get(rno, callback, error) {
		$.get("/replies/" + rno + ".json", function(result) {
			if (callback) {
				callback(result);
			}
			
		}).fail(function(xhr, status, err) {
			if (error) {
				error();
			}
		});
	}
	
	// 시간에 대한 처리
	function displayTime(timeValue) {
		var today = new Date();
		
		var gap = today.getTime() - timeValue;
		
		var dateObj = new Date(timeValue);
		var str = "';"
			if (gap < (1000 * 60 * 60 * 24)) {
				var hh = dateObj.getHours();
				var mm = dateObj.getMinutes();
				var ss = dateObj.getSeconds();
			
				return [ (hh> 9 ? '' : '0') + hh, ':', (mm > 9 ? '' : '0') + mm, ':', (ss > 9 ? '' : '0') + ss].join('');
			} else {
				var yy = dateObj.getFullYear();
				var mm = dateObj.getMonth() + 1;
				var dd = dateObj.getDate();
			
				return [yy, '/', (mm>9?'':'0')+mm, '/',(dd>9?'':'0')+dd].join('');
			
			}
	}
	
	return {
		add : add,
		getList : getList,
		remove : remove,
		update : update,
		get : get,
		displayTime : displayTime
	};
	
	
})();
