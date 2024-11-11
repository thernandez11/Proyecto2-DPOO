package componentes;

import java.util.HashMap;
import java.time.LocalDateTime;

public class LearningPath {

	private String titulo;
	private String descripcionGeneral;
	private String nivelDificultad;
	private int duracion;
	private LocalDateTime fechaCreacion;
	private LocalDateTime fechaModificacion;
	private int version;
	private HashMap<Actividad, Boolean> actividades;
	private String loginCreador;
	private int id;
	
	//Constructor
	public LearningPath(int id, String login, int version, LocalDateTime fechaCreacion, LocalDateTime fechaModificacion) {
		this.id = id;
		this.loginCreador = login;
		this.version = version;
		this.fechaCreacion = fechaCreacion;
		this.fechaModificacion = fechaModificacion;
	}

	//Getters y Setters
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcionGeneral() {
		return descripcionGeneral;
	}

	public void setDescripcionGeneral(String descripcionGeneral) {
		this.descripcionGeneral = descripcionGeneral;
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

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public LocalDateTime getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(LocalDateTime fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public HashMap<Actividad, Boolean> getActividades() {
		return actividades;
	}

	public void setActividades(HashMap<Actividad, Boolean> actividades) {
		this.actividades = actividades;
	}

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
}
