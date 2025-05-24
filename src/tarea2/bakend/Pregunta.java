package tarea2.bakend;



public abstract class Pregunta {
    protected String enunciado;
    protected BloomLevel bloomLevel;
    protected int tiempoEstimado;

    public Pregunta(String enunciado, BloomLevel bloomLevel, int tiempoEstimado) {
        this.enunciado = enunciado;
        this.bloomLevel = bloomLevel;
        this.tiempoEstimado = tiempoEstimado;
    }

    public abstract boolean esRespuestaCorrecta(String respuestaUsuario);

    public String getEnunciado() {
        return enunciado;
    }

    public BloomLevel getBloomLevel() {
        return bloomLevel;
    }

    public int getTiempoEstimado() {
        return tiempoEstimado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public void setBloomLevel(BloomLevel bloomLevel) {
        this.bloomLevel = bloomLevel;
    }

    public void setTiempoEstimado(int tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
    }
}
