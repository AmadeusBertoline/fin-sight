package dao;

import java.util.List;

import model.Compra;

public interface CompraDAO {

	void salvar(Compra c);
	List<Compra> listar();
	void atualizar(Compra c, int id);
	void excluir(int id);
	List<Compra> busca(String nome);
}
