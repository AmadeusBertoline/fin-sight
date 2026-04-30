package dao.compra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.connection.ConnectionFactory;
import model.Ativo;
import model.Compra;

public class CompraDAOImpl implements CompraDAO {

	ConnectionFactory conexao = new ConnectionFactory();
	Connection con = conexao.getConnection();

	@Override
	public void salvar(Compra c) throws SQLException{

		try {

			String sql = "INSERT INTO compras(idAtivo, dataCompra, valor_compra, quantidade, valor_unitario) VALUES(?,?,?,?,?)";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, c.getAtivo().getId());
			pst.setDate(2, java.sql.Date.valueOf(c.getDataCompra()));
			pst.setBigDecimal(3, c.getValorCompra());
			pst.setBigDecimal(4, c.getQuantidade());
			pst.setBigDecimal(5, c.getValorPago());
			pst.executeUpdate();

		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw e;
		}

	}

	@Override
	public List<Compra> listar() throws SQLException{

		List<Compra> lista = new ArrayList<>();

		try {

			String sql = "SELECT c.*, a.nome_empresa, a.id, a.ticker, a.tipo FROM Compras c "
					+ "INNER JOIN ativos a ON c.idAtivo = a.id ORDER BY c.idCompra DESC";
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet res = pst.executeQuery();

			while (res.next()) {

				Compra c = new Compra();
				c.setId(res.getInt("idCompra"));
				c.setQuantidade(res.getBigDecimal("quantidade"));
				c.setDataCompra(res.getDate("dataCompra").toLocalDate());
				c.setValorCompra(res.getBigDecimal("valor_compra"));
				c.setValorPago(res.getBigDecimal("valor_unitario"));
				
				Ativo a = new Ativo();
				a.setId(res.getInt("id"));
				a.setValorCompra(res.getBigDecimal("valor_unitario"));
				a.setNome(res.getString("nome_empresa"));
				a.setTicker(res.getString("ticker"));
				a.setTipo(res.getString("tipo"));
				
				c.setAtivo(a);
				
				lista.add(c);
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw e;
		}

		return lista;
	}

	@Override
	public void atualizar(Compra c, int id) throws SQLException{

		try {

			String sql = "UPDATE compras SET idAtivo = ?, dataCompra = ?, valor_compra = ?, quantidade = ?, valor_unitario = ? "
					+ "WHERE idCompra = ?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, c.getAtivo().getId());
			pst.setDate(2, java.sql.Date.valueOf(c.getDataCompra()));
			pst.setBigDecimal(3, c.getValorCompra());
			pst.setBigDecimal(4, c.getQuantidade());
			pst.setBigDecimal(5, c.getValorPago());
			pst.setInt(6, id);
			pst.executeUpdate();

		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw e;
		}

	}

	@Override
	public void excluir(int id) throws SQLException{
		
		try {

			String sql = "DELETE FROM compras WHERE idCompra = ?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			pst.executeUpdate();

		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw e;
		}

	}
	
	
	
	@Override
	public List<Compra> busca(String nome)throws SQLException{
		
		List<Compra> listaNomes = new ArrayList<>();
		
		try {
			
			String sql = "SELECT c.*, a.id, a.nome_empresa, a.ticker, a.tipo FROM compras c "
					+ " INNER JOIN ativos a ON c.idAtivo = a.id WHERE a.nome_empresa LIKE ?";
			
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, "%"+ nome + "%");
			ResultSet res = pst.executeQuery();
			
			while(res.next()) {
				
				Compra c = new Compra();
				c.setId(res.getInt("idCompra"));
				c.setQuantidade(res.getBigDecimal("quantidade"));
				c.setDataCompra(res.getDate("dataCompra").toLocalDate());
				c.setValorCompra(res.getBigDecimal("valor_compra"));
				c.setValorPago(res.getBigDecimal("valor_unitario"));
				
				Ativo a = new Ativo();
				a.setId(res.getInt("id"));
				a.setTicker(res.getString("ticker"));
				a.setTipo(res.getString("tipo"));
				a.setNome(res.getString("nome_empresa"));
				a.setValorCompra(res.getBigDecimal("valor_unitario"));
				
				c.setAtivo(a);
				
				listaNomes.add(c);
			}
			
		}catch(Exception e) {
			System.err.println(e.getMessage());
			throw e;
		}
		
		return listaNomes;
		
	}

}
