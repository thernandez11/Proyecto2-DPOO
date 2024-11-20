package componentes;

import java.time.LocalDateTime;
import java.util.HashMap;

public class RegistroActividad {
	
	private int idActividad;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaTerminado;
	private String estado;
	private HashMap<String, String> respuestas;
	private boolean obligatoria;
	private int nota;
	
	//Constructor
	public RegistroActividad(int idActividad, boolean obligatoria) {
		this.idActividad = idActividad;
		this.obligatoria = obligatoria;
		this.estado = "Pendiente";
	}
	
	//Getters y setters
	public int getIdActividad() {
		return idActividad;
	}

	public void setIdActividad(int idActividad) {
		this.idActividad = idActividad;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDateTime getFechaTerminado() {
		return fechaTerminado;
	}

	public void setFechaTerminado(LocalDateTime fechaTerminado) {
		this.fechaTerminado = fechaTerminado;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public HashMap<String, String> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(HashMap<String, String> respuestas) {
		this.respuestas = respuestas;
	}

	public boolean isObligatoria() {
		return obligatoria;
	}

	public void setObligatoria(boolean obligatoria) {
		this.obligatoria = obligatoria;
	}
	
	public int getNota() {
		return nota;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}
}
