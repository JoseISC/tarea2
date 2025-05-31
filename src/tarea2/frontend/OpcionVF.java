package tarea2.frontend;
import javax.swing.*;
import java.awt.*;

public class OpcionVF {

    private JPanel OpcionVFPanel = new JPanel();
    //private JPanel VerdaderoPanel = new JPanel();
    private JPanel FalsoPanel = new JPanel();
    private JPanel VerdederoPanel = new JPanel();
    private JButton falsoButton = new JButton("F");
    private JTextField justificacionTextField = new JTextField(10);

    public OpcionVF() {
        // El LayOut como BoxLayout en codigo porque en el .form no existe esa opcion.
        OpcionVFPanel.setLayout(new BoxLayout(OpcionVFPanel, BoxLayout.Y_AXIS));
        //VerdaderoPanel.setLayout(new BoxLayout(VerdaderoPanel, BoxLayout.X_AXIS));
        FalsoPanel.setLayout(new BoxLayout(FalsoPanel, BoxLayout.X_AXIS));
        VerdederoPanel.setLayout(new BoxLayout(VerdederoPanel, BoxLayout.X_AXIS));

        JButton verdaderoButton  = new JButton("V");
        Dimension minSize = new Dimension(420, 30);
        justificacionTextField.setMaximumSize(minSize);
        justificacionTextField.setPreferredSize(minSize);
        justificacionTextField.setEnabled(false);

        FalsoPanel.add(this.falsoButton);
        FalsoPanel.add(this.justificacionTextField);
        //VerdederoPanel.add(verdaderoButton);

        //OpcionVFPanel.add(verdaderoButton);
        OpcionVFPanel.add(FalsoPanel);
        //OpcionVFPanel.add(VerdederoPanel);


    };

    public JPanel getOpcionVFPanel() {
        return OpcionVFPanel;
    }

    public JButton getFalsoButton() {
        return falsoButton;
    }

    public JTextField getJustificacionTextField() {
        return justificacionTextField;
    }





}
