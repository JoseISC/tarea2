package vistoEnClases.bakend;

import java.util.ArrayList;

public class Tablero {
    private ArrayList<Dado> dados;
    private ArrayList<Jugador> jugadores;

    public Tablero(){
        this.dados = new ArrayList<>();
        this.jugadores = new ArrayList<>();
        this.dados.add(new Dado());
        this.dados.add(new Dado());
    }

    public int lanzarDados(){
        int resultado = 0;
        for(Dado dado: this.dados){
            resultado = resultado + dado.lanzarDado();
        }
        return resultado;
    }

    public void agregarJugador(String nombre){
        this.jugadores.add(new Jugador(nombre));
    }

    public int getNumeroJugadores(){
        return this.jugadores.size();
    }

}
