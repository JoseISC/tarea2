import vistoEnClases.frontend.*;
import vistoEnClases.bakend.Tablero;

public class Main {
    public static void main(String[] args) {
        Tablero tablero = new Tablero();

        MainWindow ventana = new MainWindow(tablero);
        ventana.setVisible(true);
    }
}