package tarea2.frontend;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class OpcionFalso {

    private JPanel OpcionFalsoPanel = new JPanel();
    private JButton falsoButton = new JButton("F");
    private JLabel falsoLabel = new JLabel("Falso");
    private JTextField justificacionTextField = new JTextField(10);
    private JPanel panelTest = new JPanel();

    public OpcionFalso() {
        // El LayOut como BoxLayout en codigo porque en el .form no existe esa opcion.
        OpcionFalsoPanel.setLayout(new BoxLayout(OpcionFalsoPanel, BoxLayout.X_AXIS));
        panelTest.setLayout(new BorderLayout());
        Dimension horizontalConstraint = OpcionFalsoPanel.getPreferredSize();
        horizontalConstraint.height = 25;
        OpcionFalsoPanel.setPreferredSize(horizontalConstraint);

        Dimension minSize = new Dimension(420, 30);

        // Boton
        OpcionFalsoPanel.add(this.falsoButton);

        int espacio = 10;
        this.OpcionFalsoPanel.add(new Box.Filler(new Dimension(espacio, espacio), new Dimension(espacio, espacio), new Dimension(espacio, espacio)));

        // Label
        OpcionFalsoPanel.add(this.falsoLabel);

        this.OpcionFalsoPanel.add(new Box.Filler(new Dimension(espacio, espacio), new Dimension(espacio, espacio), new Dimension(espacio, espacio)));

        // JTextField
        justificacionTextField.setMaximumSize(minSize);
        justificacionTextField.setPreferredSize(minSize);
        justificacionTextField.setEnabled(false);
        OpcionFalsoPanel.add(this.justificacionTextField);
//        Border current = OpcionFalsoPanel.getBorder();
//        Border empty = new EmptyBorder(5, 5, 5, 5);
//        if (current == null){
//            OpcionFalsoPanel.setBorder(empty);
//        }
//        else {
//            OpcionFalsoPanel.setBorder(new CompoundBorder(empty, current));
//        }

        panelTest.add(OpcionFalsoPanel, BorderLayout.PAGE_START);



        //myPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));






    };

    public JPanel getOpcionVFPanel() {
        return OpcionFalsoPanel;
    }

    public JButton getFalsoButton() {
        return falsoButton;
    }

    public JTextField getJustificacionTextField() {
        return justificacionTextField;
    }

    public JPanel getPanelTest() {
        return panelTest;
    }





}
