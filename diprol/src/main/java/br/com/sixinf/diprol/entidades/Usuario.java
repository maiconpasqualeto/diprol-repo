/**
 * 
 */
package br.com.sixinf.diprol.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.sixinf.ferramentas.persistencia.Entidade;

/**
 * @author maicon
 *
 */
@Entity
@Table(name = "usuario", schema = "public")
public class Usuario implements Entidade, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "cpf")
	private String cpf;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "senha")
	private String senha;
	
	@Column(name = "status")
	private Character status;
		
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	@Override
	public Long getIdentificacao() {
		
		return null;
	}

}
