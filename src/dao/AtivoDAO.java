package dao;

import java.math.BigDecimal;
import java.util.List;

import model.Ativo;

public interface AtivoDAO {
	
	void salvar(Ativo a);
	List<Ativo> listar();
	void atualizar(Ativo a, int id);
	void excluir(int id);
	List<Ativo> pesquisarPorNome(String nome);
	boolean ativoEmUso(int id);
}
