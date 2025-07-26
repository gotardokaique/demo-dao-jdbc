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
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                "INSERT INTO Vendedor " +
                "(ven_name, ven_email, ven_datanascimento, ven_salariobase, dep_id) " +
                "VALUES (?, ?, ?, ?, ?)",
                java.sql.Statement.RETURN_GENERATED_KEYS
            );

            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getDatanascimento().getTime()));
            st.setDouble(4, obj.getSalarioBase());
            if (obj.getDepartamento() == null) {
                st.setNull(5, java.sql.Types.INTEGER);
            } else {
                st.setInt(5, obj.getDepartamento().getId());
            }

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        obj.setId(rs.getInt(1));
                    }
                }
            } else {
                throw new DbException("Erro inesperado: nenhuma linha afetada ao inserir vendedor");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Vendedor obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                "UPDATE Vendedor SET " +
                "ven_name = ?, ven_email = ?, ven_datanascimento = ?, ven_salariobase = ?, dep_id = ? " +
                "WHERE ven_id = ?"
            );

            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getDatanascimento().getTime()));
            st.setDouble(4, obj.getSalarioBase());
            if (obj.getDepartamento() == null) {
                st.setNull(5, java.sql.Types.INTEGER);
            } else {
                st.setInt(5, obj.getDepartamento().getId());
            }
            st.setInt(6, obj.getId());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM Vendedor WHERE ven_id = ?");
            st.setInt(1, id);
            st.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Vendedor findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                "SELECT ven.*, dep.dep_id, dep.dep_nome " +
                "FROM Vendedor ven " +
                "LEFT JOIN Departamento dep ON ven.dep_id = dep.dep_id " +
                "WHERE ven.ven_id = ?"
            );

            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Departamento dep = instanciarDepartamento(rs);
                Vendedor obj = instanciarVendedor(rs, dep);
                return obj;
            }
            return null;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Vendedor> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                "SELECT ven.*, dep.dep_id, dep.dep_nome " +
                "FROM Vendedor ven " +
                "LEFT JOIN Departamento dep ON ven.dep_id = dep.dep_id " +
                "ORDER BY ven.ven_name"
            );

            rs = st.executeQuery();

            List<Vendedor> list = new java.util.ArrayList<>();
            java.util.Map<Integer, Departamento> map = new java.util.HashMap<>();

            while (rs.next()) {
                int depId = rs.getInt("dep_id");
                Departamento dep = map.get(depId);

                if (dep == null) {
                    dep = instanciarDepartamento(rs);
                    map.put(depId, dep);
                }

                Vendedor obj = instanciarVendedor(rs, dep);
                list.add(obj);
            }

            return list;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }
    
    @Override
    public List<Vendedor> findByDepartamento(Departamento departamento) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                "SELECT ven.*, dep.dep_id, dep.dep_nome " +
                "FROM Vendedor ven " +
                "LEFT JOIN Departamento dep ON ven.dep_id = dep.dep_id " +
                "WHERE ven.dep_id = ? " +
                "ORDER BY ven.ven_name"
            );

            st.setInt(1, departamento.getId());
            rs = st.executeQuery();

            List<Vendedor> list = new java.util.ArrayList<>();
            java.util.Map<Integer, Departamento> map = new java.util.HashMap<>();

            while (rs.next()) {
                int depId = rs.getInt("dep_id");
                Departamento dep = map.get(depId);

                if (dep == null) {
                    dep = instanciarDepartamento(rs);
                    map.put(depId, dep);
                }

                Vendedor obj = instanciarVendedor(rs, dep);
                list.add(obj);
            }

            return list;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    private Vendedor instanciarVendedor(ResultSet rs, Departamento dep) throws SQLException {
        Vendedor obj = new Vendedor();
        obj.setId(rs.getInt("ven_id"));
        obj.setName(rs.getString("ven_name"));
        obj.setEmail(rs.getString("ven_email"));
        obj.setDatanascimento(rs.getDate("ven_datanascimento"));
        obj.setSalarioBase(rs.getDouble("ven_salariobase"));
        obj.setDepartamento(dep);
        return obj;
    }

    private Departamento instanciarDepartamento(ResultSet rs) throws SQLException {
        Departamento dep = new Departamento();
        dep.setId(rs.getInt("dep_id"));
        dep.setName(rs.getString("dep_nome"));
        return dep;
    }
}
