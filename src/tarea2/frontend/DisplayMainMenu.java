package tarea2.frontend;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

import tarea2.backend.QuizManager;

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

            quizManager.cargarPreguntas(quizManager.leerPreguntasJson());
            cantidadPreguntas = String.valueOf(quizManager.getPreguntas().size());
            duracionEstimada = String.valueOf(quizManager.getDuracionEnMinutos());
            informacionPrueba.setText("Cantidad de preguntas: " + cantidadPreguntas + " | Duración estimada: " + duracionEstimada + " minutos");
            informationPanel.setBackground(colorPreguntasCargadas);
            btnStartQuiz.setEnabled(true);
            containerPanel.revalidate();
            containerPanel.repaint();



            JOptionPane.showMessageDialog(this,
                    "Preguntas cargadas con éxito",
                    "Éxito!",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (FileNotFoundException ex) {
            handleError("Error al cargar las preguntas", ex);
        }
    }


    private void startQuiz() {
        try {
            // Create the PruebaPreguntas with the existing quizManager
            PruebaPreguntas prueba = new PruebaPreguntas(quizManager);
            if (quizManager.getPreguntas().isEmpty()){
                handleError("Error al iniciar el quiz", new Exception("No hay preguntas para mostrar, asegúrese que cargó las preguntas y/o que el .json no este vacío"));
                return;
            }
           //quizManager.iniciarPrueba();
            // Create a new frame to display the quiz
            JFrame frame = new JFrame("Quiz");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 400);
            frame.setLocationRelativeTo(null);

            // Add the component to the frame
            frame.add(prueba.getPruebaPanel());

            // Dispose the current window
            dispose();

            // Make the new frame visible
            frame.setVisible(true);

            // Initialize the first question
            prueba.mostrarPregunta(quizManager.getPreguntaActual());

        } catch (Exception ex) {
            handleError("Error al iniciar el quiz", ex);
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