package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class UserModel {

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


	static final String TABLE_NAME = "utente";

	public synchronized void doSave(UserBean usr) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + TABLE_NAME
				+ " (nome, cognome, email, password, tipo, indirizzo, telefono,metodoPagamento) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
		
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, usr.getNome());
			preparedStatement.setString(2, usr.getCognome());
			preparedStatement.setString(3, usr.getEmail());
			preparedStatement.setString(4, usr.getPwd());
			preparedStatement.setInt(5, usr.getTipo());
			preparedStatement.setString(6, usr.getIndirizzo());
			preparedStatement.setString(7, usr.getNumero());
			preparedStatement.setString(8, usr.getMetodo());
			
			
			preparedStatement.executeUpdate();
			
			connection.commit();
			
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		
		
		
		
	}
	
	public synchronized boolean checkEmail(String email) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;


		String selectSQL = "SELECT count(*) FROM " + TABLE_NAME + " WHERE email = ?";
		boolean check;
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, email);
			
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			if(rs.getInt("count(*)") == 0)
				check = false;
			else  
				check = true;

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return check;
	}

	public synchronized UserBean doRetrieveByKey(String email) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		UserBean bean = new UserBean();

		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE email = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, email);

			ResultSet rs = preparedStatement.executeQuery();
			//serve a capire se il resultset ha prodotto risultati oppure no
			if(rs.next() == false)
				return null;
			else {
				do {
					bean.setNome(rs.getString("nome"));
					bean.setCognome(rs.getString("Cognome"));
					bean.setEmail(rs.getString("email"));
					bean.setPwd(rs.getString("password"));
					bean.setTipo(rs.getInt("tipo"));
					bean.setIndirizzo(rs.getString("indirizzo"));
					bean.setNumero(rs.getString("telefono"));
				}while(rs.next());
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
	

	public synchronized boolean doDelete(String email) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, email);

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

	public synchronized Collection<UserBean> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<UserBean> products = new LinkedList<UserBean>();

		String selectSQL = "SELECT * FROM " + TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			//nome tabella hardcoded
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				UserBean bean = new UserBean();

				bean.setNome(rs.getString("nome"));
				bean.setCognome(rs.getString("Cognome"));
				bean.setEmail(rs.getString("email"));
				bean.setPwd(rs.getString("password"));
				bean.setTipo(rs.getInt("tipo"));
				bean.setIndirizzo(rs.getString("indirizzo"));
				bean.setNumero(rs.getString("telefono"));
				products.add(bean);
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
		return products;
	}

}