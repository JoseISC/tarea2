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

        for (int i = 0; i < niveles.length; i++) {
            int total = resumen.totalesPorNivel[i];
            if (total > 0) {
                int correctas = resumen.correctasPorNivel[i];
                double pct = 100.0 * correctas / total;
                JLabel labelNivel = new JLabel(niveles[i] + " : " + correctas + "/" + total +
                        " (" + String.format("%.1f", pct) + "%)");
                resumenPorNivelLabels.add(labelNivel);
                resumenPanel.add(labelNivel);
            }
        }

        resumenQCMLabel = new JLabel("Seleccion Multiple: " + resumen.correctasSeleccionMultiple + "/" + resumen.totalSeleccionMultiple);
        resumenVFLabel = new JLabel("Verdadero/Falso: " + resumen.correctasVerdaderoFalso + "/" + resumen.totalVerdaderoFalso);

        resumenPanel.add(resumenQCMLabel);
        resumenPanel.add(resumenVFLabel);

        btnRetourMenu = new JButton("Volver al menú principal");
        btnRetourMenu.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(resultadosPanel);
            topFrame.dispose();
            DisplayMainMenu.showMenu();
        });
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
}
