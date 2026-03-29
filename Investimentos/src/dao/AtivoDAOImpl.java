package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Ativo;

public class AtivoDAOImpl implements AtivoDAO {

	public Connection con;

	public AtivoDAOImpl() {

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mariadb://localhost:3307/db_investimentos", "root", "Crocodilo@");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void salvar(Ativo a) {

		try {
			String sql = "INSERT INTO ativos (ticker, nome_empresa, tipo, quantidade, valor_unitario, data_compra) "
					+ "VALUES(?,?,?,?,?,?)";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, a.getTicker());
			pst.setString(2, a.getNome());
			pst.setString(3, a.getTipo());
			pst.setDouble(4, a.getQuantidade());
			pst.setBigDecimal(5, a.getValorCompra());
			pst.setDate(6, java.sql.Date.valueOf(a.getDataCompra()));
			
			pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Ativo> listar() {
		
		List<Ativo> ativos = new ArrayList<Ativo>();
		
		try {
			String sql = "SELECT * FROM ativos";
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet res = pst.executeQuery();
			
			while(res.next()) {
				
				Ativo a = new Ativo();
				
				a.setId(res.getLong("id"));
				a.setNome(res.getString("nome_empresa"));
				a.setTicker(res.getString("ticker"));
				a.setTipo(res.getString("tipo"));
				a.setQuantidade(res.getDouble("quantidade"));
				a.setValorCompra(res.getBigDecimal("valor_unitario"));
				a.setDataCompra(res.getDate("data_compra").toLocalDate());
				
				ativos.add(a);				
			}
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return ativos;
	}

	@Override
	public void atualizar(Ativo a, long id) {
		
		try{
			String sql = "UPDATE ativos SET nome=?, ticker=?, tipo=?, quantidade=?, valor_unitario=?, data_compra=? "
					+ "WHERE id=?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, a.getNome());
			pst.setString(2, a.getTicker());
			pst.setString(3, a.getTipo());
			pst.setDouble(4, a.getQuantidade());
			pst.setBigDecimal(5, a.getValorCompra());
			pst.setDate(6, java.sql.Date.valueOf(a.getDataCompra()));
			pst.setLong(7, a.getId());
			
			pst.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void excluir(long id) {
		
		try {
			String sql = "DELETE FROM ativos WHERE id=?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	

	}

}
