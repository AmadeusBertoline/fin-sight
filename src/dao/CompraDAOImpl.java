package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Ativo;
import model.Compra;

public class CompraDAOImpl implements CompraDAO {

	ConnectionFactory conexao = new ConnectionFactory();
	Connection con = conexao.getConnection();

	@Override
	public void salvar(Compra c) {

		try {

			String sql = "INSERT INTO compras(idAtivo, dataCompra, valor_compra, quantidade) VALUES(?,?,?,?)";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, c.getAtivo().getId());
			pst.setDate(2, java.sql.Date.valueOf(c.getDataCompra()));
			pst.setBigDecimal(3, c.getValorCompra());
			pst.setBigDecimal(4, c.getQuantidade());
			pst.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Compra> listar() {

		List<Compra> lista = new ArrayList<>();

		try {

			String sql = "SELECT c.*, a.nome_empresa, a.id, a.ticker, a.tipo, a.valor_unitario FROM Compras c "
					+ "INNER JOIN ativos a ON c.idAtivo = a.id ORDER BY c.idCompra DESC";
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet res = pst.executeQuery();

			while (res.next()) {

				Compra c = new Compra();
				c.setId(res.getInt("idCompra"));
				c.setQuantidade(res.getBigDecimal("quantidade"));
				c.setDataCompra(res.getDate("dataCompra").toLocalDate());
				c.setValorCompra(res.getBigDecimal("valor_compra"));
				
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
			e.printStackTrace();
		}

		return lista;
	}

	@Override
	public void atualizar(Compra c, int id) {

		try {

			String sql = "UPDATE compras SET idAtivo = ?, dataCompra = ?, valor_compra = ?, quantidade = ? "
					+ "WHERE idCompra = ?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, c.getAtivo().getId());
			pst.setDate(2, java.sql.Date.valueOf(c.getDataCompra()));
			pst.setBigDecimal(3, c.getValorCompra());
			pst.setBigDecimal(4, c.getQuantidade());
			pst.setInt(5, id);
			pst.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void excluir(int id) {
		
		try {

			String sql = "DELETE FROM compras WHERE idCompra = ?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			pst.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	
	@Override
	public List<Compra> busca(String nome){
		
		List<Compra> listaNomes = new ArrayList<>();
		
		try {
			
			String sql = "SELECT c.*, a.id, a.nome_empresa, a.ticker, a.tipo, a.valor_unitario FROM compras c "
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
			e.printStackTrace();
		}
		
		return listaNomes;
		
	}

}
