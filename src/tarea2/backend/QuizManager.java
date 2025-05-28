package tarea2.backend;
import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class QuizManager {
    private List<Pregunta> preguntas;
    private List<String> respuestasUsuario;
    private int indiceActual = 0;

    public QuizManager() {
        this.preguntas = new ArrayList<>();
        this.respuestasUsuario = new ArrayList<>();
        this.indiceActual = 0;
    }

    public void iniciarPrueba(){
        // Hacer que aparezca el GUI PruebaPreguntas...
        // por ahora esta hard-codeado.
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

    public boolean puedoAvanzar(){
        return indiceActual < preguntas.size() - 1;
    }

    public boolean retroceder(){
        if (indiceActual > 0){
            indiceActual--;
            return true;
        }
        return false;
    }

    public boolean puedoRetroceder(){
        return indiceActual > 0;
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

    public int getDuracionEnMinutos(){
        float duracion = 0;
        for (Pregunta pregunta : preguntas){
            duracion += pregunta.tiempoEstimado;
        }
        return Math.round(duracion / 60);
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


    // TODO: fileName "src/data/preguntas.json" puede arrojar error al correr el programa una vez compilado.
    public List<Pregunta> leerPreguntasJson() throws FileNotFoundException {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();

        JsonArray jsonArray = parser.parse(new FileReader("tarea2/src/data/preguntas.json")).getAsJsonArray();
        List<Pregunta> preguntas = new ArrayList<>();

        for (JsonElement jsonElement : jsonArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String type = jsonObject.get("type").getAsString();
            String enunciado = jsonObject.get("enunciado").getAsString();
            BloomLevel bloomLevel = gson.fromJson(jsonObject.get("bloomLevel"), BloomLevel.class);
            float tiempoEstimado = jsonObject.get("tiempoEstimado").getAsFloat();

            if (type.equals("SeleccionMultiple")) {
                JsonArray opcionesArray = jsonObject.get("opciones").getAsJsonArray();
                List<String> opciones = new ArrayList<>();
                for (JsonElement opcion : opcionesArray) {
                    opciones.add(opcion.getAsString());
                }
                int indicesRespuestaCorrecta = jsonObject.get("indiceRespuestaCorrecta").getAsInt();
                SeleccionMultiple sm = new SeleccionMultiple(enunciado, bloomLevel, tiempoEstimado, opciones, indicesRespuestaCorrecta);
                preguntas.add(sm);
            } else if (type.equals("VerdaderoFalso")) {
                boolean respuestaCorrecta = jsonObject.get("respuestaCorrecta").getAsBoolean();
                String justificacion = jsonObject.get("justificacion").getAsString();

                VerdaderoFalso vf = new VerdaderoFalso(enunciado, bloomLevel, tiempoEstimado, respuestaCorrecta, justificacion);
                preguntas.add(vf);

            }
        }
        return preguntas;
    }

    public void devMostrarPreguntas(){
        for (Pregunta p : preguntas) {
            System.out.println("Pregunta: " + p.getEnunciado() + " " + p.getBloomLevel() + " " + p.getTiempoEstimado());
        }
    }

    public void devMostrarRespuestas(){
        for (String respuesta : respuestasUsuario) {
            System.out.println("Respuesta " + respuestasUsuario.indexOf(respuesta) + ": " + respuesta);
        }
    }





}
