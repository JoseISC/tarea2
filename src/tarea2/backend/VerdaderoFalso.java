package tarea2.backend;

public class VerdaderoFalso extends Pregunta{

    private boolean respuestaCorrecta;
    private String justificacion;

    public VerdaderoFalso(String enunciado, BloomLevel bloomLevel, float tiempoEstimado, boolean respuestaCorrecta, String justificacion) {
        super(enunciado, bloomLevel, tiempoEstimado);
        this.respuestaCorrecta = respuestaCorrecta;
        this.justificacion = justificacion;
    }

    @Override public boolean esRespuestaCorrecta(String respuestaUsuario){
        if (respuestaUsuario.isEmpty()){
            return false;
        }

        boolean respuestaConvertida = "1".equals(respuestaUsuario);
        return respuestaConvertida == respuestaCorrecta;
    }

    public boolean getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setRespuestaCorrecta(boolean respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }
}
