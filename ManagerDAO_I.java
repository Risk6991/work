package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import bean.M_Bean;
import bean.Unsent_Bean;

public class ManagerDAO_I extends DAO{
	//SQL文をstring型の変数に代入
	//as文でスキーマ名を短縮しています。
	String strSqlUrl = "SELECT mg.M_ID,mg.M_NAME,mg.M_PASSWORD,mg.M_POWER"
								+ " from saketown.manager as mg";

	//seachメソッド。ID/PASS 検索を行う。
	public List<M_Bean> search(Integer id,String pass) throws SQLException,NamingException {

		//ResalsSetオブジェクトはDBから切断されると破棄されるため、
		//新しく詰め替え用のリストを用意する。
		List<M_Bean> list = new ArrayList<>();
		System.out.println("id = " + id);
		System.out.println("pass = " + pass);

		//SQL文を詰めるオブジェクト
		PreparedStatement st = null;
		//検索用のwhere文(SQL)
		String searchSql =  " WHERE M_ID = ? AND M_PASSWORD = ?;";

		//接続と結果をあらわすオブジェクト
		Connection con = null;

		try{
			//DBに接続するメソッド
			con = getConnection();

			if(id != null && pass != null){
				//SQL文をDBに送るためのメソッド
				st = con.prepareStatement(strSqlUrl + searchSql);
			} else{
				//SQL文をDBに送るためのメソッド
				st = con.prepareStatement(strSqlUrl);
			}
			//setStringメソッド
			//プレースホルダ(SQL文中の?のこと。あとで値を設定できる)
			//Integer型で第一引数を設定。第二引数にstr型を設定
			st.setInt(1, id);
			st.setString(2, pass);
			System.out.println(st);

			ResultSet rs = st.executeQuery();

			while(rs.next()){
				M_Bean m_ins = new M_Bean();
				m_ins.setM_Id(rs.getInt("M_ID"));
				m_ins.setM_Name(rs.getString("M_NAME"));
				//長谷川追加部分
				m_ins.setM_Power(rs.getInt("M_POWER"));
				list.add(m_ins);
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

	//seachメソッド。管理者検索(getM_Id)を行う。
	//ID,PASS用のseachメソッドと統合できるかも？
	//現状は動作テストのため分けます
	public List<M_Bean>search(M_Bean ins) throws SQLException,NamingException {
		//ResalsSetオブジェクトはDBから切断されると破棄されるため、
		//新しく詰め替え用のリストを用意する。
		List<M_Bean> list = new ArrayList<>();
		PreparedStatement st = null;
		//DB接続用のメソッド
		Connection con = null;

		//検索用のwhere文(SQL)を詰めるフィールド
		String searchSql = null;
		try{
			con = getConnection();
			searchSql = " WHERE mg.M_NAME LIKE '%" + ins.getM_Name() + "%';";
				System.out.println(searchSql);
			//SQL文をDBに送るためのメソッド
			st = con.prepareStatement(strSqlUrl + searchSql);
			ResultSet rs = st.executeQuery();

			while(rs.next()){		//条件に合う中身をすべて呼ぶためのメソッド
				M_Bean m_ins = new M_Bean();
				m_ins.setM_Id(rs.getInt("M_ID"));
				m_ins.setM_Name(rs.getString("M_NAME"));
				//長谷川追加部分
				m_ins.setM_PassWord(rs.getString("M_PASSWORD"));
				//長谷川追加部分
				m_ins.setM_Power(rs.getInt("M_POWER"));
				list.add(m_ins);
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

	//insert用メソッド
	//int型なのはint値で成否判定をできるように
	//引数はBeanクラスのためins作成すればメソッドで引数呼べます
	public int insert(M_Bean ins) throws  SQLException,NamingException{
		/*int auth = 0;	//管理者の権限判定用
		//初期値が0で権限なし扱いにする予定
		//現在未実装のため、実装の下準備扱いです。*/

		int line = 0;		//成否判断用 int値でinsertの判別を行う
		PreparedStatement st = null;
		Connection con = null;
 		String insertSql = "INSERT INTO saketown.manager VALUES (?,?,?,?);";
		try{
			con = getConnection();
			st = con.prepareStatement(insertSql);
				System.out.println("PreparedStatement st = " + st);

			//プレースホルダ(SQL文中の?のこと。あとで値を設定できる)
			//メソッド引数にBeanクラスが指定されており、
			//第一引数は、一つ目のint値、二つ目が指定した値を受け取る

			st.setInt(1, ins.getM_Id());
			st.setString(2, ins.getM_PassWord());
			st.setString(3,ins.getM_Name());
			st.setInt(4,ins.getM_Power());

			line = st.executeUpdate();
				System.out.println("line = " + line);

		} finally{
			if(st != null){
				st.close();
			}
			if(con != null){
				con.close();
			}
		}
		return line;
	}

	//未発送リストメソッド
	public  List<Unsent_Bean> M_Unsent()throws NamingException, SQLException{
		List<Unsent_Bean> list = new ArrayList<>();

		//現在、SAKEテーブルにおいて発送管理を行う機能が実装されていません。
		//そのためテスト用としてS_Deliveryカラムを用意し、int値の0が在庫アリ、
		//在庫なしが1で入力されています。
		//そのため未発送リストは値が1のものを表示するものです。
		int  intAuth = 1;

		String sql = "select saketown.uriage.U_NO, saketown.sake.S_NO,"
				+" saketown.sake.S_NAME, saketown.sake.S_PRICE,"
				+" saketown.sake.S_STOCK,saketown.customer.C_NO, saketown.customer.C_NAME,"
				+ " saketown.sake.S_Delivery"
				+ " from"
				+" (saketown.uriage INNER JOIN saketown.sake ON saketown.uriage.S_NO=saketown.sake.S_NO)"
				+" INNER JOIN saketown.customer ON saketown.customer.C_NO=saketown.uriage.C_NO"
				+" where saketown.sake.S_Delivery=?";

		PreparedStatement st = null;
		Connection con = null;
		try{
			con = getConnection();
			st = con.prepareStatement(sql);
				System.out.println(st);
				st.setInt(1, intAuth);
				ResultSet rs = st.executeQuery();
				while(rs.next()){
					Unsent_Bean u = new Unsent_Bean();
					u.setU_No(rs.getInt("uriage.U_NO"));
					u.setS_No(rs.getInt("sake.S_NO"));
					u.setS_Name(rs.getString("sake.S_NAME"));
					u.setS_Price(rs.getInt("sake.S_PRICE"));
					u.setS_Stock(rs.getInt("sake.S_STOCK"));
					u.setC_No(rs.getInt("customer.C_NO"));
					u.setC_Name(rs.getString("customer.C_NAME"));
					u.setIntS_Delivery(rs.getInt("sake.S_Delivery"));
					list.add(u);
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

}
