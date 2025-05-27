package tarea2.frontend;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Alternativa {



    private JPanel alternativasPanel = new JPanel();
    private JButton alternativaBoton  = new JButton();
    JLabel opcionLabel = new JLabel();
    private int indiceRespuesta;

    public Alternativa(String alternativa, String opcion, int indiceRespuesta) {
        // El LayOut como BoxLayout en codigo porque en el .form no existe esa opcion.
        this.alternativasPanel.setLayout(new BoxLayout(alternativasPanel, BoxLayout.X_AXIS));

        this.indiceRespuesta = indiceRespuesta;

        // Boton
        this.alternativaBoton.setText(alternativa);
        this.alternativasPanel.add(alternativaBoton);

        // Label Alternativa
        this.opcionLabel.setText(opcion);
        this.alternativasPanel.add(opcionLabel);


//        this.alternativaBoton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e){
//            }
//        });


    };

    public JPanel getAlternativasPanel() {
        return alternativasPanel;
    }

    public JButton getAlternativaBoton() {
        return alternativaBoton;
    }

    public int getIndiceRespuesta() {
        return indiceRespuesta;
    }




}
