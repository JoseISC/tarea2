package tarea2.frontend;

import tarea2.bakend.Pregunta;
import tarea2.bakend.SeleccionMultiple;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class PreguntasDisplay {

    private Alternativa alternatvias;

    private JPanel alternativasPanel;
    private JLabel enunciadoPreguntaLabel;
    private JPanel opcionesPanel;


    public PreguntasDisplay() {
        //opcionesPanel.setLayout(new BoxLayout(opcionesPanel, BoxLayout.Y_AXIS));
        opcionesPanel.setLayout(new GridLayout(0, 1));
        opcionesPanel.setBorder(new EmptyBorder(50, 10, 10, 10));
    }

    public JPanel getAlternativasPanel() {
        return alternativasPanel;
    }

    public void setPreguntasData(Pregunta pregunta) {
        opcionesPanel.removeAll();
        enunciadoPreguntaLabel.setText(pregunta.getEnunciado());


        if (pregunta instanceof SeleccionMultiple seleccionMultiple){
            crearAlternativas(seleccionMultiple.getOpciones());
        }
        opcionesPanel.revalidate();

    }

    public void crearAlternativas(List<String> alternativas) {
        for (String a : alternativas) {
            int index = alternativas.indexOf(a);
            Alternativa alternativa = new Alternativa(String.valueOf(index), a);
            opcionesPanel.add(alternativa.getAlternativasPanel());
            int espacio = 15;
            opcionesPanel.add(new Box.Filler(new Dimension(espacio, espacio), new Dimension(espacio, espacio), new Dimension(espacio, espacio)));
        }
    }
}
