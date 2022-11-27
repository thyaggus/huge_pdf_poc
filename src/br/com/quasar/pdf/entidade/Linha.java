package br.com.quasar.pdf.entidade;

public class Linha {
	private String data;
	private String dia;
	private String historico;
	private String subHistorico;
	private String documento;
	private String valor;
	
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getDia() {
		return dia;
	}
	public void setDia(String dia) {
		this.dia = dia;
	}
	public String getHistorico() {
		return historico;
	}
	public void setHistorico(String historico) {
		this.historico = historico;
	}
	public String getSubHistorico() {
		return subHistorico;
	}
	public void setSubHistorico(String subHistorico) {
		this.subHistorico = subHistorico;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getLinha() {
		return String.format("%10s | %3s | %20s | %20s | %12s | %17s", data, dia, historico, subHistorico, documento, valor);
	}
	
	@Override
	public String toString() {
		return "Linha [data=" + data + ", dia=" + dia + ", historico=" + historico + ", subHistorico=" + subHistorico
				+ ", documento=" + documento + ", valor=" + valor + "]";
	}
	
	
}
