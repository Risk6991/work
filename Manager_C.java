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

import bean.C_Bean_H;
import dao.CustmerDAO_M;

public class Manager_C extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{}
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException
	{
		//フィールド
		String errorMessage = null;
		String strUrl = null;
		String strReqNo = null;
		String strReqName = null;
		String strReqAddress = null;

		String action = null;
		Integer intReqNo = null;

		try{
			//sessionの開始または取得
			HttpSession session = req.getSession();
			//DAOにアクセス
			CustmerDAO_M dao = new CustmerDAO_M();
			//m_manager_registration.jspからパラメータを受け取る
			//buttonの値からswitch分岐させる
			//分岐先でBeanクラスのインスタンスに値をsetする
			C_Bean_H ins = new C_Bean_H();
			action = req.getParameter("button");

			switch (action){
				case "number":
					strReqNo = req.getParameter("c_no");
						System.out.println("strReqNo = " + strReqNo);
					if(!("".equals(strReqNo))){
						try{
							intReqNo  = Integer.parseInt(strReqNo);
							ins.setC_No(intReqNo);
						} catch (NumberFormatException e){
							errorMessage = "異常な入力です";
						}
					} else{
						errorMessage = "異常な入力です。値が入力されていません。";
					}
					break;
				case "name":
					strReqName = req.getParameter("c_name");
						System.out.println("strReqName = " + strReqName);
					if(!("".equals(strReqName))){
					ins.setC_Name(strReqName);
					} else {
						errorMessage = "異常な入力です。値が入力されていません。";
					}
					break;
				case "address":
					strReqAddress = req.getParameter("c_address");
						System.out.println("strReqAddres = " + strReqAddress);
					if(!("".equals(strReqAddress))){
						ins.setC_Addres(strReqAddress);
					} else {
						errorMessage = "異常な入力です。値が入力されていません。";
					}
					break;
				default:
					errorMessage = "action error パラメータが飛んでいません";
					break;
			}
			//型パラメータ（Beanクラス）のList型 変数の宣言
			List<C_Bean_H> list = new ArrayList<C_Bean_H>();
			if(errorMessage == null && action != null){
				list = dao.c_Search(action,ins);
				//データがDBにあり無事に値が取得できていれば取得件数(int)が入る。
				if(list.size() != 0){
					session.setAttribute("custmer", list);
					strUrl = "m_customer_result.jsp";
				} else {
					errorMessage = "入力された値はＤＢに登録がありません。";
				}
			}
			if(errorMessage != null){
			req.setAttribute("error", errorMessage);
			strUrl = "m_ng.jsp";
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new ServletException();
		}catch(NamingException e){
			throw new ServletException();
		}
		RequestDispatcher dispatcher = req.getRequestDispatcher(strUrl);
		dispatcher.forward(req,resp);
	}
}