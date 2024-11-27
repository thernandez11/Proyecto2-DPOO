package controladores;

import componentes.Actividad;
import componentes.LearningPath;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import persistencia.PersistenciaLearningPaths;

public class ControladorLearningPath {

	HashMap<Integer, LearningPath> learningPaths;
	PersistenciaLearningPaths persistenciaLearningPaths;
	ControladorActividad controladorActividades;
	
	public ControladorLearningPath(ControladorActividad controladorActividades) {
	    this.learningPaths = new HashMap<>();
		this.persistenciaLearningPaths = new PersistenciaLearningPaths();
		this.controladorActividades = controladorActividades;
	}
	
	//Consultar informacion learningPaths
	public LearningPath getLearningPath(int idLp) {
		LearningPath lp = learningPaths.get(idLp);
		return lp;
	}
	public Collection<LearningPath> getLearningPaths() {
		Collection<LearningPath> lps = learningPaths.values();
		return lps;
	}
	public Collection<LearningPath> getLearningPathsPropios(String loginActual) {
		ArrayList<LearningPath> propios = new ArrayList<>();
		Collection<LearningPath> lps = learningPaths.values();
		for (LearningPath lp : lps) {
			if (lp.getLoginCreador().equals(loginActual)) {
				propios.add(lp);
			}
		}
		return propios;
	}
	public ArrayList<Integer> getIdsActividadesLP(int idLP) {
		ArrayList<Integer> ids = new ArrayList<>();
		LearningPath lp = learningPaths.get(idLP);
		Set<Actividad> actividades = lp.getActividades().keySet();
		for (Actividad a : actividades) {
			ids.add(a.getId());
		}
		return ids;
	}
	
	//Crear learning path
	public int crearLearningPath(String loginActual) {
		LocalDateTime fecha = LocalDateTime.now();
		int id = learningPaths.size() + 1;
		LearningPath lp = new LearningPath(id, loginActual, 0, fecha, fecha);
		learningPaths.put(id, lp);
		return id;
	}

	public void addLearningPath(LearningPath lp) {
		learningPaths.put(lp.getId(), lp);
	}
	
	//Editar atributos learning path
	public void editarTitulo(int id, String titulo) {
		LearningPath lp = learningPaths.get(id);
		lp.setTitulo(titulo);
	}
	public void editarDescripcionGeneral(int id, String descripcion) {
		LearningPath lp = learningPaths.get(id);
		lp.setDescripcionGeneral(descripcion);
	}
	public void editarNivelDificultad(int id, String nivelDificultad) {
		LearningPath lp = learningPaths.get(id);
		lp.setNivelDificultad(nivelDificultad);
	}
	public void editarDuracion(int id, int duracion) {
		LearningPath lp = learningPaths.get(id);
		lp.setDuracion(duracion);
	}
	public void editarActividades(int id, HashMap<Actividad, Boolean> actividades) {
		LearningPath lp = learningPaths.get(id);
		lp.setActividades(actividades);
	}
	public void editarVersion(int id) {
		LearningPath lp = learningPaths.get(id);
		lp.setVersion(lp.getVersion() + 1);
	}
	public void editarFechaModificacion(int id) {
		LearningPath lp = learningPaths.get(id);
		LocalDateTime fecha = LocalDateTime.now();
		lp.setFechaModificacion(fecha);
	}
		
	//Persistencia learning paths
	public void guardarLPEnArchivo(String nombreArchivo) throws IOException {
        String directorioRelativo = "datos"; 
        File directorio = new File(directorioRelativo);
        
        if (!directorio.exists()) {
            directorio.mkdirs(); 
        }

        File archivo = new File(directorio, nombreArchivo); 

        persistenciaLearningPaths.guardarLearningPaths(archivo.getAbsolutePath(), this, controladorActividades);
    }

	

    public void cargarLPDesdeArchivo(String nombreArchivo) throws IOException {
        if (learningPaths == null) {
        	learningPaths = new HashMap<>();
        }

        String directorioRelativo = "datos";
        File directorio = new File(directorioRelativo);

        if (!directorio.exists()) {
            directorio.mkdir();
        }

        File archivo = new File(directorio, nombreArchivo);

        if (!archivo.exists()) {
            archivo.createNewFile();
            System.out.println("No existe el archivo " + nombreArchivo + ". Se ha creado uno nuevo");
        } else {
            persistenciaLearningPaths.cargarLearningPaths(archivo.getAbsolutePath(), this, controladorActividades);
        }
    }
	
}
