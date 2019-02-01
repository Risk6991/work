package saketown;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.S_Bean;
import dao.SakeDAO;

public class Sake extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//フィールド
		String strUrl = null;
		String flag = null;
		String strKeyword = "";
		String strMessage = "";

		//List型で型パラメータがBeanクラスのlistを用意
		List<S_Bean>s_list=new ArrayList<S_Bean>();

		//セッションの作成
		HttpSession session =req.getSession();
			System.out.println(session.getId());

		//sakeDAOへのアクセス
		SakeDAO dao =new SakeDAO();

		//SakeCからのflagを受け取り
		//全件表示か検索かの動作分岐をさせる
		flag =(String)req.getAttribute("flag");
			System.out.println("flag = " + flag);

		//全件検索(トップ画面の表示)
		if("top".equals(flag)){
			try{
				s_list = dao.search();
					System.out.println("list.size() = " + s_list.size());

			} catch (SQLException e) {
				e.printStackTrace();
				throw new ServletException();
			} catch (NamingException e) {
				throw new ServletException();
			}
		}

		//キーワード検索(検索結果の表示)
		if("search".equals(flag)){
			try{
				//SakeCからリクエストスコープに預けた
				//formの値を受け取る
				strKeyword = (String)req.getAttribute("keyword");
					System.out.println("strKeyword = " + strKeyword);
				s_list = dao.search(strKeyword);
					System.out.println("list.size() = " + s_list.size());

			} catch (SQLException e) {
				e.printStackTrace();
				throw new ServletException();
			} catch (NamingException e) {
				throw new ServletException();
			}
		}

		//カテゴリ検索(検索結果の表示)
				if("cate_search".equals(flag)){
					try{
						//SakeCからリクエストスコープに預けた
						//formの値を受け取る
						strKeyword = (String)req.getAttribute("category");
						int intCate_No=Integer.parseInt(strKeyword);
							System.out.println("intCate_No = " + intCate_No);
						s_list = dao.Cate_search(intCate_No);
							System.out.println("list.size() = " + s_list.size());

					} catch (SQLException e) {
						e.printStackTrace();
						throw new ServletException();
					} catch (NamingException e) {
						throw new ServletException();
					}
				}


		if(s_list.size() != 0){
			//DBへのアクセス結果を詰めたlistを
			//sessionスコープに預ける
			session.setAttribute("list", s_list);
			strUrl = "c_top.jsp";
		} else {
			strMessage = strKeyword + "に対して検索結果は" + s_list.size() + "件です。";
			req.setAttribute("strMessage", strMessage);
			strUrl = "c-message.jsp";
		}
		//表示するJSPにフォワード
		RequestDispatcher rd = req.getRequestDispatcher(strUrl);
		rd.forward(req, resp);
	}
}
