package tarea2.bakend;
import java.util.ArrayList;
import java.util.List;

public class QuizManager {
    private List<Pregunta> preguntas;
    private List<String> respuestasUsuario;
    private int indiceActual;

    public QuizManager() {
        this.preguntas = new ArrayList<>();
        this.respuestasUsuario = new ArrayList<>();
        this.indiceActual = 0;
    }
    public void cargarPreguntas(List<Pregunta> nuevasPreguntas){
        this.preguntas = nuevasPreguntas;
        this.respuestasUsuario = new ArrayList<>();
        for (int i = 0; i < preguntas.size(); i++){
            respuestasUsuario.add("");
        }
    }
    public Pregunta getPreguntaActual(){
        if(indiceActual >= 0 && indiceActual < preguntas.size()){
            return preguntas.get(indiceActual);
        }
        return null;
    }
    public boolean avanzar(){
        if (indiceActual < preguntas.size() -1){
            indiceActual++;
            return true;
        }
        return false;
    }
    public boolean retroceder(){
        if (indiceActual > 0){
            indiceActual--;
            return true;
        }
        return false;
    }
    public void responderActual(String respuesta){
        if (indiceActual >= 0 && indiceActual < respuestasUsuario.size()){
            respuestasUsuario.set(indiceActual, respuesta);
        }
    }

    public int getIndiceActual() {
        return indiceActual;
    }

    public List<String> getRespuestasUsuario() {
        return respuestasUsuario;
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public ResultadosRevision revisarRespuestas(){
        int[] correctasPorNivel = new int[BloomLevel.values().length];
        int[] totalesPorNivel = new int[BloomLevel.values().length];
        int correctasSeleccionMultiple = 0;
        int totalSeleccionMultiple = 0;
        int correctasVerdaderoFalso = 0;
        int totalVerdaderoFalso = 0;

        for(int i = 0; i < preguntas.size(); i++){
            Pregunta pregunta = preguntas.get(i);
            String respuesta = respuestasUsuario.get(i);

            BloomLevel nivel = pregunta.getBloomLevel();
            totalesPorNivel[nivel.ordinal()]++;

            boolean correcta = pregunta.esRespuestaCorrecta(respuesta);
            if(correcta){
                correctasPorNivel[nivel.ordinal()]++;
            }

            if (pregunta instanceof SeleccionMultiple){
                totalSeleccionMultiple++;
                if(correcta) correctasSeleccionMultiple++;
            } else if (pregunta instanceof VerdaderoFalso) {
                totalVerdaderoFalso++;
                if(correcta) correctasVerdaderoFalso++;
            }
        }
        return new ResultadosRevision(correctasPorNivel,totalesPorNivel,correctasSeleccionMultiple,totalSeleccionMultiple,correctasVerdaderoFalso,totalVerdaderoFalso);
    }
}
