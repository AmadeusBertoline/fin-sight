package dao.ativo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.connection.ConnectionFactory;
import model.Ativo;

public class AtivoDAOImpl implements AtivoDAO {

	ConnectionFactory conexao = new ConnectionFactory();
	Connection con = conexao.getConnection();

	@Override
	public void salvar(Ativo a) throws SQLException{

		try {
			String sql = "INSERT INTO ativos (ticker, nome_empresa, tipo, valor_unitario) " + "VALUES(?,?,?,?)";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, a.getTicker());
			pst.setString(2, a.getNome());
			pst.setString(3, a.getTipo());
			pst.setBigDecimal(4, a.getValorCompra());

			pst.executeUpdate();

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			throw e;
		}

	}

	@Override
	public List<Ativo> listar() throws SQLException{

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
			System.err.print(e.getMessage());
			throw e;
		}

		return ativos;
	}

	@Override
	public void atualizar(Ativo a, int id) throws SQLException{

		try {
			String sql = "UPDATE ativos SET nome_empresa=?, ticker=?, tipo=?, valor_unitario=? " + "WHERE id=?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, a.getNome());
			pst.setString(2, a.getTicker());
			pst.setString(3, a.getTipo());
			pst.setBigDecimal(4, a.getValorCompra());
			pst.setInt(5, a.getId());

			pst.executeUpdate();

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			throw e;
		}
		

	}

	@Override
	public void excluir(int id) throws SQLException{

		try {
			String sql = "DELETE FROM ativos WHERE id=?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			throw e;
		}

	}

	@Override
	public List<Ativo> pesquisarPorNome(String nome) throws SQLException{
		List<Ativo> lista = new ArrayList<>();
		try {

			String sql = "SELECT * FROM ativos WHERE nome_empresa LIKE ?";
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
			System.err.println(e.getMessage());
			throw e;
		}

		return lista;

	}

	@Override
	public boolean ativoEmUso(int id) throws SQLException{

		try {

			String sql = "SELECT COUNT(*) FROM compras WHERE idAtivo = ?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet res = pst.executeQuery();

			if (res.next()) {
				int count = res.getInt(1);
				return count > 0;
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw e;
		}

		return false;
	}

}