/**
 * 
 */
package br.com.sixinf.diprol.beans;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import net.sf.jasperreports.engine.JRException;

import org.primefaces.model.StreamedContent;

import br.com.sixinf.diprol.facade.DiprolFacade;
import br.com.sixinf.ferramentas.log.LoggerException;

/**
 * @author maicon
 *
 */
@ManagedBean(name="relatoriosBean")
@ViewScoped
public class RelatoriosBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String classificacao;
	private List<SelectItem> classificacoes = new ArrayList<SelectItem>();
	
	@PostConstruct
	public void init() {
		classificacoes.add(new SelectItem("ALFABÉTICA"));
		classificacoes.add(new SelectItem("CÓDIGO CEF"));
	}

	public String getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}

	public List<SelectItem> getClassificacoes() {
		return classificacoes;
	}

	public void setClassificacoes(List<SelectItem> classificacoes) {
		this.classificacoes = classificacoes;
	}
	
	public StreamedContent printReport() throws JRException, IOException, ClassNotFoundException, SQLException, LoggerException {  
		
		return DiprolFacade.getInstance().geraReport();
    }
}
