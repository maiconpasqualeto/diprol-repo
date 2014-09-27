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

import br.com.sixinf.ferramentas.persistencia.Entidade;

/**
 * @author maicon
 *
 */
@Entity
@Table(name = "campanha", schema = "public")
public class Campanha implements Serializable, Entidade {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "cod_campanha")
	private Integer codCampanha;
	
	@Column(name = "campanha")
	private String campanha;
	
	@Column(name = "data_inicio")
	private Date dataInicio;
	
	@Column(name = "data_termino")
	private Date dataTermino;
	
	@Column(name = "data_cadastro")
	private Date dataCadastro;
	
	@Column(name = "status")
	private Character status;
	
	public Integer getCodCampanha() {
		return codCampanha;
	}

	public void setCodCampanha(Integer codCampanha) {
		this.codCampanha = codCampanha;
	}

	public String getCampanha() {
		return campanha;
	}

	public void setCampanha(String campanha) {
		this.campanha = campanha;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	@Override
	public Long getIdentificacao() {
		return codCampanha.longValue();
	}

}
