package tarea2.frontend;

import tarea2.backend.Pregunta;
import tarea2.backend.QuizManager;
import tarea2.backend.SeleccionMultiple;
import tarea2.backend.VerdaderoFalso;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
        opcionesPanel.setBorder(new EmptyBorder(30, 10, 10, 10));

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

        Alternativa verdadero = new Alternativa("V", "Verdadero", 1);
        OpcionVF opcionVF = new OpcionVF();

        String respuestaActual = quizManager.getRespuestasUsuario().get(quizManager.getIndiceActual());
        boolean respuestaActualRespondida = !respuestaActual.isEmpty();

        // Coloreal la alternativa si el usuario ya la respondio.
        if(respuestaActualRespondida){

            // Verdadero
            if(Integer.parseInt(respuestaActual) == 1){
                verdadero.getAlternativaBoton().setBackground(colorSeleccionado);
                verdadero.getAlternativasPanel().setBackground(colorSeleccionado);
            }

            // Falso
            else if (Integer.parseInt(respuestaActual) == 0){
                String justificacionUsuario = this.quizManager.getJustificacionUsuario().get(quizManager.getIndiceActual());
                opcionVF.getJustificacionTextField().setText(justificacionUsuario);
                opcionVF.getJustificacionTextField().setEnabled(true);
                opcionVF.getFalsoButton().setBackground(colorSeleccionado);
                opcionVF.getJustificacionTextField().setBackground(colorSeleccionado);
            }
        }

        opcionesPanel.add(verdadero.getAlternativasPanel());
        //TODO: Hay que "limpiar" OpcionVF - tiene mucha basura que no se esta usando.
        opcionesPanel.add(opcionVF.getOpcionVFPanel());

        // Clickeo en V
        verdadero.getAlternativaBoton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                opcionVF.getFalsoButton().setBackground(new JButton().getBackground());
                opcionVF.getJustificacionTextField().setBackground(new JButton().getBackground());
                opcionVF.getJustificacionTextField().setEnabled(false);

                System.out.println("Usuario marco indice: " + verdadero.getIndiceRespuesta());

                verdadero.getAlternativaBoton().setBackground(colorSeleccionado);
                verdadero.getAlternativasPanel().setBackground(colorSeleccionado);

                quizManager.responderActual(String.valueOf(verdadero.getIndiceRespuesta()));

            }
        });

        // Clickeo en F
        opcionVF.getFalsoButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                verdadero.getAlternativaBoton().setBackground(new JButton().getBackground());
                verdadero.getAlternativasPanel().setBackground(new JButton().getBackground());

                opcionVF.getJustificacionTextField().setEnabled(true);
                opcionVF.getFalsoButton().setBackground(colorSeleccionado);
                opcionVF.getJustificacionTextField().setBackground(colorSeleccionado);

                System.out.println("Usuario marco indice: 0");

                quizManager.responderActual(String.valueOf("0"));
            }
        });

        opcionVF.getJustificacionTextField().getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                guardarJustificacion();
            }
            public void removeUpdate(DocumentEvent e) {
                guardarJustificacion();
            }
            public void insertUpdate(DocumentEvent e) {
                guardarJustificacion();
            }

            public void guardarJustificacion() {
                quizManager.getJustificacionUsuario().put(quizManager.getIndiceActual(), opcionVF.getJustificacionTextField().getText());
            }
        });
    }
}
