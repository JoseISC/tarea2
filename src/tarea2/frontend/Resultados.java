package tarea2.frontend;

import tarea2.backend.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class Resultados {
    private QuizManager quizManager;

    private JPanel resultadosPanel;
    private JPanel respuestasPanel;
    private JButton button1;
    private JScrollPane scrollPane;
    private JPanel resumenPanel;

    private List<JPanel> bloquesPreguntas;

    private List<JLabel> resumenPorNivelLabels;
    private JLabel resumenQCMLabel;
    private JLabel resumenVFLabel;

    private JButton btnRetourMenu;



    public Resultados(QuizManager quizManager) {
        this.quizManager = quizManager;
        this.resultadosPanel = new JPanel(new BorderLayout());
        this.bloquesPreguntas = new ArrayList<>();
        this.resumenPorNivelLabels = new ArrayList<>();

        respuestasPanel = new JPanel();
        respuestasPanel.setLayout(new BoxLayout(respuestasPanel, BoxLayout.Y_AXIS));

        List<Pregunta> preguntas = quizManager.getPreguntas();
        List<String> respuestas = quizManager.getRespuestasUsuario();

        for (int i = 0; i < preguntas.size(); i++) {
            Pregunta pregunta = preguntas.get(i);
            String respuestaUsuario = respuestas.get(i);
            boolean esCorrecta = pregunta.esRespuestaCorrecta(respuestaUsuario);

            String respuestaFormateada = formatRespuestaUsuario(pregunta, respuestaUsuario);

            JPanel bloquePregunta = new JPanel();
            bloquePregunta.setLayout(new BoxLayout(bloquePregunta, BoxLayout.Y_AXIS));
            bloquePregunta.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createEtchedBorder(), "Pregunta " + (i + 1),
                    TitledBorder.LEFT, TitledBorder.TOP
            ));

            bloquePregunta.add(new JLabel("Enunciado: " + pregunta.getEnunciado()));
            bloquePregunta.add(new JLabel("Respuesta dada : " + respuestaFormateada));
            bloquePregunta.add(new JLabel("Estado: " + (esCorrecta ? "Correcta" : "Incorrecta")));

            if (pregunta instanceof VerdaderoFalso vf && !((VerdaderoFalso) pregunta).getRespuestaCorrecta()) {
                bloquePregunta.add(new JLabel("Justificación: " + vf.getJustificacion()));
            }

            bloquesPreguntas.add(bloquePregunta);
            respuestasPanel.add(bloquePregunta);
            respuestasPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        scrollPane = new JScrollPane(respuestasPanel);
        resultadosPanel.add(scrollPane, BorderLayout.CENTER);

        resumenPanel = new JPanel(new GridLayout(0, 1));
        resumenPanel.setBorder(BorderFactory.createTitledBorder("Resumen estadístico"));

        ResultadosRevision resumen = quizManager.revisarRespuestas();
        BloomLevel[] niveles = BloomLevel.values();

        // Agregar información general
        int totalPreguntas = quizManager.getPreguntas().size();
        int preguntasRespondidas = quizManager.getCantidadPreguntasRespondidas();
        int totalCorrectas = resumen.correctasSeleccionMultiple + resumen.correctasVerdaderoFalso;
        double porcentajeGeneral = totalPreguntas > 0 ? (100.0 * totalCorrectas / totalPreguntas) : 0;
        
        JLabel resumenGeneral = new JLabel("RESUMEN GENERAL:");
        resumenGeneral.setFont(resumenGeneral.getFont().deriveFont(Font.BOLD));
        resumenPanel.add(resumenGeneral);
        
        resumenPanel.add(new JLabel("Total de preguntas: " + totalPreguntas));
        resumenPanel.add(new JLabel("Preguntas respondidas: " + preguntasRespondidas));
        resumenPanel.add(new JLabel("Respuestas correctas: " + totalCorrectas + "/" + totalPreguntas + 
                                   " (" + String.format("%.1f", porcentajeGeneral) + "%)"));
        
        // Separador
        resumenPanel.add(new JLabel(" "));
        JLabel resumenPorTipo = new JLabel("POR TIPO DE PREGUNTA:");
        resumenPorTipo.setFont(resumenPorTipo.getFont().deriveFont(Font.BOLD));
        resumenPanel.add(resumenPorTipo);

        // Calcular porcentajes para tipos de pregunta
        double pctSM = resumen.totalSeleccionMultiple > 0 ? 
            (100.0 * resumen.correctasSeleccionMultiple / resumen.totalSeleccionMultiple) : 0;
        double pctVF = resumen.totalVerdaderoFalso > 0 ? 
            (100.0 * resumen.correctasVerdaderoFalso / resumen.totalVerdaderoFalso) : 0;
            
        resumenQCMLabel = new JLabel("Selección Múltiple: " + resumen.correctasSeleccionMultiple + "/" + 
                                    resumen.totalSeleccionMultiple + " (" + String.format("%.1f", pctSM) + "%)");
        resumenVFLabel = new JLabel("Verdadero/Falso: " + resumen.correctasVerdaderoFalso + "/" + 
                                   resumen.totalVerdaderoFalso + " (" + String.format("%.1f", pctVF) + "%)");

        resumenPanel.add(resumenQCMLabel);
        resumenPanel.add(resumenVFLabel);
        
        // Separador
        resumenPanel.add(new JLabel(" "));
        JLabel resumenPorNivel = new JLabel("POR NIVEL DE BLOOM:");
        resumenPorNivel.setFont(resumenPorNivel.getFont().deriveFont(Font.BOLD));
        resumenPanel.add(resumenPorNivel);

        for (int i = 0; i < niveles.length; i++) {
            int total = resumen.totalesPorNivel[i];
            if (total > 0) {
                int correctas = resumen.correctasPorNivel[i];
                double pct = 100.0 * correctas / total;
                JLabel labelNivel = new JLabel(niveles[i] + ": " + correctas + "/" + total +
                        " (" + String.format("%.1f", pct) + "%)");
                resumenPorNivelLabels.add(labelNivel);
                resumenPanel.add(labelNivel);
            }
        }

        btnRetourMenu = new JButton("Volver al menú principal");
        btnRetourMenu.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(resultadosPanel);
            topFrame.dispose();
            DisplayMainMenu.showMenu();
        });
        
        JButton btnRevisarRespuestas = new JButton("Revisar respuestas");
        btnRevisarRespuestas.addActionListener(e -> {
            mostrarRevisionIndividual();
        });
        
        resumenPanel.add(btnRevisarRespuestas);
        resumenPanel.add(btnRetourMenu);

        resultadosPanel.add(resumenPanel, BorderLayout.SOUTH);
    }

    private String formatRespuestaUsuario(Pregunta pregunta, String respuestaUsuario) {
        if (pregunta instanceof SeleccionMultiple sm) {
            try {
                int idx = Integer.parseInt(respuestaUsuario);
                return sm.getOpciones().get(idx);
            } catch (NumberFormatException e) {
                return "Respuesta no válida";
            }
        } else if (pregunta instanceof VerdaderoFalso) {
            return "1".equals(respuestaUsuario) ? "Verdadero" : "Falso";
        }
        return respuestaUsuario;
    }

    public JPanel getResultadosPanel() {
        return resultadosPanel;
    }
    
    private void mostrarRevisionIndividual() {
        // Crear nueva ventana para revisión individual
        RevisionIndividual revision = new RevisionIndividual(quizManager);
        
        JFrame frame = new JFrame("Revisión de respuestas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);
        
        frame.add(revision.getRevisionPanel());
        
        // Cerrar la ventana actual
        JFrame frameActual = (JFrame) SwingUtilities.getWindowAncestor(resultadosPanel);
        frameActual.dispose();
        
        // Mostrar la nueva ventana
        frame.setVisible(true);
    }
}
