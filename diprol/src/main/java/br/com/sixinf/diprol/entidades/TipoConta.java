package br.com.sixinf.diprol.entidades;

public enum TipoConta {
	
	DESPESAS('D'),
	RECEITAS('R');
	
	private Character tipo;
	
	TipoConta(Character tipo){
		this.tipo = tipo;
	}
	
	public Character getTipo() {
		return this.tipo;
	}
	
}
