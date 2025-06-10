package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.VendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

public class VendedorDaoJDBC implements VendedorDao {

	private Connection conn;

	public VendedorDaoJDBC(Connection conn) {
		this.conn = conn;

	}

	@Override
	public void insert(Vendedor obj) {

	}

	@Override
	public void update(Vendedor obj) {

	}

	@Override
	public void deleteById(Integer id) {

	}

	@Override
	public Vendedor findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(" 		SELECT	         		" + " 		  *	         		"
					+ " 		FROM Vendedor ven	         		"
					+ " 		LEFT JOIN Departamento dep ON ven.dep_id = dep.dep_id	         		"
					+ " 		where ven.ven_id = ?	         		");

			st.setInt(1, id);
			rs = st.executeQuery();
			
			if (rs.next()) {
				Departamento dep = new Departamento();
				dep.setId(rs.getInt("dep_id"));
				dep.setName(rs.getString("dep_nome"));
				
			    Vendedor obj = new Vendedor();
	            obj.setId(rs.getInt("ven_id"));
	            obj.setName(rs.getString("ven_name"));
	            obj.setEmail(rs.getString("ven_email"));
	            obj.setDatanascimento(rs.getDate("ven_datanascimento"));
	            obj.setSalarioBase(rs.getDouble("ven_salariobase"));
	            obj.setDepartamento(dep);

	            return obj;
				 
				
			}
			return null;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
			
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);

		}

	}

	@Override
	public List<Vendedor> findAll() {
		return null;
	}

}
