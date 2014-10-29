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
@Table(name = "tab_movimento", schema = "public")
public class Movimento implements Serializable, Entidade {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "cod_movimento")
	private Integer codMovimento;
	
	@Column(name = "movimento")
	private String movimento;
	
	@Column(name = "acao_saldo")
	private String acaoSaldo;
	
	@Column(name = "gera_contrapartida")
	private Character geraContrapartida;
	
	@Column(name = "cod_movto_contrapartida")
	private Integer codMovimentoContrapartida;
	
	@Column(name = "tipo_cliente")
	private String tipoCliente;
	
	@Column(name = "data_cadastro")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;
	
	@Column(name = "status")
	private Character status;
	
	@Column(name = "permuta")
	private Character permuta;
	
	@Column(name = "cod_movto_permuta")
	private Integer codMovimentoPermuta;
	
	public Integer getCodMovimento() {
		return codMovimento;
	}

	public void setCodMovimento(Integer codMovimento) {
		this.codMovimento = codMovimento;
	}

	public String getMovimento() {
		return movimento;
	}

	public void setMovimento(String movimento) {
		this.movimento = movimento;
	}

	public String getAcaoSaldo() {
		return acaoSaldo;
	}

	public void setAcaoSaldo(String acaoSaldo) {
		this.acaoSaldo = acaoSaldo;
	}

	public Character getGeraContrapartida() {
		return geraContrapartida;
	}

	public void setGeraContrapartida(Character geraContrapartida) {
		this.geraContrapartida = geraContrapartida;
	}

	public Integer getCodMovimentoContrapartida() {
		return codMovimentoContrapartida;
	}

	public void setCodMovimentoContrapartida(Integer codMovimentoContrapartida) {
		this.codMovimentoContrapartida = codMovimentoContrapartida;
	}

	public String getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
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

	public Character getPermuta() {
		return permuta;
	}

	public void setPermuta(Character permuta) {
		this.permuta = permuta;
	}

	public Integer getCodMovimentoPermuta() {
		return codMovimentoPermuta;
	}

	public void setCodMovimentoPermuta(Integer codMovimentoPermuta) {
		this.codMovimentoPermuta = codMovimentoPermuta;
	}

	@Override
	public Long getIdentificacao() {
		return codMovimento.longValue();
	}
	
	

}
