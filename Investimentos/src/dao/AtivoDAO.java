package dao;

import java.util.List;

import model.Ativo;

public interface AtivoDAO {
	
	void salvar(Ativo a);
	List<Ativo> listar();
	void atualizar(Ativo a, long id);
	void excluir(long id);

}
