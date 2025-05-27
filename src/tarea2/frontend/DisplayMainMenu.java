package tarea2.frontend;

import javax.swing.*;
import java.awt.*;
import tarea2.backend.QuizManager;

public class DisplayMainMenu extends JFrame {
    private QuizManager quizManager;
    private JPanel mainPanel;
    private JButton btnLoadQuestions;
    private JButton btnStartQuiz;
    private JButton btnExit;

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
        btnStartQuiz.addActionListener(e -> startQuiz());
        mainPanel.add(btnStartQuiz, gbc);

        gbc.gridy++;
        btnExit = new JButton("Salir");
        btnExit.addActionListener(e -> dispose());
        mainPanel.add(btnExit, gbc);

        add(mainPanel);
    }

    private void loadQuestions() {
        try {
            // Create the PreguntasDisplay with the quizManager
            PreguntasDisplay preguntasDisplay = new PreguntasDisplay(quizManager);

            // Create a new frame to display it
            JFrame frame = new JFrame("Preguntas");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);

            // Add the component to the frame
            // Assuming getAlternativasPanel() returns the main panel
            frame.add(preguntasDisplay.getAlternativasPanel());

            // Dispose the current window
            dispose();

            // Make the new frame visible
            frame.setVisible(true);
        } catch (Exception ex) {
            handleError("Error al cargar las preguntas", ex);
        }
    }


    private void startQuiz() {
        try {
            // Create the PruebaPreguntas with the existing quizManager
            PruebaPreguntas prueba = new PruebaPreguntas(quizManager);

            // Create a new frame to display the quiz
            JFrame frame = new JFrame("Quiz");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);

            // Add the component to the frame
            frame.add(prueba.getPruebaPanel());

            // Dispose the current window
            dispose();

            // Make the new frame visible
            frame.setVisible(true);

            // Initialize the first question if available
            if (!quizManager.getPreguntas().isEmpty()) {
                prueba.mostrarPregunta(quizManager.getPreguntaActual());
            }
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