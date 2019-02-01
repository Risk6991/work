package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import bean.R_Bean;
import bean.S_Bean;

public class SakeDAO_I extends DAO {
	//商品（酒）の表示するカラム
	String strS_SQL ="SELECT saketown.sake.S_NO,saketown.sake.S_NAME,saketown.sake.S_PRICE,"
			+ " saketown.sake.S_INFO,saketown.category.CATE_CATEGORY_NAME FROM saketown.sake inner join saketown.category "
			+ " ON saketown.sake.S_CATEGORY_NO=saketown.category.CATE_NO";

	String strR_SQL ="SELECT saketown.review.R_NO,saketown.review.R_NAME,saketown.customer.C_NAME,saketown.sake.S_NAME,saketown.review.R_TEXT "
			+ " FROM (saketown.review inner join saketown.customer on saketown.review.C_NO=saketown.customer.C_NO)"
			+ " inner join saketown.sake on saketown.review.S_NO = saketown.sake.S_NO";

	//管理者 商品検索用のSQL文
	String strM_SQL = "SELECT sk.S_NO,sk.S_NAME,sk.S_STOCK,sk.S_PRICE,sk.S_INFO,"
			+ "cg.CATE_NO,cg.CATE_CATEGORY_NAME FROM saketown.sake as sk"
			+ " inner join saketown.category as cg ON saketown.sk.S_CATEGORY_NO"
			+ "=saketown.cg.CATE_NO ";
	//全表示用メソッド
	//メソッドを増やすか。汎用させるかは、相談案件
	public List<S_Bean> search( ) throws NamingException,SQLException{
		List<S_Bean>list = new ArrayList<S_Bean>();
		Connection con =null;
		PreparedStatement pst =null;
		try{
			con = getConnection();
			//全件表示
			pst = con.prepareStatement(strS_SQL);

			ResultSet rs =pst.executeQuery();
			//リストにセットを行う
			while(rs.next()){
				S_Bean s =new S_Bean();

				s.setS_No(rs.getInt("S_NO"));
				s.setS_Name(rs.getString("S_NAME"));
				s.setS_Price(rs.getInt("S_PRICE"));
				s.setS_Info(rs.getString("S_Info"));
				s.setCATE_Name(rs.getString("CATE_CATEGORY_NAME"));

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
	//キーワード検索のDAOのパターン
	public List<S_Bean> search(String strkeyword ) throws NamingException,SQLException{
		List<S_Bean>list = new ArrayList<S_Bean>();
		Connection con =null;
		PreparedStatement pst =null;
		try{
			con = getConnection();
			//名前検索
			pst = con.prepareStatement(strS_SQL + " WHERE saketown.sake.S_NAME like ? ");

			//部分一致ID検索
			pst.setString(1, "%" + strkeyword + "%");
			ResultSet rs =pst.executeQuery();
			//リストにセットを行う
			while(rs.next()){
				S_Bean s =new S_Bean();

				s.setS_No(rs.getInt("S_NO"));
				s.setS_Name(rs.getString("S_NAME"));
				s.setS_Price(rs.getInt("S_PRICE"));
				s.setS_Info(rs.getString("S_Info"));
				s.setCATE_Name(rs.getString("CATE_CATEGORY_NAME"));

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

	//番号検索のDAOのパターン
	public List<S_Bean> search(int intS_Num ) throws NamingException,SQLException{
		List<S_Bean>list = new ArrayList<S_Bean>();
		Connection con =null;
		PreparedStatement pst =null;
		try{
			con = getConnection();
			//名前検索
			pst = con.prepareStatement(strS_SQL + " WHERE saketown.sake.S_NO = ? ");

			//部分一致ID検索
			pst.setInt(1,intS_Num );
			ResultSet rs =pst.executeQuery();
			//リストにセットを行う
			while(rs.next()){
				S_Bean s =new S_Bean();

				s.setS_No(rs.getInt("S_NO"));
				s.setS_Name(rs.getString("S_NAME"));
				s.setS_Price(rs.getInt("S_PRICE"));
				s.setS_Info(rs.getString("S_Info"));
				s.setCATE_Name(rs.getString("CATE_CATEGORY_NAME"));

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
	//レビュー番号検索のDAOのパターン
	public List<R_Bean> R_search(int intS_Num ) throws NamingException,SQLException{
		List<R_Bean>list = new ArrayList<R_Bean>();
		Connection con =null;
		PreparedStatement pst =null;
		try{
			con = getConnection();
			//名前検索
			pst = con.prepareStatement(strR_SQL + " WHERE saketown.sake.S_NO = ? ");

			//部分一致ID検索
			pst.setInt(1,intS_Num );
			ResultSet rs =pst.executeQuery();
			//リストにセットを行う
			while(rs.next()){
				R_Bean s =new R_Bean();


				s.setR_No(rs.getInt("R_NO"));
				s.setR_Name(rs.getString("R_NAME"));
				s.setR_Text(rs.getString("R_TEXT"));

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

	//Manager管理で商品検索を行うためのメソッド
	//使用するのはManager_S.java です
	//1つ目の引数は、条件分岐用のstr値、
	//2つ目がパラメータから受け取る値をBeanクラスを使って受け取っています。
	//ito
	public  List<S_Bean>search(String s , S_Bean ins)throws NamingException, SQLException{
		//ResalsSetオブジェクトはDBから切断されると破棄されるため、
		//新しく詰め替え用のリストを用意する。
		List<S_Bean> list = new ArrayList<>();
			System.out.println("s = " + s);

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
				case "s_no_search":
					search = "WHERE saketown.sk.S_NO=" + ins.getS_No();
					break;
				case "s_name_search" :
					search = "WHERE  saketown.sk.S_NAME LIKE '%" + ins.getS_Name() + "%';";
					break;
				case "s_price_search":
					search = "WHERE saketown.sk.S_PRICE=" + ins.getS_Price();
					break;
				case "s_category_no_search":
					search = "WHERE saketown.cg.CATE_NO=" + ins.getCATE_No();
					break;
				case "s_stock_search":
					search = "WHERE saketown.sk.S_STOCK=" + ins.getS_Stock();
					break;

			}
			st = con.prepareStatement(strM_SQL + search);
			rs = st.executeQuery();

			while(rs.next()){		//条件に合う中身をすべて呼ぶためのメソッド
				S_Bean s_ins = new S_Bean();
				s_ins.setS_No(rs.getInt("S_NO"));
				s_ins.setS_Name(rs.getString("S_NAME"));
				s_ins.setS_Price(rs.getInt("S_PRICE"));
				s_ins.setS_Info(rs.getString("S_INFO"));
				s_ins.setS_Categorry_No(rs.getInt("CATE_NO"));
				s_ins.setCATE_Name(rs.getString("CATE_CATEGORY_NAME"));
				s_ins.setS_Stock(rs.getInt("S_STOCK"));
				list.add(s_ins);
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

	//insert用メソッド(商品追加用メソッド)
	//int型なのはint値で成否判定をできるように
	//引数はBeanクラスのためins作成すればメソッドで引数呼べます
	public int S_insert(S_Bean insS) throws  SQLException,NamingException{
		int unUseStock = 0;//使用しないプレースホルダ
		int unUseDelivery = 0;//使用しないプレースホルダ
		int line = 0;		//成否判断用
		PreparedStatement st = null;
		Connection con = null;

 		String insertSql = "INSERT INTO saketown.sake VALUES(?,?, ?, ?, ?, ?, ?);";

		try{
			con = getConnection();
			st = con.prepareStatement(insertSql);

			//プレースホルダ(SQL文中の?のこと。あとで値を設定できる)
			//メソッド引数にBeanクラスが指定されており、
			//第一引数は、一つ目のint値、二つ目が指定した値を受け取る

			st.setInt(1, insS.getS_No());
			st.setString(2, insS.getS_Name());
			st.setInt(3, insS.getS_Price());

			st.setInt(4, unUseStock);

			st.setString(5, insS.getS_Info());

			st.setInt(6, insS.getS_Categorry_No());

			st.setInt(7, unUseDelivery);
			//0:失敗、1:成功(自分メモ)
			line = st.executeUpdate();

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





}