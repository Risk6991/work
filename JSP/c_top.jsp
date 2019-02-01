<%@page import="bean.S_Bean"%>
<%@page import="java.util.List" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ja">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="css/bootstrap.min.css">
<link href="css/c-mystyle.css" rel="stylesheet" type="text/css">
<link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
<link rel="stylesheet" href="css/pagenation-style.css" media="all">

<%
List <S_Bean> s_list=(List<S_Bean>)session.getAttribute("list");
	System.out.println(session.getId());
%>

<title>SakeTown</title>
</head>

<body body="" class="full-screen-preview">
<script src="js/jquery-3.3.1.slim.min.js"></script>
<script src="js/popper.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/jquery.js"></script>
<script src="js/jquery.pagination.js"></script>
<script src="js/en.min.js"></script>

<script>
$(function($) {
	$('.list').pagination({
		element      :'li',
		prevText     : '＜',
		nextText     : '＞',
		firstText    : '≪',
		lastText     : '≫',
		ellipsisText : '…',
		viewNum      : 12,
		pagerNum     : 3,
		ellipsis     : true,
		linkInvalid  : true,
		goTop        : true,
		firstLastNav : true,
		prevNextNav  : true
	});

});
</script>

<!-- ログインの表示処理をまとめています -->
<jsp:include page="head_login.jsp" />

<!-- 検索、カート等のバー表示処理をまとめています -->
<jsp:include page="head_bar.jsp" />

<!-- ▼ここから予備バー▼ -->
<div class="row no-gutters">
    <div class="col-12" style="background:#f4f4f4; height:20px;">
    </div>
</div>
<!-- ▲ここまで予備バー▲ -->

<!-- ▼ここより下カラム▼ -->
<div class="row no-gutters padding_bottom_20">

    <!-- ▼ここより左余白▼ -->
    <div class="col-2" style="background:#ffffff;">
    </div>
    <!-- ▲ここまで左余白▲ -->

    <!-- ▼ここより中央カラム▼ -->
    <div class="col-8" style="background:#ffffff;">
        <div class="row no-gutters">

			<!-- ▼ここから左カラム▼ -->
            <div class="col-3" style="background:#ffffff;">

                <!-- ▼ここからカテゴリーカラム▼ -->
                <div class="card border-primary text-primary">
                <form method="post" action="category">
                    <h6 class="card-header text-white">カテゴリー</h6>
                    <div class="card-body">
                    <p class="card-text">日本酒</p>
                    <p class="card-text">洋酒</p>
                    <p class="card-text">ビール</p>
                    <p class="card-text">ワイン</p>
                    <p class="card-text">焼酎</p>
                    <p class="card-text">おつまみ</p>
                    </div>
                </form>
                </div>
                <!-- ▲ここまでカテゴリーカラム▲ -->

                <!-- ▼ここから条件カラム▼ -->
                <div class="card border-primary text-primary">
                    <h6 class="card-header text-white">条件から探す</h6>
                    <div class="card-body">
                    <p class="card-text">1,000円未満</p>
                    <p class="card-text">1,000円以上2,000円未満</p>
                    <p class="card-text">2,000円以上3,000円未満</p>
                    <p class="card-text">3,000円以上4,000円未満</p>
                    <p class="card-text">4,000円以上5,000円未満</p>
                    <p class="card-text">5,000円以上</p>
                    </div>
                </div>
                <!-- ▲ここまで条件カラム▲ -->
			</div>
			<!-- ▲ここまで左カラム▲ -->

        	<!-- ▼ここから右カラム▼ -->
            <div class="col-9" style="background:#ffffff;">

                <div class="col-12">

                    <!-- ▼ここから表示並べ替え▼ -->
                    <div class="col-12 d-flex align-items-center justify-content-end" style="background:#f4f4f4; margin-top:20px">
                        <div class="dropdown" style=" margin-top:10px; margin-bottom:10px;">
                          <button class="btn btn-whity dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" onfocus="this.blur()">
                            表示を並びかえる
                          </button>
                          <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <a class="dropdown-item" href="#">新着順</a>
                            <a class="dropdown-item" href="#">値段順</a>
                            <a class="dropdown-item" href="#">人気順</a>
                          </div>
                        </div>
                    </div>
                    <!-- ▲ここまで表示並べ替え▲ -->

                    <!-- ▼ここから商品▼ -->


                    <div class="list">

						<ul>
<%
	for(int i=1;i <=  s_list.size(); i++){
%>
							<li>



<div class="card">
								 <form method="post" name= "form<%=i%>" action= "sake_c">
		                        	<input type="hidden" name="button" value="detail">
                        			<input type="hidden" name="getnumber" value="<%=i-1%>">
		                            <a href="javascript:form<%=i%>.submit()"class="card-link" style="color: #5b5b5b">
			                            <img src="image/<%=s_list.get(i-1).getS_IMG()%>" class="card-img-top">
			                            <div class="card-body" style="height:100px">
			                            	<h6 class="card-title"><%=s_list.get(i-1).getS_Name()%></h6>
			                            </div>
			                            <div class="card-footer text-right">
			                            	<%=s_list.get(i-1).getS_Price()%> 円
			                            </div>
		                            </a>
		                            </form>
</div>



							</li>
<%
	}
%>

						</ul>

						<div class="pager">
						</div>

					<div class="pageNum" style="color: 5b5b5b">
						<span class="nownum"></span>/<span class="totalnum"></span>pages
					</div>

				</div><!-- /list -->



                    <!-- ▲ここまで商品▲ -->


                </div>
            </div>
            <!-- ▲ここまで右カラム▲ -->
        </div>

    </div>
    <!-- ▲ここまで中央カラム▲ -->

	<!-- ▼ここから右余白▼ -->
    <div class="col-2" style="background:#ffffff;">
    </div>
	<!-- ▲ここまで右余白▲ -->

</div>
<!-- ▲ここまで下カラム▲ -->

<!-- ▼ここから下会社概要▼ -->
<div class="row no-gutters" style="margin-top: 20px;">
    <div class="col-2" style="background: #f0f0f0; height: 120px">
    </div>
    <div class="col-8" style="background: #f0f0f0; height: 120px">
    <a href="s-style.html">style</a><br>
    <a href="index.html">index</a><br>
    <a href="c-sake.html">sake</a><br>
    <a href="c-login.html">login</a><br>
    </div>
    <div class="col-2" style="background: #f0f0f0; height: 120px">
    </div>
</div>
<!-- ▲ここまで下会社概要▲ -->

<!-- ▼ここから下商標▼ -->
<div class="row no-gutters">
    <div class="col-12  d-flex align-items-center justify-content-center" style="background: #f0f0f0; height: 60px; color: #5b5b5b;">Copyrights 2018 Team IZAKAYA All Rights Reserved.
    </div>
</div>
<!-- ▲ここまで下商標▲ -->


</body>

</html>



<!--

-->
