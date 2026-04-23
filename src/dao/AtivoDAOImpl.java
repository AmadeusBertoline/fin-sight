package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Ativo;

public class AtivoDAOImpl implements AtivoDAO {
	
	ConnectionFactory conexao = new ConnectionFactory();
	Connection con = conexao.getConnection();
	

	@Override
	public void salvar(Ativo a) {

		try {
			String sql = "INSERT INTO ativos (ticker, nome_empresa, tipo, valor_unitario) " + "VALUES(?,?,?,?)";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, a.getTicker());
			pst.setString(2, a.getNome());
			pst.setString(3, a.getTipo());
			pst.setBigDecimal(4, a.getValorCompra());

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

			while (res.next()) {

				Ativo a = new Ativo();

				a.setId(res.getInt("id"));
				a.setNome(res.getString("nome_empresa"));
				a.setTicker(res.getString("ticker"));
				a.setTipo(res.getString("tipo"));
				a.setValorCompra(res.getBigDecimal("valor_unitario"));

				ativos.add(a);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ativos;
	}

	@Override
	public void atualizar(Ativo a, long id) {

		try {
			String sql = "UPDATE ativos SET nome=?, ticker=?, tipo=?, valor_unitario=? " + "WHERE id=?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, a.getNome());
			pst.setString(2, a.getTicker());
			pst.setString(3, a.getTipo());
			pst.setBigDecimal(4, a.getValorCompra());
			pst.setLong(5, a.getId());

			pst.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void excluir(long id) {

		try {
			String sql = "DELETE FROM ativos WHERE id=?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setLong(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Ativo> pesquisarPorNome(String nome) {
		List<Ativo> lista = new ArrayList<>();
		try {

			String sql = "SELECT * FROM ativos WHERE nome LIKE ?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, "%" + nome + "%");
			ResultSet res = pst.executeQuery();

			while (res.next()) {

				Ativo a = new Ativo();

				a.setId(res.getInt("id"));
				a.setNome(res.getString("nome_empresa"));
				a.setTicker(res.getString("ticker"));
				a.setTipo(res.getString("tipo"));
				a.setValorCompra(res.getBigDecimal("valor_unitario"));

				lista.add(a);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;

	}

	@Override
	public BigDecimal totalGeral() {

		return BigDecimal.ZERO;

	}

	@Override
	public long quantidadeAtivos() {

		long quantidade = 0;

		try {

			String sql = "SELECT COUNT(id) as quantidade FROM ativos;";
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet res = pst.executeQuery();

			while (res.next()) {
				quantidade = res.getLong("quantidade");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return quantidade;
	}
}