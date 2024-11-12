package componentes;

import java.time.LocalDateTime;
import java.util.List;

public class Actividad {
	
	private String loginCreador;
	private int id;
	private String tipo;
	private String descripcion;
	private List<String> objetivos;
	private String nivelDificultad;
	private int duracion;
	private List<Actividad> actividadesPrevias;
	private List<Actividad> actividadesSeguimiento;
	private LocalDateTime fechaLimite;
	private String url;
	private List<PreguntaMultiple> preguntasMultiples;
	private List<PreguntaVerdaderoFalso> preguntasVerdaderoFalso;
	private List<PreguntaAbierta> preguntasAbiertas;
	private int notaMinima;
	
	//Constructor
	public Actividad(int id, String loginCreador) {
		this.id = id;
		this.loginCreador = loginCreador;
	}
	
	//Getters y Setters
	public String getLoginCreador() {
		return loginCreador;
	}

	public void setLoginCreador(String loginCreador) {
		this.loginCreador = loginCreador;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<String> getObjetivos() {
		return objetivos;
	}

	public void setObjetivos(List<String> objetivos) {
		this.objetivos = objetivos;
	}

	public String getNivelDificultad() {
		return nivelDificultad;
	}

	public void setNivelDificultad(String nivelDificultad) {
		this.nivelDificultad = nivelDificultad;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public List<Actividad> getActividadesPrevias() {
		return actividadesPrevias;
	}

	public void setActividadesPrevias(List<Actividad> actividadesPrevias) {
		this.actividadesPrevias = actividadesPrevias;
	}

	public LocalDateTime getFechaLimite() {
		return fechaLimite;
	}

	public void setFechaLimite(LocalDateTime fechaLimite) {
		this.fechaLimite = fechaLimite;
	}

	public List<Actividad> getActividadesSeguimiento() {
		return actividadesSeguimiento;
	}

	public void setActividadesSeguimiento(List<Actividad> actividadesSeguimiento) {
		this.actividadesSeguimiento = actividadesSeguimiento;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<PreguntaMultiple> getPreguntasMultiples() {
		return preguntasMultiples;
	}

	public void setPreguntasMultiples(List<PreguntaMultiple> preguntasMultiples) {
		this.preguntasMultiples = preguntasMultiples;
	}

	public List<PreguntaAbierta> getPreguntasAbiertas() {
		return preguntasAbiertas;
	}

	public void setPreguntasAbiertas(List<PreguntaAbierta> preguntasAbiertas) {
		this.preguntasAbiertas = preguntasAbiertas;
	}
	
	public int getNotaMinima() {
		return notaMinima;
	}
	
	public void setNotaMinima(int notaMinima) {
		this.notaMinima = notaMinima;
	}

	public List<PreguntaVerdaderoFalso> getPreguntasVerdaderoFalso() {
		return preguntasVerdaderoFalso;
	}

	public void setPreguntasVerdaderoFalso(List<PreguntaVerdaderoFalso> preguntasVerdaderoFalso) {
		this.preguntasVerdaderoFalso = preguntasVerdaderoFalso;
	}
}
