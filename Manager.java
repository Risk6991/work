package saketown;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.M_Bean;

public class Manager extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String strErrorMessage = "不正なアドレスです";
		req.setAttribute("errorMessage", strErrorMessage);
		RequestDispatcher rd = req.getRequestDispatcher("list.jsp");
		rd.forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException
	{
		resp.setContentType("text/html; charset=UTF-8");
		//フォワード先指定変数の初期化
		String strUrl = "";
		String errorMessage = "";
		String strIndex = null;

		//sessionの取得または開始
		HttpSession session = req.getSession();
		//jsp html用の書き込み準備
		PrintWriter out = resp.getWriter();

		//DBアクセスの準備
		Connection con =null;
		PreparedStatement st = null;

		//m_sake_update.jspよりgetParameterする
		String strReqNo = req.getParameter("s_no");
		String strReqName = req.getParameter("s_name");
		String strReqPrice = req.getParameter("s_price");
		String strReqInfo = req.getParameter("s_info");
		String strReqCate_No = req.getParameter("s_category_no");
		//int型の初期化
		int intPrice = -1;
		int intCATE_No = -1;
		int intDelivery = -1;
		int intLine = 0;
		Integer intIndex = -1;//m_sake_update.jspより飛ぶint値を利用するもの
		//管理者権限の情報を取得
		List<M_Bean> list = (List<M_Bean>)session.getAttribute("login");
		//フィールド
		int intM_Id = -1;
		//長谷川追加(権限変数)
		int intM_Power = 0;

		//例外処理(エラーメッセージ)
		//1.数値入力チェック
		//2.未入力チェック
		//3.入力文字数チェック
		//ただし、エラー文に関してはエラーが発生したときにすぐ表示できるように
		//if文の分岐とエラーメッセージを足し合わせてあります。
		if(strReqNo != null && !("".equals(strReqNo)) && strReqName != null && !("".equals(strReqName))
				&& strReqPrice != null && !("".equals(strReqPrice))
				&& strReqInfo != null && !("".equals(strReqInfo))
				&& strReqCate_No != null && !("".equals(strReqCate_No))) {
			//商品名が20文字以内に入力されていないときのエラー
			if(20 <= strReqName.length()){
				System.out.println("文字数は"+ strReqName.length());
				errorMessage += "20文字以内に入力してください。";
			}
			//商品情報が500文字以内に入力されていないときのエラー
			if(500 <= strReqInfo.length()){
				System.out.println("文字数は"+ strReqInfo.length());
				errorMessage += "500文字以内に入力してください。";
			}

			try{
				intM_Id  = Integer.parseInt(strReqNo);
				intPrice  = Integer.parseInt(strReqPrice);
				intCATE_No = Integer.parseInt(strReqCate_No);
				if(intM_Id < 0 || intPrice < 0 || intCATE_No < 0) {
					errorMessage +="正の値を入力してください.";
				}

			}catch(NumberFormatException e){
				errorMessage += "数字を入力してください";
			}

		} else{
			errorMessage += "値が未入力項目があります";
		}

		//m_index.jspよりParameterをgetParameter
		//例外処理いる？
		String action = req.getParameter("action");
			System.out.println("parameter取得直後 = "+action);
		//管理者情報の取得
		intM_Power = list.get(0).getM_Power();
		System.out.println("intM_Power = "+ intM_Power);
		//try catchの開始？(number?)
		try{

			//if条件分岐
			if("m_insert".equals(action)){
				//管理者新規登録画面遷移
				//M_Login.javaのsessionAttribute内容をgetAttributeで取る
				//intM_Power = (<List<M_Bean>>)list.get(0).getPower()
				//if(intM_Power == 1)→m_mnager_rejistration.jsp
				//else →errorMessageへ
				if(intM_Power == 1) {
				System.out.println("intM_Power" + intM_Power + 2);
				strUrl = "m_manager_registration.jsp";
				} else {
					errorMessage = "管理者権限がないため、登録できません。";
					req.setAttribute("error", errorMessage);
					strUrl = "m_ng.jsp";
				}
			} else if("m_search".equals(action)) {
				//管理者検索
				strUrl = "m_manager_search.jsp";
			} else if("m_update_page".equals(action)){
				//actionで判定して実施
				strIndex = req.getParameter("number");
				System.out.println("strIndex =" + strIndex);
				//try～catch
				try{
					intIndex = Integer.parseInt(strIndex);

				}catch(NumberFormatException e){
					System.out.println("商品IDで整数が得られませんでした");
				}
				req.setAttribute("intIndex", intIndex);
				//管理者情報更新
				strUrl = "m_manager_renewal.jsp";
			} else if("s_insert".equals(action)){
				//商品新規登録
				if(intM_Power == 1){
					System.out.println("intM_Power="+ intM_Power + 3);
					strUrl = "m_sake_registration.jsp";
				}else {
					errorMessage = "管理者権限がないため、登録できません。";
					req.setAttribute("error", errorMessage);
					strUrl = "m_ng.jsp";
				}

			} else if("s_search".equals(action)){
				//商品検索
				strUrl = "m_sake_search.jsp";
			} else if("s_update_page".equals(action)){
				//actionで判定して実施
				strIndex = req.getParameter("number");
				System.out.println("strIndex =" + strIndex);
				//try～catch
				try{
					intIndex = Integer.parseInt(strIndex);

				}catch(NumberFormatException e){
					System.out.println("商品IDで整数が得られませんでした");
				}
				req.setAttribute("intIndex", intIndex);
				//商品情報更新
				strUrl = "m_sake_update.jsp";
			} else if("s_delete_page".equals(action)){
				//actionで判定して実施
				strIndex = req.getParameter("number_del");
				System.out.println("strIndex =" + strIndex);
				//try～catch
				try{
					intIndex = Integer.parseInt(strIndex);

				}catch(NumberFormatException e){
					System.out.println("商品IDで整数が得られませんでした");
				}
				req.setAttribute("intIndex_del", intIndex);
				//商品情報削除
				strUrl = "m_delete-confirmation.jsp";
			} else if("c_search".equals(action)){
				//会員検索
				strUrl = "m_customer_search.jsp";
			} else if("not_dispatch_list".equals(action)){
				//未発送リスト
				strUrl = "unsent";
			} else if("salse_information".equals(action)){
				//売り上げ情報
				strUrl = "m_uriage.jsp";
			}
		//入力チェック
		}catch(NumberFormatException e){
			System.out.println("文字を入力してください");

		} finally{
			try{
				if(st != null){
					st.close();
				}
				if(con != null){
					con.close();
				}
			}catch(SQLException e){
				throw new ServletException();
			}
		}

		//フォワード
		//RequestDispatcher dispatcher = req.getRequestDispatcher(strUrl);
		//dispatcher.forward(req,resp);
		req.getRequestDispatcher(strUrl)
									.forward(req, resp);

	}
}
