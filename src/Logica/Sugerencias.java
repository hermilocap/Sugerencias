package Logica;

public class Sugerencias {
	

	private int id;
	private  String sugerencia;
	private  String grupo;
	
	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSugerencia() {
		return sugerencia;
	}

	public void setSugerencia(String sugerencia) {
		this.sugerencia = sugerencia;
	}
	
	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	
	@Override
	public String toString(){
		return sugerencia;
	}
	
	


}
