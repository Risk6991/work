package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import bean.C_Bean_H;

public class CustmerDAO_M extends DAO {
	//顧客情報を表示するカラム
	String strC_nSQL ="SELECT * FROM saketown.customer ";

	//重複チェックメソッド
	//メソッドを増やすか。汎用させるかは、相談案件
	public List<C_Bean_H> search(String strMail) throws NamingException,SQLException{
		List<C_Bean_H>list = new ArrayList<C_Bean_H>();
		Connection con =null;
		PreparedStatement pst =null;
		try{
			con = getConnection();
			//全件表示
			pst = con.prepareStatement(strC_nSQL + "  WHERE saketown.customer.C_MAIL_ID = ? ");

			pst.setString(1,strMail);

			ResultSet rs =pst.executeQuery();
			//リストにセットを行う
			while(rs.next()){
				C_Bean_H s =new C_Bean_H();


				s.setC_Mail_Id(rs.getString("C_MAIL_ID"));

				list.add(s);


			}
		}finally{
			if(pst !=null){
				pst.close();
			}
			if(con !=null){
				con.close();
			}
		}

		return list;

	}

	//Manager管理で会員(顧客)検索を行うためのメソッド
	//使用するのはManager_C.java です
	//1つ目の引数は、条件分岐用のstr値、
	//2つ目がパラメータから受け取る値をBeanクラスを使って受け取っています。
	//ito
	public  List<C_Bean_H>c_Search(String s , C_Bean_H ins)throws NamingException, SQLException{
		//ResalsSetオブジェクトはDBから切断されると破棄されるため、
		//新しく詰め替え用のリストを用意する。
		List<C_Bean_H> list = new ArrayList<>();
			System.out.println("s = " + s);

		//SQL文を詰めるメソッドの宣言。
		//int型とString型があるため2つ用意
		//もっと良い方法があるかも

		//8/21更新　もっと良い方法ありました。
		//setInt,Stringメソッドを使わず直接SQL文にBeanクラスのgetterを利用しました。
		//そうすることでPreparaの変数を1つで処理することができるし、わかり易い。
		PreparedStatement st = null;
		//PreparedStatement pst = null;
		ResultSet rs = null;

		//DB接続用のメソッド
		Connection con = null;
		//検索用のwhere文(SQL)を詰めるフィールド
		String search = null;
		try{
			con = getConnection();//DBに接続

			//1つ目の引数による分岐
			switch (s) {
				case "number":
					search = "WHERE  saketown.customer.C_NO =" + ins.getC_No();
					break;
				case "name" :
					search = "WHERE  saketown.customer.C_NAME LIKE '%" + ins.getC_Name() + "%';";
					break;
				case "address":
					search = "WHERE  saketown.customer.C_ADDRES LIKE '%" + ins.getC_Addres() + "%';";

			}
			st = con.prepareStatement(strC_nSQL + search);
			rs = st.executeQuery();

			while(rs.next()){		//条件に合う中身をすべて呼ぶためのメソッド
				C_Bean_H c_ins = new C_Bean_H();
				c_ins.setC_No(rs.getInt("C_NO"));
				c_ins.setC_Name(rs.getString("C_NAME"));
				c_ins.setC_Addres_No(rs.getInt("C_ADDRES_NO"));
				c_ins.setC_Addres(rs.getString("C_ADDRES"));
				c_ins.setC_Mail_Id(rs.getString("C_MAIL_ID"));
				list.add(c_ins);
			}
				System.out.println("listsize = " + list.size());
		} finally{
			if(st != null){
				st.close();
			}
			if(con != null){
				con.close();
			}
		}
		return list;
	}
	//顧客情報追加
	public int insert(C_Bean_H C_Bean) throws SQLException,NamingException{
		int line=0;
		Connection con = null;
		PreparedStatement st= null;
		try{
			con =getConnection();
			st=con.prepareStatement("insert into saketown.customer values(null,?,?,?,?,?)");

			st.setString(1,C_Bean.getC_Name());
			st.setString(2,C_Bean.getC_Addres());
			st.setInt(3,C_Bean.getC_Addres_No());
			st.setString(4,C_Bean.getC_Mail_Id());
			st.setString(5,C_Bean.getC_PassWord());

			line=st.executeUpdate();

		}finally{

			if(con!=null){
				con.close();
			}
			if(st!=null){
				st.close();
			}
		}

		return line;
	}
	//顧客情報更新
	public int update(C_Bean_H C_Bean) throws SQLException,NamingException{
		int line=0;
		Connection con = null;
		PreparedStatement st= null;
		try{
			con =getConnection();
			st=con.prepareStatement(" UPDATE saketown.customer SET C_NAME =? , C_ADDRES =?, C_ADDRES_NO =?,"
					+ " C_MAIL_ID=?, C_PASSWORD = ? WHERE C_NO =? ");

			st.setString(1,C_Bean.getC_Name());
			st.setString(2,C_Bean.getC_Addres());
			st.setInt(3,C_Bean.getC_Addres_No());
			st.setString(4,C_Bean.getC_Mail_Id());
			st.setString(5,C_Bean.getC_PassWord());
			st.setInt(6,C_Bean.getC_No());

			line=st.executeUpdate();

		}finally{

			if(con!=null){
				con.close();
			}
			if(st!=null){
				st.close();
			}
		}

		return line;
	}
	//顧客情報削除
	public int delete(C_Bean_H C_Bean) throws SQLException,NamingException{
		int line=0;
		Connection con = null;
		PreparedStatement st= null;
		try{
			con =getConnection();
			st=con.prepareStatement(" DELETE FROM saketown.customer WHERE C_NO =?");

			st.setInt(1,C_Bean.getC_No());

			line=st.executeUpdate();

		}finally{

			if(con!=null){
				con.close();
			}
			if(st!=null){
				st.close();
			}
		}

		return line;
	}



}