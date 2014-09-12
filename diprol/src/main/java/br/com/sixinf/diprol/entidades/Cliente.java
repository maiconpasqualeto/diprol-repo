/**
 * 
 */
package br.com.sixinf.diprol.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.sixinf.ferramentas.persistencia.Entidade;

/**
 * @author maicon
 *
 */
@Entity
@Table(name = "cliente", schema = "public")
public class Cliente implements Serializable, Entidade {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "cod_cef")
	private String codCEF;
	
	@Column(name = "razao_social")
	private String razaoSocial;
	
	@Column(name = "cnpj")
	private String cnpj;
	
	@Column(name = "cep")
	private String cep;
	
	@Column(name = "endereco")
	private String endereco;
	
	@Column(name = "endereco_numero")
	private String enderecoNumero;
	
	@Column(name = "complemento")
	private String complemento;
	
	@Column(name = "bairro")
	private String bairro;
	
	@Column(name = "cidade")
	private String cidade;
	
	@Column(name = "uf")
	private String uf;
	
	@Column(name = "contato")
	private String contato;
	
	@Column(name = "telefone_ddd1")
	private String telefone1DDD;
	
	@Column(name = "telefone1")
	private String telefone1;
	
	@Column(name = "telefone_ddd2")
	private String telefone2DDD;
	
	@Column(name = "telefone2")
	private String telefone2;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "venda_fixa")
	private Integer vendaFixa;
	
	@Column(name = "canal_atendimento")
	private String canalAtendimento;
	
	@Column(name = "status")
	private Character status;
	
	@Column(name = "data_cadastro")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;

	public String getCodCEF() {
		return codCEF;
	}

	public void setCodCEF(String codCEF) {
		this.codCEF = codCEF;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getEnderecoNumero() {
		return enderecoNumero;
	}

	public void setEnderecoNumero(String enderecoNumero) {
		this.enderecoNumero = enderecoNumero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public String getTelefone1DDD() {
		return telefone1DDD;
	}

	public void setTelefone1DDD(String telefone1ddd) {
		telefone1DDD = telefone1ddd;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2DDD() {
		return telefone2DDD;
	}

	public void setTelefone2DDD(String telefone2ddd) {
		telefone2DDD = telefone2ddd;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getVendaFixa() {
		return vendaFixa;
	}

	public void setVendaFixa(Integer vendaFixa) {
		this.vendaFixa = vendaFixa;
	}

	public String getCanalAtendimento() {
		return canalAtendimento;
	}

	public void setCanalAtendimento(String canalAtendimento) {
		this.canalAtendimento = canalAtendimento;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	/* (non-Javadoc)
	 * @see br.com.sixinf.ferramentas.persistencia.Entidade#getIdentificacao()
	 */
	@Override
	public Long getIdentificacao() {
		// TODO Auto-generated method stub
		return null;
	}

}
