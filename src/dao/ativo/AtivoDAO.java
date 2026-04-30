package dao.ativo;

import java.sql.SQLException;
import java.util.List;

import model.Ativo;

public interface AtivoDAO {
	
	void salvar(Ativo a) throws SQLException;
	List<Ativo> listar() throws SQLException;
	void atualizar(Ativo a, int id) throws SQLException;
	void excluir(int id) throws SQLException;
	List<Ativo> pesquisarPorNome(String nome) throws SQLException ;
	boolean ativoEmUso(int id) throws SQLException;
}
