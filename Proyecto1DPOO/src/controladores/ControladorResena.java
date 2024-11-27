package controladores;

import componentes.Resena;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import persistencia.PersistenciaResenias;

public class ControladorResena {

	ArrayList<Resena> resenas;
	
	public ControladorResena() {
		this.resenas = new ArrayList<>(); 
	}
	
	public void crearResena(int id, String opinion, int rating, String loginAutor, String rolAutor) {
		Resena r = new Resena(id, opinion, rating, loginAutor, rolAutor);
		resenas.add(r);
	}
	
	public ArrayList<Resena> resenasActividad(int idActividad) {
		ArrayList<Resena> listaResenas = new ArrayList<>();
		for (Resena resena : resenas) {
			if (resena.getIdActividad() == idActividad) {
				listaResenas.add(resena);
			}
		}
		return listaResenas;
	}
	
	public float calcularRating(int idActividad) {
		ArrayList<Resena> listaResenas = resenasActividad(idActividad);
		float sumaRatings = 0;
		for (Resena resena : listaResenas) {
			sumaRatings += resena.getRating();
		}
		if (sumaRatings != 0) {
			return sumaRatings/listaResenas.size();
		}
		return 0;
	}
	
    // Método para guardar reseñas en un archivo
    public void guardarResenasEnArchivo(String nombreArchivo) throws IOException {
        String directorioRelativo = "datos";
        File directorio = new File(directorioRelativo);

        if (!directorio.exists()) {
            directorio.mkdir();
        }

        File archivo = new File(directorio, nombreArchivo);

        PersistenciaResenias.guardarResenias(archivo.getAbsolutePath(), this);
    }

    // Método para cargar reseñas desde un archivo
    public void cargarResenasDesdeArchivo(String nombreArchivo) throws IOException {
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
            PersistenciaResenias.cargarResenias(archivo.getAbsolutePath(), this);
        }
    }


    public List<Resena> getResenas() {
        return resenas;
    }
	
}
