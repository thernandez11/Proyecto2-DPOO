package controladores;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.time.LocalDateTime;

import componentes.Actividad;
import componentes.LearningPath;

public class ControladorLearningPath {

	HashMap<Integer, LearningPath> learningPaths;
	
	public ControladorLearningPath() {
	    this.learningPaths = new HashMap<>();
	}
	
	public LearningPath getLearningPath(int idLp) {
		LearningPath lp = learningPaths.get(idLp);
		return lp;
	}
	
	public Collection<LearningPath> getLearningPaths() {
		Collection<LearningPath> lps = learningPaths.values();
		return lps;
	}
	
	public int crearLearningPath(String loginActual) {
		LocalDateTime fecha = LocalDateTime.now();
		int id = learningPaths.size() + 1;
		LearningPath lp = new LearningPath(id, loginActual, 0, fecha, fecha);
		learningPaths.put(id, lp);
		return id;
	}
	
	public int crearLearningPathEditado(int idLp, String loginActual) {
		LearningPath lpOriginal = learningPaths.get(idLp);
		LocalDateTime fecha = LocalDateTime.now();
		int id = learningPaths.size() + 1;
		LearningPath lp = new LearningPath(id, loginActual, (lpOriginal.getVersion() + 1), lpOriginal.getFechaCreacion(), fecha);
		learningPaths.put(id, lp);
		return id;
	}
	
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
		
	public ArrayList<Integer> getActividadesLP(int idLP) {
		ArrayList<Integer> ids = new ArrayList<>();
		LearningPath lp = learningPaths.get(idLP);
		Set<Actividad> actividades = lp.getActividades().keySet();
		for (Actividad a : actividades) {
			ids.add(a.getId());
		}
		return ids;
	}
	
	public void guardarLPEnArchivo(String nombreArchivo) throws IOException {
        String directorioRelativo = "Persistencia"; 
        File directorio = new File(directorioRelativo);
        
        if (!directorio.exists()) {
            directorio.mkdirs(); 
        }

        File archivo = new File(directorio, nombreArchivo); 

        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo, true))) { 
            for (LearningPath learningPath : learningPaths.values()) {
                writer.println(learningPath.getTitulo() + "," + learningPath.getDescripcionGeneral() + "," + learningPath.getNivelDificultad()+ "," + 
                				learningPath.getDuracion()+ "," + learningPath.getFechaCreacion()+ "," + learningPath.getFechaModificacion()+ "," + 
                				learningPath.getVersion() + "," + learningPath.getActividades() + "," + learningPath.getLoginCreador() ); // Guarda como login,password
            }
            System.out.println("Datos guardados exitosamente en " + archivo.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error al guardar los datos: " + e.getMessage());
            throw e;
        }
    }
    public void cargarLPDesdeArchivo(String nombreArchivo) throws IOException {
        if (learningPaths == null) {
        	learningPaths = new HashMap<>();
        }

        String directorioRelativo = "Persistencia"; 
        File archivo = new File(directorioRelativo, nombreArchivo);

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parte = line.split(","); 
                if (parte.length == 2) {
                    String titulo = parte[0];
                    String descripcion = parte[1];
                    String dificultad = parte[2];
                    Integer duracion = Integer.parseInt(parte[3]);
                    LocalDateTime fechaCreacion = LocalDateTime.parse(parte[4]);
                    LocalDateTime fechaModificacion = LocalDateTime.parse(parte[5]);
                    Integer version = Integer.parseInt(parte[6]);
                    String Actividades= parte[7];
                    String logInCreador = parte[8];
                    learningPaths.put(learningPaths.size() + 1, new LearningPath(version, logInCreador, version, fechaCreacion, fechaModificacion));
                }
            }
            System.out.println("Datos cargados exitosamente desde " + archivo.getAbsolutePath() + ". Total de estudiantes: " + learningPaths.size());
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no existe. Se creará al guardar.");
        } catch (IOException e) {
            System.err.println("Error al cargar los datos: " + e.getMessage());
            throw e;
        }
    }
	
}
