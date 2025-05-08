package vistoEnClases.frontend;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CopiarListener implements ActionListener {
    private JTextField texto;
    private JLabel textoCopiado;

    public CopiarListener(JTextField texto, JLabel textoCopiado) {
        this.texto = texto;
        this.textoCopiado = textoCopiado;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String texto = this.texto.getText();
        this.textoCopiado.setText(texto);
    }
}
