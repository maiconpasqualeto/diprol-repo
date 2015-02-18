/**
 * 
 */
package br.com.sixinf.diprol.entidades;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "estoque_resumo", schema = "public")
public class ResumoEstoque implements Entidade, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="seqEstoqueResumo", sequenceName="estoque_resumo_cod_estoque_resumo_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqEstoqueResumo")
	@Column(name = "cod_estoque_resumo")
	private Long codEstoqueResumo;
	
	@Column(name = "cod_campanha")
	private Integer codCampanha;
	
	@Column(name = "campanha")
	private String campanha;
	
	@Column(name = "uf")
	private String uf;
	
	@Column(name = "cod_cef")
	private String codCef;
	
	@Column(name = "cod_cef_contrapartida")
	private String codCefContrapartida;
	
	@Column(name = "razao_social")
	private String razaoSocial;
	
	@Column(name = "cidade")
	private String cidade;
	
	@Column(name = "canal_atendimento")
	private String canalAtendimento;
	
	@Column(name = "data_movimento")
	private Date dataMovimento;
	
	@Column(name = "saldo_anterior")
	private Integer saldoAnterior;
	
	@Column(name = "entrada")
	private Integer entrada;
	
	@Column(name = "reforco")
	private Integer reforco;
	
	@Column(name = "fatura")
	private Integer fatura;
	
	@Column(name = "a_vista")
	private Integer avista;
	
	@Column(name = "v_gratis")
	private Integer gratis;
	
	@Column(name = "nota")
	private Integer nota;
	
	@Column(name = "balcao")
	private Integer balcao;
	
	@Column(name = "devolucao")
	private Integer devolucao;
	
	@Column(name = "devolucao_sem_troc")
	private Integer devolucaoSemTroca;
	
	@Column(name = "transferencia")
	private Integer transferencia;
	
	@Column(name = "pre_venda")
	private Integer preVenda;
	
	@Column(name = "saida_estoque")
	private Integer saidaEstoque;
	
	@Column(name = "saldo_atual")
	private Integer saldoAtual;
  	
	public Long getCodEstoqueResumo() {
		return codEstoqueResumo;
	}

	public void setCodEstoqueResumo(Long codEstoqueResumo) {
		this.codEstoqueResumo = codEstoqueResumo;
	}

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

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCodCef() {
		return codCef;
	}

	public void setCodCef(String codCef) {
		this.codCef = codCef;
	}

	public String getCodCefContrapartida() {
		return codCefContrapartida;
	}

	public void setCodCefContrapartida(String codCefContrapartida) {
		this.codCefContrapartida = codCefContrapartida;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getCanalAtendimento() {
		return canalAtendimento;
	}

	public void setCanalAtendimento(String canalAtendimento) {
		this.canalAtendimento = canalAtendimento;
	}

	public Date getDataMovimento() {
		return dataMovimento;
	}

	public void setDataMovimento(Date dataMovimento) {
		this.dataMovimento = dataMovimento;
	}

	public Integer getSaldoAnterior() {
		return saldoAnterior;
	}

	public void setSaldoAnterior(Integer saldoAnterior) {
		this.saldoAnterior = saldoAnterior;
	}

	public Integer getEntrada() {
		return entrada;
	}

	public void setEntrada(Integer entrada) {
		this.entrada = entrada;
	}

	public Integer getReforco() {
		return reforco;
	}

	public void setReforco(Integer reforco) {
		this.reforco = reforco;
	}

	public Integer getFatura() {
		return fatura;
	}

	public void setFatura(Integer fatura) {
		this.fatura = fatura;
	}

	public Integer getAvista() {
		return avista;
	}

	public void setAvista(Integer avista) {
		this.avista = avista;
	}

	public Integer getGratis() {
		return gratis;
	}

	public void setGratis(Integer gratis) {
		this.gratis = gratis;
	}

	public Integer getNota() {
		return nota;
	}

	public void setNota(Integer nota) {
		this.nota = nota;
	}

	public Integer getBalcao() {
		return balcao;
	}

	public void setBalcao(Integer balcao) {
		this.balcao = balcao;
	}

	public Integer getDevolucao() {
		return devolucao;
	}

	public void setDevolucao(Integer devolucao) {
		this.devolucao = devolucao;
	}

	public Integer getDevolucaoSemTroca() {
		return devolucaoSemTroca;
	}

	public void setDevolucaoSemTroca(Integer devolucaoSemTroca) {
		this.devolucaoSemTroca = devolucaoSemTroca;
	}

	public Integer getTransferencia() {
		return transferencia;
	}

	public void setTransferencia(Integer transferencia) {
		this.transferencia = transferencia;
	}

	public Integer getPreVenda() {
		return preVenda;
	}

	public void setPreVenda(Integer preVenda) {
		this.preVenda = preVenda;
	}

	public Integer getSaidaEstoque() {
		return saidaEstoque;
	}

	public void setSaidaEstoque(Integer saidaEstoque) {
		this.saidaEstoque = saidaEstoque;
	}

	public Integer getSaldoAtual() {
		return saldoAtual;
	}

	public void setSaldoAtual(Integer saldoAtual) {
		this.saldoAtual = saldoAtual;
	}

	@Override
	public Long getIdentificacao() {
		return codEstoqueResumo;
	}

}
