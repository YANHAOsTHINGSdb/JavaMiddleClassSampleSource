<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%--HTML 5対応--%>

<html>
<head>
<title>社員検索</title>
<link rel="stylesheet" href="css/tabulator.css">
<link href="css/tabulator_midnight.min.css" rel="stylesheet">
<script type="text/javascript" src="js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="js/jquery.json.js"></script>

<script type="text/javascript" src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>

<script type="text/javascript" src="js/tabulator.min.js"></script>
<script type="text/javascript" src="js/tabulator.js"></script>
</head>

<body>

	<%--
首先引入Jquery插件库，当然这个不是必须的，但引入一个脚本库，可以为我们省却不少重复麻烦的工作。
 --%>
	<script>
		$(function() {
			var $m = $('body');
			var alpha = $m.data('モード');
			if(alpha == '1'){
				$("#search_btn").click();
			}

			$("#response").html("Response Values");
			// Ajax通信テスト ボタンクリック
			$("#ajax_btn").click(function() {
				// outputDataを空に初期化
				//$("#output_data").text("");

				var url = $("url_post").val();
				var JSONdata = {
					name_input : $("#name_input").val(),
					maleFemale_input : $("#maleFemale_input").val(),
					birthDate_input : $("#birthDate_input").val(),
					joinDate_input : $("#joinDate_input").val()
				};

				//alert(JSON.stringify(JSONdata));

				$.ajax({
					type : "GET",
					url : "http://localhost:8080/JavaMiddleClassSampleSource/getTestData",
					dataType : "json", //dataType设置成 json，这个意思是说 ’服务器的数据返回的是json格式数据，需要帮我把数据转换成对象
					data : JSON.stringify(JSONdata),
					scriptCharset : 'utf-8',
					success : function(data) {
						success(data);
					},

					error : function() {

						alert("AJAXの返す処理はERRORがあり by Yan");
					}
				});
			});

			$("#add_btn").click(function() {
				window.status = "処理中です。しばらくお待ちください。";
			});

			$("#search_btn").click(function() {
				//alert("検索 by Yan");
				//var url = $("url_post").val();
				var JSONdata = {
					番号 : $("#番号").val(),
					姓名 : $("#姓名").val(),
					電話番号 : $("#電話番号").val(),
					性別 : $("#性別").val(),
					生年月日開始 : $("#生年月日開始").val(),
					生年月日終了 : $("#生年月日終了").val(),
					入社年月日開始 : $("#入社年月日開始").val(),
					入社年月日終了 : $("#入社年月日終了").val(),
					契約種類 : $("#契約種類").val()
				};

				//alert(JSON.stringify(JSONdata));

				$.ajax({
					type : 'POST',
					url : "http://localhost:8080/JavaMiddleClassSampleSource/getTestData",
					dataType : "json", //dataType设置成 json，这个意思是说 ’服务器的数据返回的是json格式数据，需要帮我把数据转换成对象
					contentType : "application/json",

					data : JSON.stringify(JSONdata),
					success : function(data) {
						success(data);
					},
					error : function(e) {
						console.log(e);
						alert("AJAXの検索処理はERRORがあり by Yan");
					}
				});
			});

		});

		// Ajax通信成功時処理
		function success(data) {
			/* 			$("#emlist").empty();
			 buildHtmlTable(data,$("#emlist")); */
			$("#example-table").tabulator({
				height : "311px",
				layout : "fitColumns",
				placeholder : "No Data Set",
				columns : [
				{
					title : "s_ID",
					field : "s_ID",
					sorter : "string",
					sorter : "boolean",
						cellClick : function(e, cell) {
							oneRowClick(cell.getValue())
						}
					},
				{
					title : "番号",
					field : "番号",
					sorter : "string",
					sorter : "boolean",
					cellClick : function(e, cell) {
					    var row = cell.getRow();
					    var data = row.getData();
					    oneRowClick(data.s_ID);
					}
				}, {
					title : "姓名",
					field : "姓名",
					sorter : "string",
					width : 200,
					sorter : "boolean"
				}, {
					title : "電話番号",
					field : "電話番号",
					sorter : "string",
					width : 200,
					sorter : "boolean"
				}, {
					title : "性別",
					field : "性別",
					sorter : "string",
					width : 100,
					sorter : "boolean"
				}, {
					title : "入社年月日",
					field : "入社年月日",
					sorter : "date",
					sorter : "boolean"
				}, {
					title : "生年月日",
					field : "生年月日",
					sorter : "date",
					align : "left",
						cellClick : function(e, cell) {
						    var row = cell.getRow()
						    var data = row.getData();
							oneRowDeleteClick(data.s_ID);
						}
				}, {
					title : "契約種類",
					field : "契約種類",
					sorter : "string",
					align : "left",
						cellClick : function(e, cell) {
						    var row = cell.getRow()
						    var data = row.getData();
							oneRowDeleteClick(data.s_ID);
						}
				}, ],
				rowClick : function(e, row) {
					/* alert("Row " + row.getIndex() + " Clicked!!!!"); */

				},
			});
			$("#example-table").tabulator("setData", data);
		}
		function oneRowClick(selectedID) {
			/* 			alert(selected番号);
			 alert("oneRowClick IS RUN HERE!!");
			 */
			var JSONdata = {
					 s_ID : selectedID
			};

			$.ajax({
				type : 'POST',
				url : "http://localhost:8080/JavaMiddleClassSampleSource/getTestData",
				dataType : "json", //dataType设置成 json，这个意思是说 ’服务器的数据返回的是json格式数据，需要帮我把数据转换成对象
				contentType : "application/json",

				data : JSON.stringify(JSONdata),

				success : function(data) {
					/* 					var obj = eval("("+data+")");
					 if(obj.success==undefined){//查询成功，跳转到详情页面 */

					if(data[0]){
						$("#s_ID").val(data[0].s_ID);
						$("#番号").val(data[0].番号);
						$("#姓名").val(data[0].姓名);
						$("#電話番号").val(data[0].電話番号);
						$("#性別").val(data[0].性別);
						$("#入社年月日").val(data[0].入社年月日);
						$("#生年月日").val(data[0].生年月日);
						$("#契約種類").val(data[0].契約種類);
						//---------------------------------
						$("#theForm").attr("action",
								"http://localhost:8080/JavaMiddleClassSampleSource/社員edit");
						$("#theForm").submit();
					}else{
						alert("没有找到检索对象。");
					}

				},
				error : function(e) {
					alert("AJAXの編集処理はERRORがあり by Yan");
				}
			});
		}

		function oneRowDeleteClick(selectedID) {

			var JSONdata = {
					s_ID : selectedID
			};

			alert(JSON.stringify(JSONdata));

			$.ajax({
				type : 'POST',
				url : "http://localhost:8080/JavaMiddleClassSampleSource/社員delete",
				dataType : "json", //dataType设置成 json，这个意思是说 ’服务器的数据返回的是json格式数据，需要帮我把数据转换成对象
				contentType : "application/json",

				data : JSON.stringify(JSONdata),
				success : function(data) {

					$("#theForm").attr("action",
							"http://localhost:8080/JavaMiddleClassSampleSource/getTestData");
					$("#theForm").submit();
				},
				error : function(e) {
					alert("AJAXの削除処理はERRORがあり by Yan");
				}
			});
		}

		// Ajax通信失敗時処理
		function error(XMLHttpRequest, textStatus, errorThrown) {
			alert("error:" + XMLHttpRequest);
			alert("status:" + textStatus);
			alert("errorThrown:" + errorThrown);
		}
	</script>
	<form name="theForm" id="theForm" method="get"
		action="http://localhost:8080/JavaMiddleClassSampleSource/社員add">
		<h1>人物設定</h1>
		<br>
		<div>
			<input id="s_ID" name="s_ID" type="text" Value="">(隐藏项目=s_ID，调试用)
		</div>
		<br>
		<div>
			<label>番号</label> <input id="番号" name="番号" type="text" Value="">
		</div>
		<br>
		<div>
			<label>姓名</label> <input id="姓名" name="姓名" type="text" Value="">
		</div>
		<br>
		<div>
			<label>電話番号</label> <input id="電話番号" name="電話番号" type="text" Value="">
		</div>
		<br>
		<div>
			<label>性別</label> <select id="性別" name="性別" style="width: 60px">
				<option value="" selected="selected"></option>
				<option value="女">女</option>
				<option value="男">男</option>
			</select>
		</div>
		<br>
		<div>
			<label>生年月日</label> <input id="生年月日開始" type="text" Value=""
				placeholder="YYYY/MM/DD" type="text"> ～ <input id="生年月日終了"
				type="text" Value="" placeholder="YYYY/MM/DD" type="text">
			<div id="caleandar"></div>
			<input id="生年月日" name="生年月日" type="hidden" Value="">
		</div>
		<br>
		<div>
			<label>入社年月日</label> <input id="入社年月日開始" type="text" Value=""
				placeholder="YYYY/MM/DD" type="text"> ～ <input id="入社年月日終了"
				type="text" Value="" placeholder="YYYY/MM/DD" type="text"> <input
				id="入社年月日" name="入社年月日" type="hidden" Value="">
		</div>

		<br>
		<div>
			<label>契約種類</label> <select id="契約種類" name="契約種類"
				style="width: 150px">
				<option value="" selected="selected"></option>
				<option value="役員">役員</option>
				<option value="正社員">正社員</option>
				<option value="契約社員">契約社員</option>
				<option value="その他(個人事業主)">その他(個人事業主)</option>

			</select>
		</div>
		<br>
		<div>
			<input type="button" id="search_btn" Value="検索"> <input
				type="submit" id="add_btn" Value="追加">
		</div>
		<br>
		<div>
			<table id="emlist" style="width: 70%">
				<thead>
					<tr>
						<th>社員一覧</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
		</div>
		<br>
		<div>
			<div id="example-table"></div>
		</div>
	</form>
</body>

</html>
