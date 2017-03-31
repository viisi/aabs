package br.com.aabs.entidade;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cliente", schema = "aabs")
public class Cliente extends Entidades {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "cpf")
	private String cpf;
	
	@Column(name = "telefone")
	private String telefone;
	
	@Column(name = "celular")
	private String celular;
	
	@Column(name = "endereco")
	private String endereco;
	
	@Column(name = "agencia")
	private String agencia;
	
	@Column(name = "conta")
	private String conta;
	
	@Column(name = "banco")
	private String banco;
	
	@Column(name = "referencia1")
	private String referencia1;
	
	@Column(name = "telefone_referencia1")
	private String telefone_referencia1;
	
	@Column(name = "referencia2")
	private String referencia2;
	
	@Column(name = "telefone_referencia2")
	private String telefone_referencia2;
	
	@Column(name = "empresa")
	private String empresa;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
	private List<Contrato> contratos;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public List<Contrato> getContratos() {
		return contratos;
	}

	public void setContratos(List<Contrato> contratos) {
		this.contratos = contratos;
	}

	public String getReferencia1() {
		return referencia1;
	}

	public void setReferencia1(String referencia1) {
		this.referencia1 = referencia1;
	}

	public String getTelefone_referencia1() {
		return telefone_referencia1;
	}

	public void setTelefone_referencia1(String telefone_referencia1) {
		this.telefone_referencia1 = telefone_referencia1;
	}

	public String getReferencia2() {
		return referencia2;
	}

	public void setReferencia2(String referencia2) {
		this.referencia2 = referencia2;
	}

	public String getTelefone_referencia2() {
		return telefone_referencia2;
	}

	public void setTelefone_referencia2(String telefone_referencia2) {
		this.telefone_referencia2 = telefone_referencia2;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
}