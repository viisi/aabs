package br.com.aabs.servico;

import java.util.List;

import javax.ejb.Stateless;

import br.com.aabs.entidade.Cliente;

@Stateless
public interface ClienteServico {
	
	Cliente carregar(Integer id) throws Exception;
	
	Cliente cadastrar(Cliente cliente) throws Exception;
	
	Cliente alterar(Cliente cliente) throws Exception;;
		
	void excluir(Cliente cliente) throws Exception;
	
	List<Cliente> consultar(Cliente filtro) throws Exception;
	
	Cliente consultarPorCpf(Cliente filtro) throws Exception;
	
}