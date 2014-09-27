/**
 * 
 */
package br.com.sixinf.diprol.converters;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.sixinf.ferramentas.persistencia.Entidade;

/**
 * @author maicon
 * 
 */
@FacesConverter(value = "entidadeConverter")
public class EntidadeConverter implements Converter {

	private static final String key = "com.example.jsf.EntityConverter";
	private static final String empty = "";

	private Map<String, Object> getViewMap(FacesContext context) {
		Map<String, Object> viewMap = context.getViewRoot().getViewMap();
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Map<String, Object> idMap = (Map) viewMap.get(key);
		if (idMap == null) {
			idMap = new HashMap<String, Object>();
			viewMap.put(key, idMap);
		}
		return idMap;
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent c, String value) {
		if (value == null || 
				value.isEmpty()) {
			return null;
		}		
		return getViewMap(context).get(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent c, Object value) {
		if (value == null) {
			return empty;
		}
		String id = ((Entidade) value).getIdentificacao().toString() + value.getClass().hashCode();
		getViewMap(context).put(id, value);
		return id;
	}

}
