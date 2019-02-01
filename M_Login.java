package saketown;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.M_Bean;
import dao.ManagerDAO_I;

public class M_Login extends HttpServlet{

	//DAOにアクセスする
	ManagerDAO_I dao = new ManagerDAO_I();

	//要素が 型パラメータ（Beanクラス）のList型 変数の宣言
	//ex.String型のArrayListと同一(ArrayList<String>)
	List<M_Bean> list;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException
	{
		//フィールド
		String strReqId = null;
		Integer intReqId = null;		//Integer値を用意してあるのでnullで条件分岐、例外処理可能
		String strReqPass = null;

		String strUrl = null;
		String errorMessage = null;

		//存在すればsessionの取得、無ければ開始を行う
		HttpSession session = req.getSession();

		try {
			//getParameterメソッド
			//form からID(String値)受け取り、intに変換を行う
			strReqId = req.getParameter("getM_Id");
				System.out.println("strReqId = " + strReqId);
			strReqPass = req.getParameter("getM_Password");
				System.out.println("strReqPass = " + strReqPass);

			//例外処理をここで作成すること。
			//現在、動作確認用のみ
			if(!("".equals(strReqId)) && !("".equals(strReqPass))){
				try{
					intReqId = Integer.parseInt(strReqId);
						System.out.println("intReqId = " + intReqId);
						list = dao.search(intReqId,strReqPass);
						System.out.println("listsize =" + list.size());

					if( list.size() != 0){
						//sessionスコープにデータを預ける
						session.setAttribute("login", list);
					} else {
						errorMessage = "パスワードが間違っています。";
					}
				} catch (NumberFormatException e){
					errorMessage = "ID入力が不正です。";
				}
			} else {
				errorMessage = "異常な入力です。値が入力されていません。";
			}

			//エラーの最終処理
			//エラー判定があればsessionにstr値を預け、エラー用のページへ。
			if(errorMessage == null){
				strUrl = "m_index.jsp";

			} else {
				req.setAttribute("error", errorMessage);
				strUrl = "m_login_ng.jsp";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServletException();
		} catch (NamingException e) {
			throw new ServletException();
		}

		//画面遷移(フォワード)する
		req.getRequestDispatcher(strUrl)
								.forward(req, resp);
	}
}