package tarea2.frontend;

import tarea2.backend.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RevisionIndividual {
    private QuizManager quizManager;
    
    private JPanel revisionPanel;
    private JPanel preguntaPanel;
    private JLabel enunciadoLabel;
    private JPanel respuestasPanel;
    private JLabel numeroLabel;
    private JButton anteriorButton;
    private JButton siguienteButton;
    private JButton volverResumenButton;
    
    private int indiceActual = 0;
    
    public RevisionIndividual(QuizManager quizManager) {
        this.quizManager = quizManager;
        this.indiceActual = 0;
        
        initializeComponents();
        setupEventListeners();
        mostrarPreguntaActual();
    }
    
    private void initializeComponents() {
        revisionPanel = new JPanel(new BorderLayout());
        
        // Panel superior con número de pregunta
        JPanel topPanel = new JPanel(new FlowLayout());
        numeroLabel = new JLabel();
        topPanel.add(numeroLabel);
        revisionPanel.add(topPanel, BorderLayout.NORTH);
        
        // Panel central con pregunta y respuestas
        preguntaPanel = new JPanel();
        preguntaPanel.setLayout(new BoxLayout(preguntaPanel, BoxLayout.Y_AXIS));
        preguntaPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        enunciadoLabel = new JLabel();
        enunciadoLabel.setFont(enunciadoLabel.getFont().deriveFont(Font.BOLD, 16f));
        preguntaPanel.add(enunciadoLabel);
        
        preguntaPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        respuestasPanel = new JPanel();
        respuestasPanel.setLayout(new BoxLayout(respuestasPanel, BoxLayout.Y_AXIS));
        preguntaPanel.add(respuestasPanel);
        
        JScrollPane scrollPane = new JScrollPane(preguntaPanel);
        revisionPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Panel inferior con botones de navegación
        JPanel bottomPanel = new JPanel(new FlowLayout());
        
        anteriorButton = new JButton("Anterior");
        siguienteButton = new JButton("Siguiente");
        volverResumenButton = new JButton("Volver al resumen");
        
        bottomPanel.add(anteriorButton);
        bottomPanel.add(siguienteButton);
        bottomPanel.add(volverResumenButton);
        
        revisionPanel.add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventListeners() {
        anteriorButton.addActionListener(e -> {
            if (indiceActual > 0) {
                indiceActual--;
                mostrarPreguntaActual();
            }
        });
        
        siguienteButton.addActionListener(e -> {
            if (indiceActual < quizManager.getPreguntas().size() - 1) {
                indiceActual++;
                mostrarPreguntaActual();
            }
        });
        
        volverResumenButton.addActionListener(e -> {
            volverAlResumen();
        });
    }
    
    private void mostrarPreguntaActual() {
        Pregunta pregunta = quizManager.getPreguntas().get(indiceActual);
        String respuestaUsuario = quizManager.getRespuestasUsuario().get(indiceActual);
        boolean esCorrecta = pregunta.esRespuestaCorrecta(respuestaUsuario);
        
        // Actualizar número de pregunta
        numeroLabel.setText("Pregunta " + (indiceActual + 1) + " de " + quizManager.getPreguntas().size());
        
        // Actualizar enunciado
        enunciadoLabel.setText("<html><body style='width: 500px'>" + pregunta.getEnunciado() + "</body></html>");
        
        // Limpiar panel de respuestas
        respuestasPanel.removeAll();
        
        // Agregar información sobre la respuesta
        JLabel estadoLabel = new JLabel("Estado: " + (esCorrecta ? "CORRECTA" : "INCORRECTA"));
        estadoLabel.setFont(estadoLabel.getFont().deriveFont(Font.BOLD));
        estadoLabel.setForeground(esCorrecta ? Color.GREEN.darker() : Color.RED.darker());
        respuestasPanel.add(estadoLabel);
        
        respuestasPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        if (pregunta instanceof SeleccionMultiple) {
            mostrarRevisionSeleccionMultiple((SeleccionMultiple) pregunta, respuestaUsuario, esCorrecta);
        } else if (pregunta instanceof VerdaderoFalso) {
            mostrarRevisionVerdaderoFalso((VerdaderoFalso) pregunta, respuestaUsuario, esCorrecta);
        }
        
        // Agregar información del nivel de Bloom y tiempo
        respuestasPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        JLabel infoLabel = new JLabel("Nivel de Bloom: " + pregunta.getBloomLevel() + 
                                     " | Tiempo estimado: " + pregunta.getTiempoEstimado() + " segundos");
        infoLabel.setFont(infoLabel.getFont().deriveFont(Font.ITALIC));
        infoLabel.setForeground(Color.GRAY);
        respuestasPanel.add(infoLabel);
        
        // Actualizar botones
        anteriorButton.setEnabled(indiceActual > 0);
        siguienteButton.setEnabled(indiceActual < quizManager.getPreguntas().size() - 1);
        
        // Revalidar panel
        respuestasPanel.revalidate();
        respuestasPanel.repaint();
    }
    
    private void mostrarRevisionSeleccionMultiple(SeleccionMultiple pregunta, String respuestaUsuario, boolean esCorrecta) {
        respuestasPanel.add(new JLabel("Opciones:"));
        respuestasPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        
        int respuestaUsuarioInt = -1;
        try {
            respuestaUsuarioInt = Integer.parseInt(respuestaUsuario);
        } catch (NumberFormatException e) {
            // Respuesta inválida
        }
        
        for (int i = 0; i < pregunta.getOpciones().size(); i++) {
            String opcion = pregunta.getOpciones().get(i);
            JLabel opcionLabel = new JLabel((i + 1) + ") " + opcion);
            
            // Colorear según corresponda
            if (i == pregunta.getIndiceRespuestaCorrecta()) {
                // Respuesta correcta
                opcionLabel.setForeground(Color.GREEN.darker());
                opcionLabel.setText(opcionLabel.getText() + " ✓ (Correcta)");
                opcionLabel.setFont(opcionLabel.getFont().deriveFont(Font.BOLD));
            }
            
            if (i == respuestaUsuarioInt && !esCorrecta) {
                // Respuesta del usuario si es incorrecta
                opcionLabel.setForeground(Color.RED.darker());
                opcionLabel.setText(opcionLabel.getText() + " ✗ (Tu respuesta)");
            } else if (i == respuestaUsuarioInt && esCorrecta) {
                opcionLabel.setText(opcionLabel.getText() + " (Tu respuesta)");
            }
            
            respuestasPanel.add(opcionLabel);
            respuestasPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        }
    }
    
    private void mostrarRevisionVerdaderoFalso(VerdaderoFalso pregunta, String respuestaUsuario, boolean esCorrecta) {
        boolean respuestaUsuarioBool = "1".equals(respuestaUsuario);
        boolean respuestaCorrectaBool = pregunta.getRespuestaCorrecta();
        
        respuestasPanel.add(new JLabel("Opciones:"));
        respuestasPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        
        // Opción Verdadero
        JLabel verdaderoLabel = new JLabel("Verdadero");
        if (respuestaCorrectaBool) {
            verdaderoLabel.setForeground(Color.GREEN.darker());
            verdaderoLabel.setText(verdaderoLabel.getText() + " ✓ (Correcta)");
            verdaderoLabel.setFont(verdaderoLabel.getFont().deriveFont(Font.BOLD));
        }
        if (respuestaUsuarioBool && !esCorrecta) {
            verdaderoLabel.setForeground(Color.RED.darker());
            verdaderoLabel.setText(verdaderoLabel.getText() + " ✗ (Tu respuesta)");
        } else if (respuestaUsuarioBool && esCorrecta) {
            verdaderoLabel.setText(verdaderoLabel.getText() + " (Tu respuesta)");
        }
        respuestasPanel.add(verdaderoLabel);
        
        // Opción Falso
        JLabel falsoLabel = new JLabel("Falso");
        if (!respuestaCorrectaBool) {
            falsoLabel.setForeground(Color.GREEN.darker());
            falsoLabel.setText(falsoLabel.getText() + " ✓ (Correcta)");
            falsoLabel.setFont(falsoLabel.getFont().deriveFont(Font.BOLD));
        }
        if (!respuestaUsuarioBool && !esCorrecta) {
            falsoLabel.setForeground(Color.RED.darker());
            falsoLabel.setText(falsoLabel.getText() + " ✗ (Tu respuesta)");
        } else if (!respuestaUsuarioBool && esCorrecta) {
            falsoLabel.setText(falsoLabel.getText() + " (Tu respuesta)");
        }
        respuestasPanel.add(falsoLabel);
        
        // Mostrar justificación si la respuesta correcta es falso
        if (!respuestaCorrectaBool) {
            respuestasPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            JLabel justificacionTitulo = new JLabel("Justificación:");
            justificacionTitulo.setFont(justificacionTitulo.getFont().deriveFont(Font.BOLD));
            respuestasPanel.add(justificacionTitulo);
            
            JLabel justificacionTexto = new JLabel("<html><body style='width: 400px'>" + 
                                                  pregunta.getJustificacion() + "</body></html>");
            respuestasPanel.add(justificacionTexto);
        }
    }
    
    private void volverAlResumen() {
        // Cerrar la ventana actual y volver al resumen
        JFrame frameActual = (JFrame) SwingUtilities.getWindowAncestor(revisionPanel);
        frameActual.dispose();
        
        // Crear nueva ventana de resultados
        Resultados resultados = new Resultados(quizManager);
        JFrame frameResultados = new JFrame("Resultados");
        frameResultados.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameResultados.setSize(800, 500);
        frameResultados.setLocationRelativeTo(null);
        frameResultados.add(resultados.getResultadosPanel());
        frameResultados.setVisible(true);
    }
    
    public JPanel getRevisionPanel() {
        return revisionPanel;
    }
} 