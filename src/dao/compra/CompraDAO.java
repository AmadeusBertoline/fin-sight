package dao.compra;

import java.sql.SQLException;
import java.util.List;

import model.Compra;

public interface CompraDAO {

	void salvar(Compra c) throws SQLException;
	List<Compra> listar() throws SQLException;
	void atualizar(Compra c, int id) throws SQLException;
	void excluir(int id) throws SQLException;
	List<Compra> busca(String nome) throws SQLException;
}
