package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class OrderModel{

	private static DataSource ds;

	static {
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			ds = (DataSource) envCtx.lookup("jdbc/storage");

		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	static final String TABLE_NAME = "ordine";

	
	public synchronized int doSave(OrderBean order) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		
		
		String insertSQL = "INSERT INTO " + TABLE_NAME
				+ " (email, stato, data, importo, carta, dataSpedizione, indirizzo) VALUES (?, ?, current_date(), ?, ?, DATE_ADD(current_date(), INTERVAL 5 DAY ), ?);";
		int code = 0;
		

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, order.getEmail());
			preparedStatement.setInt(2, order.getStato());
			
			preparedStatement.setDouble(3, order.getImporto());
			preparedStatement.setString(4, order.getCarta());
			
			preparedStatement.setString(5, order.getAddress());
			
			preparedStatement.executeUpdate();
			
		
			connection.commit();
			
			ResultSet keys = preparedStatement.getGeneratedKeys();
			keys.next();
			code = keys.getInt(1);
		} finally {
			try {
				//per SonarCloud: togliere questo controllo potrebbe causare nullPointerException
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return code;
		
		
	}
	
	public synchronized void doSaveContiene(List <ProductCartBean> products, int idOrdine) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO contiene (idProdotto, idOrdine, descrizione, prezzo, quantita, categoria,sconto,nome, iva) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
		
		

		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			
			Iterator<ProductCartBean> it = products.iterator();
			while (it.hasNext()) {
				ProductCartBean p = it.next();
				
				preparedStatement = connection.prepareStatement(insertSQL);
				
				preparedStatement.setInt(1, p.getProduct().getCode());
				preparedStatement.setInt(2, idOrdine);
				preparedStatement.setString(3, p.getProduct().getDescription());
				preparedStatement.setDouble(4, p.getProduct().getPrice());
				preparedStatement.setInt(5, p.getQuantityCart());
				preparedStatement.setInt(6, p.getProduct().getCategoria());
				preparedStatement.setDouble(7, p.getProduct().getSconto());
				preparedStatement.setString(8, p.getProduct().getName());
				preparedStatement.setDouble(9, p.getProduct().getIva());
				preparedStatement.executeUpdate();
				connection.commit();
			}
				
		}  finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		
		
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			
			Iterator<ProductCartBean> it = products.iterator();
			while (it.hasNext()) {
				ProductCartBean p = it.next();
				String updateSQL = "update prodotto set quantita = quantita - ? "
								+ " where id = ?";
				//la stringa viene gestita da PrepredStatement come suggerito da SonarCloud docs
				preparedStatement = connection.prepareStatement(updateSQL);
				
				preparedStatement.setInt(1, p.getQuantityCart());
				preparedStatement.setInt(2, p.getProduct().getCode());
				
				
				preparedStatement.executeUpdate();
				connection.commit();
			}
				
		} finally {
			//senza questo controllo abbiamo problemi di null pointer exception
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		
	}

	
	public synchronized OrderBean doRetrieveByKey(int code) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		OrderBean bean = new OrderBean();

		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, code);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				bean.setId(rs.getInt("id"));
				bean.setEmail(rs.getString("email"));
				bean.setStato(rs.getInt("stato"));
				bean.setData(rs.getDate("data"));
				bean.setImporto(rs.getDouble("importo"));
				bean.setCarta(rs.getString("carta"));
				bean.setDataSpedizione(rs.getDate("dataSpedizione"));
				bean.setAddress(rs.getString("indirizzo"));

			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return bean;
	}
	

	
	public synchronized boolean doDelete(int code) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, code);

			result = preparedStatement.executeUpdate();

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return (result != 0);
	}

	
	public synchronized Collection<OrderBean> doRetrieveAll(String order, String email) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<OrderBean> orders = new LinkedList<>();

		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE email = ?";
		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			//stringa  SQL gestita mediante prepared Statement
			preparedStatement = connection.prepareStatement(selectSQL);
			
			preparedStatement.setString(1, email);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				OrderBean bean = new OrderBean();

				bean.setId(rs.getInt("id"));
				bean.setEmail(rs.getString("email"));
				bean.setStato(rs.getInt("stato"));
				bean.setData(rs.getDate("data"));
				bean.setImporto(rs.getDouble("importo"));
				bean.setCarta(rs.getString("carta"));
				bean.setDataSpedizione(rs.getDate("dataSpedizione"));
				bean.setAddress(rs.getString("indirizzo"));
				orders.add(bean);
			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return orders;
	}
	
	
	public synchronized Collection<OrderBean> doRetrieveAllSU(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<OrderBean> orders = new LinkedList<>();

		String selectSQL = "SELECT * FROM " + TABLE_NAME;
		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			//il nome della tabella è hard coded
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				OrderBean bean = new OrderBean();

				bean.setId(rs.getInt("id"));
				bean.setEmail(rs.getString("email"));
				bean.setStato(rs.getInt("stato"));
				bean.setData(rs.getDate("data"));
				bean.setImporto(rs.getDouble("importo"));
				bean.setCarta(rs.getString("carta"));
				bean.setDataSpedizione(rs.getDate("dataSpedizione"));
				bean.setAddress(rs.getString("indirizzo"));
				orders.add(bean);
			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return orders;
	}
	
	
	public synchronized Collection<OrderBean> doRetrieveAllSUData(String dataInizio,String dataFine) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<OrderBean> orders = new LinkedList<>();

		String selectSQL = "SELECT * FROM " + TABLE_NAME +" WHERE data BETWEEN ? AND ?";
		

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			 preparedStatement.setString(1, dataInizio);
	            preparedStatement.setString(2, dataFine);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				OrderBean bean = new OrderBean();

				bean.setId(rs.getInt("id"));
				bean.setEmail(rs.getString("email"));
				bean.setStato(rs.getInt("stato"));
				bean.setData(rs.getDate("data"));
				bean.setImporto(rs.getDouble("importo"));
				bean.setCarta(rs.getString("carta"));
				bean.setDataSpedizione(rs.getDate("dataSpedizione"));
				bean.setAddress(rs.getString("indirizzo"));
				orders.add(bean);
			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return orders;
	}
	
	public synchronized Collection<OrderBean> doRetrieveAllData(String dataInizio,String dataFine, String email) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<OrderBean> orders = new LinkedList<>();

		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE email = ? AND data BETWEEN ? AND ?";
	

		try {
			connection = ds.getConnection();
			//sql gestito con prepared statement come indicato da sonarcloudDocs
			preparedStatement = connection.prepareStatement(selectSQL);
			
			preparedStatement.setString(1, email);
            preparedStatement.setString(2, dataInizio);
            preparedStatement.setString(3, dataFine);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				OrderBean bean = new OrderBean();

				bean.setId(rs.getInt("id"));
				bean.setEmail(rs.getString("email"));
				bean.setStato(rs.getInt("stato"));
				bean.setData(rs.getDate("data"));
				bean.setImporto(rs.getDouble("importo"));
				bean.setCarta(rs.getString("carta"));
				bean.setDataSpedizione(rs.getDate("dataSpedizione"));
				bean.setAddress(rs.getString("indirizzo"));
				orders.add(bean);
			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return orders;
	}

}

