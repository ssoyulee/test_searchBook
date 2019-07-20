<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>도서 검색</title>
		
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
		<style>
		    div.left {
		        width: 50%;
		        float: left;
		        box-sizing: border-box;
		    }
		    div.right {
		        width: 50%;
		        float: right;
		        box-sizing: border-box;
		    }
		</style>
		<script>
			$(function() {

				// 로그인 버튼 클릭
				$('#btnLogin').on('click', execLogin);

				// 회원가입 버튼 클릭
				$('#btnReg').on('click', execReg);

				// 회원가입 버튼 클릭
				$('#btnSearch').on('click', execSearch);
			})
			
			function execLogin(){

				var params = {
						"userId":$('#userId').val()
					,	"userPw":$('#userPw').val()
				}
				
		        $.ajax({
		            url:'/searchBook/loginUser?'+$.param(params),
		            type:'get',
		            dataType : "json",
		            success:function(data){
		                if ( data.resultCode == '00' ){
		                	alert('로그인에 성공하였습니다.');
		                	$('#searchArea').show();
		                	$('#loginUserId').val( $('#userId').val() );
			            } else {
				            alert('로그인에 실패하였습니다.' + data.resultMsg);
				        }
		            },
		            error: function(xhr, status, error) {
		                alert(error);
		            } 
		        });
		        
			}
			
			function execReg(){

				var params = {
						"userId":$('#userId').val()
					,	"userPw":$('#userPw').val()
				}
				
		        $.ajax({
		            url:'/searchBook/registUser?'+$.param(params),
		            type:'get',
		            dataType : "json",
		            success:function(data){
		                if ( data.resultCode == '00' ){
		                	alert('회원가입에 성공하였습니다.');
			            } else {
				            alert('회원가입에 실패하였습니다.' + data.resultMsg);
				        }
		            },
		            error: function(xhr, status, error) {
		                alert(error);
		            } 
		        });
		        
			}
			function execSearch(){

				var params = {
						"userId":$('#loginUserId').val()
					, "query" : $('#searchQuery').val()
					, "page" : $('#searchPage').val()
					, "size" : $('#searchCount').val()
				}
				
		        $.ajax({
		            url:'/searchBook/search?'+$.param(params),
		            type:'get',
		            dataType : "json",
		            success:function(data){

		                var html = "<colgroup><col width='11%'/><col width='11%'/><col width='11%'/><col width='11%'/><col width='11%'/><col width='11%'/><col width='11%'/><col width='11%'/><col width='11%'/></colgroup><thead>"
		                	html += "<tr style='background-color: #d6cbcb;'><td>제목</td><td>도서 썸네일</td><td>소개</td><td>ISBN</td><td>저자</td><td>출판사</td><td>출판일</td><td>정가</td><td>판매가</td></tr>";
		                $.each( data.resultData.documents, function(idx){
							html += '<tr>';
							html += '<td>' + this.title + '</td>';
							html += '<td>' +'<img src="' + this.thumbnail + '">' + '</td>';
							html += '<td>' + this.contents.substring(0,50) + '</td>';
							html += '<td>' + this.isbn + '</td>';
							html += '<td>' + this.authors + '</td>';
							html += '<td>' + this.publisher + '</td>';
							html += '<td>' + this.datetime + '</td>';
							html += '<td>' + this.price + '</td>';
							html += '<td>' + this.sale_price + '</td>';
							html += '</tr></thead>';
						});
						$('#tblSearchResult').html(html);		                
		            },
		            error: function(xhr, status, error) {
		                alert(error);
		            },
		            complete  : function(){
		                selectSearchHist();
		                selectMostSearchWord();
			        }
		        });
		        
			}
			
			function selectSearchHist(){

				var params = {
						"userId":$('#loginUserId').val()
				}
				
		        $.ajax({
		            url:'/searchBook/selectSearchHist?'+$.param(params),
		            type:'get',
		            dataType : "json",
		            success:function(data){
			            if ( data.resultData ){
			                var html = "<tr style='background-color: #d6cbcb;'><td>검색어</td></tr>";
			                $.each( data.resultData, function(idx){
								html += '<tr>';
								html += '<td>' + this.searchWord + '</td>';
								html += '</tr>';
							});
							$('#tblSearchHistory').html(html);
						}
		            },
		            error: function(xhr, status, error) {
		                alert(error);
		            } 
		        });
			}
			
			function selectMostSearchWord(){

				var params = {
						"userId":$('#loginUserId').val()
				}
				
		        $.ajax({
		            url:'/searchBook/selectMostSearchWord?'+$.param(params),
		            type:'get',
		            dataType : "json",
		            success:function(data){
			            if ( data.resultData ){
			                var html = "<colgroup><col width='50%'/><col width='50%'/></colgroup><thead>"
			                	html += "<tr style='background-color: #d6cbcb;'><td>검색어</td><td>검색수</td></tr>";
			                $.each( data.resultData, function(idx){
								html += '<tr>';
								html += '<td>' + this.searhWord + '</td>';
								html += '<td>' + this.searchCount + '</td>';
								html += '</tr></thead>';
							});
							$('#tblMostSearchWord').html(html);
			            }
		            },
		            error: function(xhr, status, error) {
		                alert(error);
		            } 

		        });
			}

			selectMostSearchWord();
			selectSearchHist();
			
		</script>
	</head>

	<body>
		<form>
			<input type = "hidden" id="loginUserId">
			
			<div id = "loginArea" style="width: 70%" align="left" >
			  <div class="form-group">
			    <label for="formGroupExampleInput">회원아이디</label>
			    <input type="text" class="form-control" placeholder="회원아이디를 입력하세요" id="userId">
			  </div>
			  <div class="form-group">
			    <label for="formGroupExampleInput2">비밀번호</label>
			    <input type="password" class="form-control" placeholder="비밀번호를 입력하세요" id="userPw">
			  </div>
			  <div align="center">
			     <button class="btn btn-outline-secondary" type="button" id="btnLogin">로그인</button>
			     <button class="btn btn-outline-secondary" type="button" id="btnReg">회원가입</button>
			  </div>
			</div>
			
			<div id = "searchArea" style="display: none">
				<div>
			  		<div class="form-group">
					    <label for="formGroupExampleInput">검색어</label>
					    <input type="text" class="form-control" placeholder="검색어를 입력하세요" id="searchQuery">
					    <label for="formGroupExampleInput">페이지</label>
					    <input type="text" class="form-control" placeholder="검색어를 입력하세요" id="searchPage" value="1">
					    <label for="formGroupExampleInput">페이지당 출력수</label>
					    <input type="text" class="form-control" placeholder="검색어를 입력하세요" id="searchCount" value="10">
					    <button class="btn btn-outline-secondary" type="button" id="btnSearch">검색</button>
				  	</div>
				</div>
				<div>
					<h5><b>검색결과</b></h5>
					<table id = "tblSearchResult" style="width: 80%">
					</table>
				</div>
				<div class="left">
					<h5><b>인기 키워드 목록</b></h5>
					<table id = "tblMostSearchWord" style="width: 80%">
					</table>
				</div>
				<div class="right">
					<h5><b>내 검색 히스토리</b></h5>
					<table id = "tblSearchHistory" style="width: 80%">
					</table>
				</div>
			</div>
		</form>
	</body>
</html>