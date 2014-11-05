/**
 * 
 */
package br.com.sixinf.diprol.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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

import br.com.sixinf.diprol.DiprolHelper;
import br.com.sixinf.ferramentas.persistencia.Entidade;

/**
 * @author maicon
 *
 */
@Entity
@Table(name="conta_movimento")
public class ContaMovimento  implements Serializable, Entidade {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="seqContaMovimento", sequenceName="conta_movimento_cod_conta_movimento_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqContaMovimento")
	@Column(name="cod_conta_movimento")
	private Long codContaMovimento;
	
	@Column(name="ano")
	private Integer ano;
	
	@Column(name="mes")
	private Integer mes;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cod_conta")
	private Conta conta;
	
	@Column(name="previsto")
	private BigDecimal previsto;
	
	@Column(name="realizado")
	private BigDecimal realizado;
	
	@Column(name="usuario_cpf")
	private String usuarioCpf; 
	
	@Column(name="data_lancamento")
	@Temporal(TemporalType.DATE)
	private Date dataLancamento;
	
	public Long getCodContaMovimento() {
		return codContaMovimento;
	}

	public void setCodContaMovimento(Long codContaMovimento) {
		this.codContaMovimento = codContaMovimento;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public BigDecimal getPrevisto() {
		return previsto;
	}

	public void setPrevisto(BigDecimal previsto) {
		this.previsto = previsto;
	}

	public BigDecimal getRealizado() {
		return realizado;
	}

	public void setRealizado(BigDecimal realizado) {
		this.realizado = realizado;
	}

	public String getUsuarioCpf() {
		return usuarioCpf;
	}

	public void setUsuarioCpf(String usuarioCpf) {
		this.usuarioCpf = usuarioCpf;
	}

	public Date getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(Date dataLancamento) {
		this.dataLancamento = dataLancamento;
	}
	
	public String getPrevistoFormatado(){
		return DiprolHelper.getMoneyFormatterInstance().format(previsto);
	}

	@Override
	public Long getIdentificacao() {
		return codContaMovimento;
	}

}
