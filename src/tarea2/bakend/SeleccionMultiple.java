package tarea2.bakend;
import java.util.List;

public class SeleccionMultiple extends Pregunta{
    private List<String> opciones;
    private int indiceRespuestaCorrecta;

    public SeleccionMultiple(String enunciado, BloomLevel bloomLevel, int tiempoEstimado, List<String> opciones, int indiceRespuestaCorrecta) {
        super(enunciado, bloomLevel, tiempoEstimado);
        this.opciones = opciones;
        this.indiceRespuestaCorrecta = indiceRespuestaCorrecta;
    }

    @Override public boolean esRespuestaCorrecta (String respuestaUsuario){
        try{
            int respuestaIndice = Integer.parseInt(respuestaUsuario);
            return respuestaIndice == indiceRespuestaCorrecta;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public List<String> getOpciones() {
        return opciones;
    }

    public int getIndiceRespuestaCorrecta() {
        return indiceRespuestaCorrecta;
    }
}
