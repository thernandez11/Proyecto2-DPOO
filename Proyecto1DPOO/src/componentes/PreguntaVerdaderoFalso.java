package componentes;

import java.util.List;

public class PreguntaVerdaderoFalso extends Pregunta {

	private List<Opcion> opciones;
	
	//Constructor
	public PreguntaVerdaderoFalso(String textoPregunta, List<Opcion> opciones) {
		super(textoPregunta);
		this.opciones = opciones;
	}

	//Getters y Setters
	public List<Opcion> getOpciones() {
		return opciones;
	}

	public void setOpciones(List<Opcion> opciones) {
		this.opciones = opciones;
	}
	
}
