package tarea2.frontend;
import javax.swing.*;

public class Alternativa {



    private JPanel alternativasPanel = new JPanel();

    public Alternativa(String alternativa, String opcion) {
        // El LayOut como BoxLayout en codigo porque en el .form no existe esa opcion.
        alternativasPanel.setLayout(new BoxLayout(alternativasPanel, BoxLayout.X_AXIS));

        JButton alternativaBoton  = new JButton(alternativa);
        //alternativaBoton.setHorizontalAlignment(SwingConstants.CENTER);
        alternativasPanel.add(new JButton(alternativa));

        JLabel opcionLabel = new JLabel(opcion);
        //opcionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        alternativasPanel.add(opcionLabel);
    };

    public JPanel getAlternativasPanel() {
        return alternativasPanel;
    }




}
