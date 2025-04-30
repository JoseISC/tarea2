package tarea2.bakend;

public class ResultadosRevision {
    public int[] correctasPorNivel;
    public int[] totalesPorNivel;
    public int correctasSeleccionMultiple;
    public int totalSeleccionMultiple;
    public int correctasVerdaderoFalso;
    public int totalVerdaderoFalso;

    public ResultadosRevision(int[] correctasPorNivel, int[] totalesPorNivel, int correctasSeleccionMultiple, int totalSeleccionMultiple, int correctasVerdaderoFalso, int totalVerdaderoFalso) {
        this.correctasPorNivel = correctasPorNivel;
        this.totalesPorNivel = totalesPorNivel;
        this.correctasSeleccionMultiple = correctasSeleccionMultiple;
        this.totalSeleccionMultiple = totalSeleccionMultiple;
        this.correctasVerdaderoFalso = correctasVerdaderoFalso;
        this.totalVerdaderoFalso = totalVerdaderoFalso;
    }
}
