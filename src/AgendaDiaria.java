import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AgendaDiaria {
    private JTextField txtCompromisso;
    private JSpinner spnData;
    private JTable tblResultado;
    private JButton btnRemove;
    private JButton btnAdd;
    private JPanel panelMain;
    private JLabel lblSelectData;
    private JLabel lblQualseuCompromisso;
    private JSpinner spnHora;
    private JLabel lblselecioneHora;

    public AgendaDiaria() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Compromisso");
        modelo.addColumn("Data");
        modelo.addColumn("Hora");
        tblResultado.setModel(modelo);

        // Configuração do Spinner de Data
        spnData.setModel(new SpinnerDateModel());
        JSpinner.DateEditor editorData = new JSpinner.DateEditor(spnData, "dd/MM/yyyy");
        spnData.setEditor(editorData);

        // Configuração do Spinner de Hora (alterado para SpinnerNumberModel)
        spnHora.setModel(new SpinnerNumberModel(0, 0, 23, 1)); // Hora de 0 a 23
        JSpinner.DateEditor editorHora = new JSpinner.DateEditor(spnHora, "HH:mm");
        spnHora.setEditor(editorHora);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String compromisso = txtCompromisso.getText();
                if (compromisso.equals("")) {
                    JOptionPane.showMessageDialog(null, "Adicione um compromisso.");
                } else {
                    // Formatação da data e hora
                    String data = new SimpleDateFormat("dd/MM/yyyy").format(spnData.getValue());
                    String hora = new SimpleDateFormat("HH:mm").format(spnHora.getValue());

                    // Verifica se a data não está no passado
                    Date now = new Date();
                    Date dataSelecionada = (Date) spnData.getValue();

                    if (dataSelecionada.before(now)) {
                        JOptionPane.showMessageDialog(null, "A data selecionada já passou.");
                    } else {
                        // Adiciona os dados à tabela
                        modelo.addRow(new Object[]{compromisso, data, hora});
                        txtCompromisso.setText("");
                    }
                }
            }
        });

        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = tblResultado.getSelectedRow();
                if (linhaSelecionada != -1) {
                    modelo.removeRow(linhaSelecionada);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um compromisso para remover.");
                }

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Agenda Diária");
        frame.setContentPane(new AgendaDiaria().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
