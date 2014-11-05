/**
 * 
 */
package br.com.sixinf.diprol.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.sixinf.ferramentas.persistencia.Entidade;

/**
 * @author maicon
 *
 */
@Entity
@Table(name="conta_grupo")
public class ContaGrupo implements Serializable, Entidade {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="seqGrupoConta", sequenceName="conta_grupo_cod_grupo_conta_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqGrupoConta")
	@Column(name="cod_grupo_conta")
	private Long codGrupoConta;
	
	@Column(name="grupo_conta")
	private String grupoConta;
	
	public Long getCodGrupoConta() {
		return codGrupoConta;
	}

	public void setCodGrupoConta(Long codGrupoConta) {
		this.codGrupoConta = codGrupoConta;
	}

	public String getGrupoConta() {
		return grupoConta;
	}

	public void setGrupoConta(String grupoConta) {
		this.grupoConta = grupoConta;
	}

	@Override
	public Long getIdentificacao() {
		return codGrupoConta;
	}

}
