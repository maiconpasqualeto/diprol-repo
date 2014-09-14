/**
 * 
 */
package br.com.sixinf.diprol.beans;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import br.com.sixinf.diprol.dao.ClienteDAO;
import br.com.sixinf.diprol.entidades.Cliente;


/**
 * @author maicon
 *
 */
public class ClienteLazyList extends LazyDataModel<Cliente> {

	private static final long serialVersionUID = 1L;
	
		
	@Override
	public List<Cliente> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) {
		
		ClienteDAO dao = ClienteDAO.getInstance();
		
		if (sortField == null)
			sortField = "id";
		
		List<Cliente> produtos = dao.buscarClientesPaginado(first, first + pageSize, sortField, sortOrder, filters);
		
		this.setRowCount(dao.buscarCountClientesPaginado(filters).intValue());
		
		return produtos;
		
	}
	
}
