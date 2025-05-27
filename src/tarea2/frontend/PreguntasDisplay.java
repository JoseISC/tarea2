package tarea2.frontend;

import tarea2.bakend.Pregunta;
import tarea2.bakend.QuizManager;
import tarea2.bakend.SeleccionMultiple;
import tarea2.bakend.VerdaderoFalso;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PreguntasDisplay {

    private QuizManager quizManager;

    private Alternativa alternatvias;
    private JPanel alternativasPanel;
    private JLabel enunciadoPreguntaLabel;
    private JPanel opcionesPanel;
    private List<Alternativa> alternativasGUI;
    private final Color colorSeleccionado = new Color(95, 218, 255);
    private final Color colorDefaultBoton = new Color(33,37,43);


    public PreguntasDisplay(QuizManager quizManager) {
        this.quizManager = quizManager;

        //opcionesPanel.setLayout(new BoxLayout(opcionesPanel, BoxLayout.Y_AXIS));
        opcionesPanel.setLayout(new GridLayout(0, 1));
        opcionesPanel.setBorder(new EmptyBorder(50, 10, 10, 10));

        this.alternativasGUI = new ArrayList<>();
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
        else if (pregunta instanceof VerdaderoFalso verdaderoFalso){
            crearVerdaderoFalso();
        }
        opcionesPanel.revalidate();

    }

    public void crearAlternativas(List<String> alternativas) {
        this.alternativasGUI.clear();

        // Ver si el usuario ya respondio la pregunta.
        String respuestaActual = quizManager.getRespuestasUsuario().get(quizManager.getIndiceActual());
        boolean respuestaActualRespondida = !respuestaActual.isEmpty();

        for (String a : alternativas) {
            int index = alternativas.indexOf(a);



            // this.alternativasGUI es el ARRAY de los botones.
            // alternativas (sin el this) es el STRING del texto de las alternativas.
            Alternativa alternativa = new Alternativa(String.valueOf(index), a, index);
            this.alternativasGUI.add(alternativa);
            opcionesPanel.add(alternativa.getAlternativasPanel());
            int espacio = 15;
            opcionesPanel.add(new Box.Filler(new Dimension(espacio, espacio), new Dimension(espacio, espacio), new Dimension(espacio, espacio)));

            // Coloreal la alternativa si el usuario ya la respondio.
            if(respuestaActualRespondida){
                if(Integer.parseInt(respuestaActual) == index){
                    alternativa.getAlternativaBoton().setBackground(colorSeleccionado);
                    alternativa.getAlternativasPanel().setBackground(colorSeleccionado);
                }
            }

            alternativa.getAlternativaBoton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    for (Alternativa a : alternativasGUI){
                        a.getAlternativaBoton().setBackground(new JButton().getBackground());
                        a.getAlternativasPanel().setBackground(new JButton().getBackground());
                    }


                    System.out.println("Usuario marco indice: " + alternativa.getIndiceRespuesta());

                    alternativa.getAlternativaBoton().setBackground(colorSeleccionado);
                    alternativa.getAlternativasPanel().setBackground(colorSeleccionado);

                    quizManager.responderActual(String.valueOf(alternativa.getIndiceRespuesta()));

                }
            });

        }
    }

    public void crearVerdaderoFalso() {
        OpcionVF opcionVF = new OpcionVF();
        opcionesPanel.add(opcionVF.getOpcionVFPanel());
    }
}
