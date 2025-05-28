package tarea2.backend;



public abstract class Pregunta {
    protected String enunciado;
    protected BloomLevel bloomLevel;
    protected float tiempoEstimado;

    public Pregunta(String enunciado, BloomLevel bloomLevel, float tiempoEstimado) {
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

    public float getTiempoEstimado() {
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
