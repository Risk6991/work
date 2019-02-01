<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="bean.M_Bean"%>
<%@page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ja">
<head>

  <meta charset="utf-8">

  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

  <link rel="stylesheet" href="css/bootstrap.min.css">
  <link href="css/m-mystyle.css" rel="stylesheet" type="text/css">
<%
List<M_Bean> list = (List<M_Bean>)session.getAttribute("login");
%>
  <title>管理者専用</title>

</head>

<body>

    <div class="container text-center" style="margin-top:20px">        <!-- 全体を囲むコンテナ -->

        <img src="image/logo.gif">

		<h5 class="my-3" style="color: #5b5b5b">管理者 <%=list.get(0).getM_Name()%> 様ログイン中</h5>

        <div class="row">

	            <div class="col-md-6 col-lg-4 mb-4">
		            <form action="manager" method="post" name= "form1" >
		        		<input type="hidden" name="action" value="m_insert">
			                <a href="javascript:form1.submit()">
			                <div class="card">
			                    <div class="card-body">
			                        <img src="image/m-img01.gif" class="rounded-circle">
			                    </div>
			                    <div class="card-footer bg-primary text-white" style="border-radius:0">管理者新規登録
			                    </div>
			                </div>
			                </a>
			        </form>
	            </div>

	            <div class="col-md-6 col-lg-4 mb-4">
		            <form method="post" name= "form2" action= "manager">
						<input type="hidden" name="action" value="m_search">
			            	<a href="javascript:form2.submit()">
			                <div class="card">
			                    <div class="card-body">
			                        <img src="image/m-img02.gif" class="rounded-circle">
			                    </div>
			                    <div class="card-footer bg-primary text-white" style="border-radius:0">管理者検索
			                    </div>
			                </div>
			                </a>
		            </form>
	            </div>

	            <div class="col-md-6 col-lg-4 mb-4">
		            <form method="post" name= "form3" action= "manager">
						<input type="hidden" name="action" value="s_insert">
			            	<a href="javascript:form3.submit()">
			                <div class="card">
			                    <div class="card-body">
			                        <img src="image/m-img03.gif" class="rounded-circle">
			                    </div>
			                    <div class="card-footer bg-primary text-white" style="border-radius:0">商品新規登録
			                    </div>
			                </div>
			                </a>
		           </form>
	            </div>

	            <div class="col-md-6 col-lg-4 mb-4">
		            <form method="post" name= "form4" action= "manager">
						<input type="hidden" name="action" value="s_search">
			            	<a href="javascript:form4.submit()">
			                <div class="card">
			                    <div class="card-body">
			                        <img src="image/m-img04.gif" class="rounded-circle">
			                    </div>
			                    <div class="card-footer bg-primary text-white" style="border-radius:0">商品検索
			                    </div>
			                </div>
			                </a>
		           </form>
	            </div>

	            <div class="col-md-6 col-lg-4 mb-4">
		            <form action= "manager" method="post" name= "form5" >
						<input type="hidden" name="action" value="c_search">
			            	<a href="javascript:form5.submit()">
			                <div class="card">
			                    <div class="card-body">
			                        <img src="image/m-img05.gif" class="rounded-circle">
			                    </div>
			                    <div class="card-footer bg-primary text-white" style="border-radius:0">会員検索
			                    </div>
			                </div>
			            	</a>
		            </form>
	            </div>

	            <div class="col-md-6 col-lg-4 mb-4">
		            <form method="post" name= "form6" action= "manager">
						<input type="hidden" name="action" value="not_dispatch_list">
			            	<a href="javascript:form6.submit()">
			                <div class="card">
			                    <div class="card-body">
			                        <img src="image/m-img06.gif" class="rounded-circle">
			                    </div>
			                    <div class="card-footer bg-primary text-white" style="border-radius:0">未発送リスト
			                    </div>
			                </div>
			                </a>
		           </form>
	            </div>

	            <div class="col-md-6 col-lg-4 mb-4">
		            <form method="post" name= "form7" action= "manager">
						<input type="hidden" name="action" value="salse_information">
			            	<a href="javascript:form7.submit()">
			                <div class="card">
			                    <div class="card-body">
			                        <img src="image/m-img07.gif" class="rounded-circle">
			                    </div>
			                    <div class="card-footer bg-primary text-white" style="border-radius:0">売上情報
			                    </div>
			                </div>
			                </a>
		           </form>
	            </div>

        </div>

    </div>        <!-- 全体を囲むコンテナ -->



<script src="js/jquery-3.3.1.slim.min.js"></script>

<script src="js/bootstrap.bundle.min.js"></script>


</body>
</html>