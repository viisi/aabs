package br.com.aabs.servico;

import java.util.List;

import javax.ejb.Stateless;

import br.com.aabs.entidade.Contrato;
import br.com.aabs.entidade.Parcela;

@Stateless
public interface ContratoServico {
	
	Contrato carregar(Integer id) throws Exception;
	
	Contrato cadastrar(Contrato contrato, Parcela primeiraParcela) throws Exception;
	
	Contrato alterar(Contrato contrato) throws Exception;;
		
	void excluir(Contrato contrato) throws Exception;
	
	List<Contrato> ultimosContratos() throws Exception;;
}