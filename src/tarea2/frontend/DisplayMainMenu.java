package tarea2.frontend;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.List;

import tarea2.backend.QuizManager;
import tarea2.backend.Pregunta;

public class DisplayMainMenu extends JFrame {
    private QuizManager quizManager;
    private JPanel mainPanel;
    private JButton btnLoadQuestions;
    private JButton btnStartQuiz;
    private JButton btnExit;
    private JPanel informationPanel;
    private JLabel informacionPrueba;
    private JPanel containerPanel;
    private String cantidadPreguntas = "N/A";
    private String duracionEstimada = "N/A";

    Color colorNullPreguntas = new Color(255, 196, 196, 165);
    Color colorPreguntasCargadas = new Color(196, 255, 213, 165);

    public DisplayMainMenu() {
        quizManager = new QuizManager();
        setTitle("Quiz Application - Menú Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        initComponents();
        btnStartQuiz.setEnabled(false);
        setVisible(true);
    }

    private void initComponents() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        btnLoadQuestions = new JButton("Cargar Preguntas");
        btnLoadQuestions.addActionListener(e -> loadQuestions());
        mainPanel.add(btnLoadQuestions, gbc);

        gbc.gridy++;
        btnStartQuiz = new JButton("Iniciar Quiz");
        btnStartQuiz.setEnabled(false);
        btnStartQuiz.addActionListener(e -> startQuiz());
        mainPanel.add(btnStartQuiz, gbc);

        gbc.gridy++;
        btnExit = new JButton("Salir");
        btnExit.addActionListener(e -> dispose());
        mainPanel.add(btnExit, gbc);

        gbc.gridy++;
        informationPanel = new JPanel();
        informationPanel.setLayout(new FlowLayout());
        informacionPrueba = new JLabel("Cantidad de preguntas: " + cantidadPreguntas + " | Duración estimada: " + duracionEstimada);
        informationPanel.add(informacionPrueba);

        informationPanel.setBackground(colorNullPreguntas);

        containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));

        containerPanel.add(mainPanel, gbc);
        containerPanel.add(informationPanel);

        add(containerPanel);
    }

    private void loadQuestions() {
        try {
            // Deshabilitar el botón mientras se cargan las preguntas
            btnLoadQuestions.setEnabled(false);
            btnLoadQuestions.setText("Cargando...");

            // Cargar las preguntas
            List<Pregunta> preguntasCargadas = quizManager.leerPreguntasJson();
            if (preguntasCargadas.isEmpty()) {
                throw new Exception("El archivo de preguntas está vacío");
            }
            quizManager.cargarPreguntas(preguntasCargadas);
            
            // Actualizar la información
            cantidadPreguntas = String.valueOf(quizManager.getPreguntas().size());
            duracionEstimada = String.valueOf(quizManager.getDuracionEnMinutos());
            informacionPrueba.setText("Cantidad de preguntas: " + cantidadPreguntas + " | Duración estimada: " + duracionEstimada + " minutos");
            informationPanel.setBackground(colorPreguntasCargadas);
            btnStartQuiz.setEnabled(true);
            
            // Actualizar la interfaz
            containerPanel.revalidate();
            containerPanel.repaint();

            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(this,
                    "Se cargaron " + cantidadPreguntas + " preguntas.\nDuración estimada: " + duracionEstimada + " minutos",
                    "Preguntas cargadas con éxito",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            handleError("Error al cargar las preguntas", ex);
            // Restaurar el estado del botón en caso de error
            btnLoadQuestions.setEnabled(true);
            btnLoadQuestions.setText("Cargar Preguntas");
            // Asegurarse de que el botón de iniciar quiz esté deshabilitado
            btnStartQuiz.setEnabled(false);
            // Restaurar el color del panel de información
            informationPanel.setBackground(colorNullPreguntas);
            informacionPrueba.setText("Cantidad de preguntas: N/A | Duración estimada: N/A");
        }
    }

    private void startQuiz() {
        try {
            // Validar que hay preguntas cargadas
            if (quizManager.getPreguntas().isEmpty()) {
                handleError("Error al iniciar el quiz", 
                    new Exception("No hay preguntas para mostrar. Por favor, cargue las preguntas primero."));
                return;
            }

            // Deshabilitar botones durante la transición
            btnStartQuiz.setEnabled(false);
            btnLoadQuestions.setEnabled(false);

            // Crear la ventana del quiz
            JFrame frame = new JFrame("Quiz");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 400);
            frame.setLocationRelativeTo(null);

            // Crear e inicializar el panel de preguntas
            PruebaPreguntas prueba = new PruebaPreguntas(quizManager);
            frame.add(prueba.getPruebaPanel());

            // Cerrar la ventana actual
            dispose();

            // Mostrar la nueva ventana
            frame.setVisible(true);

            // Mostrar la primera pregunta
            prueba.mostrarPregunta(quizManager.getPreguntaActual());

        } catch (Exception ex) {
            handleError("Error al iniciar el quiz", ex);
            // Restaurar el estado de los botones en caso de error
            btnStartQuiz.setEnabled(true);
            btnLoadQuestions.setEnabled(true);
        }
    }

    private void handleError(String message, Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, 
            message + ": " + ex.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    }

    public static void showMenu() {
        SwingUtilities.invokeLater(() -> new DisplayMainMenu());
    }
}