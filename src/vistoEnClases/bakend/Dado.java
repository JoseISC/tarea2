package vistoEnClases.bakend;

public class Dado {
    int min = 1;
    int max = 6;
    int range = max - min + 1;

    public int lanzarDado(){
        int random = (int)(Math.random() * range) + min;
        return random;
    }
}
