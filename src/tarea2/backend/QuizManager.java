package tarea2.backend;
import com.google.gson.*;

import java.io.File;
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

    public List<Pregunta> getPreguntasSeleccionMultiple(){
        List<Pregunta> preguntasSM = new ArrayList<>();
        for (Pregunta pregunta : this.preguntas){
            if (pregunta instanceof SeleccionMultiple){
                preguntasSM.add(pregunta);
            }
        }
        return preguntasSM;
    }

    public List<Pregunta> getPreguntasVerdaderoFalso(){
        List<Pregunta> preguntasVF = new ArrayList<>();
        for (Pregunta pregunta : this.preguntas){
            if (pregunta instanceof VerdaderoFalso){
                preguntasVF.add(pregunta);
            }
        }
        return preguntasVF;
    }

    public List<String> getSMRespuestas(){
        List<String> respuestas = new ArrayList<>();
        for (Pregunta pregunta : this.preguntas){
            if (pregunta instanceof SeleccionMultiple){
                respuestas.add(this.respuestasUsuario.get(this.preguntas.indexOf(pregunta)));
            }
        }
        return respuestas;
    }

    public List<String> getVFRespuestas(){
        List<String> respuestas = new ArrayList<>();
        for (Pregunta pregunta : this.preguntas){
            if (pregunta instanceof VerdaderoFalso){
                respuestas.add(this.respuestasUsuario.get(this.preguntas.indexOf(pregunta)));
            }
        }
        return respuestas;
    }


    public int getPorcentajePregunta(List<Pregunta> preguntas, List<String> respuestas){
        int correctas = 0;
        for (Pregunta pregunta : preguntas){
            if (pregunta.esRespuestaCorrecta(respuestas.get(this.preguntas.indexOf(pregunta)))){
                correctas++;
            }
        }
        return correctas;
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


    // Método mejorado para cargar preguntas con mejor manejo de errores
    public List<Pregunta> leerPreguntasJson() throws Exception {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        List<Pregunta> preguntas = new ArrayList<>();

        try {
            // Intentar diferentes rutas posibles
            String[] rutasPosibles = {
                "src/data/preguntas.json",
                "data/preguntas.json",
                new File("").getAbsolutePath() + File.separator + "src" + File.separator + "data" + File.separator + "preguntas.json"
            };
            
            FileReader reader = null;
            String rutaUsada = null;
            
            for (String ruta : rutasPosibles) {
                try {
                    reader = new FileReader(ruta);
                    rutaUsada = ruta;
                    break;
                } catch (FileNotFoundException e) {
                    // Continuar con la siguiente ruta
                }
            }
            
            if (reader == null) {
                throw new FileNotFoundException("No se pudo encontrar el archivo preguntas.json en ninguna de las rutas esperadas");
            }
            
            System.out.println("Archivo cargado desde: " + rutaUsada);

            JsonArray jsonArray = parser.parse(reader).getAsJsonArray();
            reader.close();
            
            if (jsonArray.size() == 0) {
                throw new Exception("El archivo JSON está vacío o no contiene preguntas válidas");
            }

            for (JsonElement jsonElement : jsonArray) {
                try {
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
                    } else {
                        System.err.println("Tipo de pregunta desconocido: " + type);
                    }
                } catch (Exception e) {
                    System.err.println("Error al procesar pregunta: " + e.getMessage());
                    // Continuar con la siguiente pregunta
                }
            }
            
            if (preguntas.isEmpty()) {
                throw new Exception("No se pudieron cargar preguntas válidas del archivo JSON");
            }
            
            System.out.println("Se cargaron " + preguntas.size() + " preguntas exitosamente");
            return preguntas;
            
        } catch (Exception e) {
            throw new Exception("Error al leer el archivo de preguntas: " + e.getMessage(), e);
        }
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

    public boolean todasLasPreguntasRespondidas() {
        for (String respuesta : respuestasUsuario) {
            if (respuesta.isEmpty()) {
                return false;
            }
        }
        return true;
    }
    
    public List<Integer> getPreguntasSinResponder() {
        List<Integer> sinResponder = new ArrayList<>();
        for (int i = 0; i < respuestasUsuario.size(); i++) {
            if (respuestasUsuario.get(i).isEmpty()) {
                sinResponder.add(i + 1); // +1 para mostrar números de pregunta desde 1
            }
        }
        return sinResponder;
    }
    
    public int getCantidadPreguntasRespondidas() {
        int respondidas = 0;
        for (String respuesta : respuestasUsuario) {
            if (!respuesta.isEmpty()) {
                respondidas++;
            }
        }
        return respondidas;
    }

}
