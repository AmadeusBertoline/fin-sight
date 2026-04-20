package dao;

import java.math.BigDecimal;
import java.util.List;

import model.Ativo;

public interface AtivoDAO {
	
	void salvar(Ativo a);
	List<Ativo> listar();
	void atualizar(Ativo a, long id);
	void excluir(long id);
	List<Ativo> pesquisarPorNome(String nome);
	BigDecimal totalGeral();
}
