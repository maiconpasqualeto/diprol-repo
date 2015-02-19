/**
 * 
 */
package br.com.sixinf.diprol.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.sixinf.diprol.DiprolHelper;
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
	@SequenceGenerator(name="seqCampanha", sequenceName="campanha_cod_campanha_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqCampanha")
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
	
	@Column(name = "qtde_recebida")
	private BigDecimal qtdeRecebida;
	
	@Column(name = "qtde_reforco")
	private BigDecimal qtdeReforco;
	
	@Column(name = "qtde_devolvida")
	private BigDecimal qtdeDevolvida;
	
	@Column(name = "sicap")
	private BigDecimal sicap;
	
	@Column(name = "venda_alternativa")
	private BigDecimal vendaAlternativa;
	
	@Column(name = "devolucao_autorizada")
	private BigDecimal devolucaoAutorizada;
	
	@Column(name = "valor_unitario")
	private BigDecimal valorUnitario;
	
	@Column(name = "valor_campanha")
	private BigDecimal valorCampanha;
	
	@Column(name = "comissao_percentual")
	private BigDecimal comissaoPercentual;
	
	@Column(name = "comissao_campanha")
	private BigDecimal comissaoCampanha;
	
	@Column(name = "saldo_campanha")
	private BigDecimal saldoCampanha;
	
	@Column(name = "situacao_campanha")
	private String situacaoCampanha;
	
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

	public BigDecimal getQtdeRecebida() {
		return qtdeRecebida;
	}

	public void setQtdeRecebida(BigDecimal qtdeRecebida) {
		this.qtdeRecebida = qtdeRecebida;
	}

	public BigDecimal getQtdeReforco() {
		return qtdeReforco;
	}

	public void setQtdeReforco(BigDecimal qtdeReforco) {
		this.qtdeReforco = qtdeReforco;
	}

	public BigDecimal getQtdeDevolvida() {
		return qtdeDevolvida;
	}

	public void setQtdeDevolvida(BigDecimal qtdeDevolvida) {
		this.qtdeDevolvida = qtdeDevolvida;
	}

	public BigDecimal getSicap() {
		return sicap;
	}

	public void setSicap(BigDecimal sicap) {
		this.sicap = sicap;
	}

	public BigDecimal getVendaAlternativa() {
		return vendaAlternativa;
	}

	public void setVendaAlternativa(BigDecimal vendaAlternativa) {
		this.vendaAlternativa = vendaAlternativa;
	}

	public BigDecimal getDevolucaoAutorizada() {
		return devolucaoAutorizada;
	}

	public void setDevolucaoAutorizada(BigDecimal devolucaoAutorizada) {
		this.devolucaoAutorizada = devolucaoAutorizada;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public BigDecimal getValorCampanha() {
		return valorCampanha;
	}

	public void setValorCampanha(BigDecimal valorCampanha) {
		this.valorCampanha = valorCampanha;
	}

	public BigDecimal getComissaoPercentual() {
		return comissaoPercentual;
	}

	public void setComissaoPercentual(BigDecimal comissaoPercentual) {
		this.comissaoPercentual = comissaoPercentual;
	}

	public BigDecimal getComissaoCampanha() {
		return comissaoCampanha;
	}

	public void setComissaoCampanha(BigDecimal comissaoCampanha) {
		this.comissaoCampanha = comissaoCampanha;
	}

	public BigDecimal getSaldoCampanha() {
		return saldoCampanha;
	}

	public void setSaldoCampanha(BigDecimal saldoCampanha) {
		this.saldoCampanha = saldoCampanha;
	}

	public String getSituacaoCampanha() {
		return situacaoCampanha;
	}

	public void setSituacaoCampanha(String situacaoCampanha) {
		this.situacaoCampanha = situacaoCampanha;
	}
	
	public String getValorCampanhaFormatado() {
		if(valorCampanha != null)
			return DiprolHelper.getMoneyFormatterInstance().format(valorCampanha.floatValue());
		return "";
	}
	
	public String getSaldoCampanhaFormatado() {
		if(saldoCampanha != null)
			return DiprolHelper.getMoneyFormatterInstance().format(saldoCampanha.floatValue());
		return "";
	}
	
	public String getComissaoCampanhaFormatada() {
		if(comissaoCampanha != null)
			return DiprolHelper.getMoneyFormatterInstance().format(comissaoCampanha.floatValue());
		return "";
	}

	@Override
	public Long getIdentificacao() {
		return codCampanha.longValue();
	}

}
