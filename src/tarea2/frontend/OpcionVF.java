package tarea2.frontend;
import javax.swing.*;
import java.awt.*;

public class OpcionVF {

    private JPanel OpcionVFPanel = new JPanel();
    //private JPanel VerdaderoPanel = new JPanel();
    private JPanel FalsoPanel = new JPanel();

    public OpcionVF() {
        // El LayOut como BoxLayout en codigo porque en el .form no existe esa opcion.
        OpcionVFPanel.setLayout(new BoxLayout(OpcionVFPanel, BoxLayout.Y_AXIS));
        //VerdaderoPanel.setLayout(new BoxLayout(VerdaderoPanel, BoxLayout.X_AXIS));
        FalsoPanel.setLayout(new BoxLayout(FalsoPanel, BoxLayout.X_AXIS));

        JButton verdaderoButton  = new JButton("V");
        JButton falsoButton = new JButton("F");
        JTextField justificacionTextField = new JTextField(10);
        Dimension minSize = new Dimension(420, 30);
        justificacionTextField.setMaximumSize(minSize);
        justificacionTextField.setPreferredSize(minSize);

        FalsoPanel.add(falsoButton);
        FalsoPanel.add(justificacionTextField);

        OpcionVFPanel.add(verdaderoButton);
        OpcionVFPanel.add(FalsoPanel);


    };

    public JPanel getOpcionVFPanel() {
        return OpcionVFPanel;
    }




}
