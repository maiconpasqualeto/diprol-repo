/**
 * 
 */
package br.com.sixinf.diprol.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.sixinf.ferramentas.persistencia.Entidade;

/**
 * @author maicon
 *
 */
@Entity
@Table(name="conta")
public class Conta implements Entidade, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="seqConta", sequenceName="estoque_cod_conta_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqConta")
	@Column(name="cod_conta")
	private Long codConta;
	
	@Column(name="conta")
	private String conta;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cod_grupo_conta")
	private ContaGrupo contaGrupo;
	
	@Column(name="tipo")
	private Character tipo;
	
	public Long getCodConta() {
		return codConta;
	}

	public void setCodConta(Long codConta) {
		this.codConta = codConta;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public ContaGrupo getContaGrupo() {
		return contaGrupo;
	}

	public void setContaGrupo(ContaGrupo contaGrupo) {
		this.contaGrupo = contaGrupo;
	}
	
	public Character getTipo() {
		return tipo;
	}

	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}

	@Override
	public Long getIdentificacao() {
		return codConta;
	}

}
