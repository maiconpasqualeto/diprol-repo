/**
 * 
 */
package br.com.sixinf.diprol.entidades;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.sixinf.ferramentas.persistencia.Entidade;

/**
 * @author maicon
 *
 */
@Entity
@Table(name = "estoque", schema = "public")
public class Estoque implements Serializable, Entidade {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="seqEstoque", sequenceName="estoque_cod_estoque_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqEstoque")
	@Column(name="cod_estoque")
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cod_campanha")
	private Campanha campanha;
	
	@Column(name="uf")
	private String uf;
	
	@Column(name="cod_cef")
	private String codCEF;
	
	@Column(name="data_movimento")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataMovimento;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cod_movimento")
	private Movimento movimento;
	
	@Column(name="qtde")
	private Integer quantidade;
	
	@Column(name="saldo_anterior")
	private Integer saldoAnterior;
	
	@Column(name="saldo_atual")
	private Integer saldoAtual;
	
	@Column(name="cod_cef_contrapartida")
	private String codCEFContrapartida;
	
	@Column(name="usuario_cpf")
	private String usuarioCPF;
	
	@Column(name="observacao")
	private String observacao;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Campanha getCampanha() {
		return campanha;
	}

	public void setCampanha(Campanha campanha) {
		this.campanha = campanha;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCodCEF() {
		return codCEF;
	}

	public void setCodCEF(String codCEF) {
		this.codCEF = codCEF;
	}

	public Date getDataMovimento() {
		return dataMovimento;
	}

	public void setDataMovimento(Date dataMovimento) {
		this.dataMovimento = dataMovimento;
	}

	public Movimento getMovimento() {
		return movimento;
	}

	public void setMovimento(Movimento movimento) {
		this.movimento = movimento;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Integer getSaldoAnterior() {
		return saldoAnterior;
	}

	public void setSaldoAnterior(Integer saldoAnterior) {
		this.saldoAnterior = saldoAnterior;
	}

	public Integer getSaldoAtual() {
		return saldoAtual;
	}

	public void setSaldoAtual(Integer saldoAtual) {
		this.saldoAtual = saldoAtual;
	}

	public String getCodCEFContrapartida() {
		return codCEFContrapartida;
	}

	public void setCodCEFContrapartida(String codCEFContrapartida) {
		this.codCEFContrapartida = codCEFContrapartida;
	}

	public String getUsuarioCPF() {
		return usuarioCPF;
	}

	public void setUsuarioCPF(String usuarioCPF) {
		this.usuarioCPF = usuarioCPF;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@Override
	public Long getIdentificacao() {
		return id;
	}

	
	
}
