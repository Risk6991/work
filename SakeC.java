package saketown;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.C_LoginBean;



public class SakeC extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{}
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException
	{
		//フィールド
		String errorMessage = null;
		String strMessage = null;
		String strListNumber = null;
		Integer intListNumber = null;

		String strUrl = null;
		String strKeyword = null;
		String strAge = null;
		String strCategory=null;

		String action = null;
		String flag = null;

		//sessionの開始または取得
		HttpSession session = req.getSession();
		session.removeAttribute(flag);

		//ログイン処理確認用のsession
		List<C_LoginBean>c_list = (List<C_LoginBean>)session.getAttribute("login");

		//各jspからbuttonのパラメータ(action)を受け取る
		//actionの値からswitch分岐させる
		//分岐先で各処理を行う。 ex.indexから受け取るパラメータは top
		action = req.getParameter("button");
			System.out.println("action = " + action);

			switch (action){
				case "top":
					//Sakeサーブレット上での動作を選択するflagを立てる
					//リクエストスコープに保存しSakeサーブレットへ
					flag = "top";
					req.setAttribute("flag", flag);
					strUrl = "sake";
					break;
				case "detail":
					//詳細ページ表示用の番号をc_top.jspから受け取る
					strListNumber = req.getParameter("getnumber");
						System.out.println("strListNumber = " + strListNumber);
					//ログイン情報を確認して、ログインしていたらDBにアクセスDB閲覧履歴に登録
					try{
							intListNumber  = Integer.parseInt(strListNumber);
								System.out.println("intListNumber = " + intListNumber);

						} catch (NumberFormatException e){
							errorMessage = "異常な入力です";
						}
					req.setAttribute("listnumber", intListNumber);
					strUrl = "c_sake.jsp";
					break;

				case "start_login":
					//sessionを受け取って中身があればメンバーサイトへ
					//無ければログイン用サイトへ
					if(c_list == null){
						strUrl = "c_login.jsp";
					} else if(c_list.size() != 0){
						strUrl = "c-member-page.jsp";
					} else {
						strUrl = "c_login.jsp";
					}
					break;

				case "logout":
					//sessionをすべて破棄する。
					session.invalidate();
					strMessage = "ログアウトしました。";
					req.setAttribute("strMessage", strMessage);
					strUrl = "c-message.jsp";
					break;

				case "search":
					//Sakeサーブレット上での動作を選択するflagを立てる
					//リクエストスコープに保存しSakeサーブレットへ
					flag = "search";
					req.setAttribute("flag", flag);

					//c_top.jspからSearch用キーワードを受け取る
					//リクエストスコープに保存しSakeサーブレットへ
					strKeyword = req.getParameter("keyword");
						System.out.println("strKeyword = " + strKeyword);
					req.setAttribute("keyword", strKeyword);
					strUrl = "sake";
					break;

				case "cate_search":
					//Sakeサーブレット上での動作を選択するflagを立てる
					//リクエストスコープに保存しSakeサーブレットへ
					flag = "cate_search";
					req.setAttribute("flag", flag);

					//c_top.jspからSearch用キーワードを受け取る
					//リクエストスコープに保存しSakeサーブレットへ
					strCategory = req.getParameter("category");
						System.out.println("strCategory = " + strCategory);
					req.setAttribute("category", strCategory);
					strUrl = "sake";
					break;

				case "cart":
					//カート処理が複雑のため、Cart.javaサーブレットへの橋渡しのみ
					strUrl = "cart";
					break;

				case "buy":
					//c_cart.jspから購入用パラメータを受け取り
					//ログイン既未の確認処理を行う
					if(c_list == null){
						strUrl = "c_login.jsp";
					} else if(c_list.size() != 0){
						strUrl = "c_confirmation.jsp";
					} else {
						strUrl = "c_login.jsp";
					}
					break;

				case "confirm":
					//c_cart.jspから年齢確認用のパラメータを受け取り
					//年齢の確認処理を行う
					strAge = req.getParameter("majority");
					if("OK".equals(strAge)){
						strUrl = "c-member-registration.jsp";
					}
					if("NG".equals(strAge)){
						strUrl = "c-minority.jsp";
					}
					break;

				case "sales":
					//c_confirmation.jspから購入確定のパラメータを受け取り
					//購入後の売り上げ処理を行うSalesサーブレットへ
					strUrl = "sales";
					break;

				case"history":
					//購入履歴：Historyサーブレットからc-member-history.jspに遷移
					strUrl="history";
					break;

				case"r_write":
					//レビューの記載ページへの遷移
					//Review_Writerサーブレットからc-member-review.jspへ
					//商品情報を送って遷移
					strUrl="review_write";
					break;

				case"review":
					//レビューの登録:
					//reviewサーブレットから記載したレビューをDBに登録
					//結果をc-message.jspに表示
					strUrl="review";
					break;

				case"renewal":
					//undispatchedで未発送賞品が無いかチェック
					//会員情報更新画面へ遷移
					strUrl="undispatched";
					break;
				case"update":
					//Custmerサーブレットから、更新処理をして、結果をc-message.jspに表示
					strUrl="custmer";
					break;

				case"delete":
					//会員情報削除はカスタマーサーブレットで行う
					//結果をc-message.jspに表示
					strUrl ="custmer";
					break;
				case"unsent":
					//未発送リストの表示：Undispatchedサーブレットからc-unsent-list.jspに遷移
					strUrl ="undispatched";
					break;

				case"view":
					//閲覧履歴リストの表示
					//Viewサーブレットからc-browsing-history.jspに遷移
					strUrl ="view";
					break;

				default:
					errorMessage = "action error パラメータが飛んでいません";
					break;
			}

		RequestDispatcher dispatcher = req.getRequestDispatcher(strUrl);
		dispatcher.forward(req,resp);
	}
}