<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%--HTML 5対応--%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Home</title>
<script type="text/javascript" src="js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="js/jquery.json.js"></script>

<script type="text/javascript" src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>


</head>
<body>
	<script>
	$(function() {
		$("#back_btn").click(function() {
		    $("#fbean").attr("action","http://localhost:8080/myapp/back");
		    $("#fbean").submit();
		});

		$("#save_btn").click(function() {
			if("${モード}" == "編集"){
			    $("#fbean").attr("action","http://localhost:8080/myapp/update");
			    $("#fbean").submit();

			}else{
			    $("#fbean").attr("action","http://localhost:8080/myapp/save");
			    $("#fbean").submit();
			}
		});

	});



	</script>

	<form id ="fbean" name="fbean" method="post">

	<h1>${titleName}</h1>
	<div>
		<label>番号</label>
		<input type="text" value="${番号}" name="番号" ${モード == '編集' ? 'disabled' : ''}/>
		<input type="hidden" value="${番号}" name="old_番号" />
	</div>
	<br>
	<div>
		<label>姓名</label>
		<input type="text" value="${姓名}" name="姓名" />
		<input type="hidden" value="${姓名}" name="old_姓名" />
	</div>
	<br>
	<div>
		<label>性別</label> <select style="width:60px" name="性別" >
			<option value="" ${性別 == '' ? 'selected' : ''}></option>
			<option value="女" ${性別 == '女' ? 'selected' : ''}>女</option>
			<option value="男" ${性別 == '男' ? 'selected' : ''}>男</option>
		</select>
		<input type="hidden" value="${性別}" name="old_性別" />
	</div>
	<br>
	<div>
		<label>生年月日</label>
		<input type="text" value="${生年月日}"
			placeholder="YYYY/MM/DD"
			type="text" name="生年月日"/>
		<input type="hidden" value="${生年月日}" name="old_生年月日" />
	</div>
	<br>
	<div>
		<label>入社年月日</label>
		<input type="text" Value="${入社年月日}" name="入社年月日"
			placeholder="YYYY/MM/DD"
			type="text"/>
		<input type="hidden" value="${入社年月日}" name="old_入社年月日" />
	</div>
	<br>
	<div>
		<label>契約種類</label>
		<select id="契約種類" style="width:150px" name="契約種類">
			<option value="" ${契約種類 == '' ? 'selected' : ''}></option>
			<option value="役員" ${契約種類 == '役員' ? 'selected' : ''} >役員</option>
			<option value="正社員" ${契約種類 == '正社員' ? 'selected' : ''} >正社員</option>
			<option value="契約社員" ${契約種類 == '契約社員' ? 'selected' : ''} >契約社員</option>
			<option value="その他(個人事業主)" ${契約種類 == 'その他(個人事業主)' ? 'selected' : ''} >その他(個人事業主)</option>
		</select>
		<input type="hidden" value="${契約種類}" name="old_契約種類" />
	</div>
	<br>
	<div>
		<input type="button" id="back_btn" Value="戻る">
	</div>
	<br>

	<div>
		<input type="button" id="save_btn" Value="登録">
	</div>

	<h1>${errorMsg}</h1>
</form>
</body>
</html>
