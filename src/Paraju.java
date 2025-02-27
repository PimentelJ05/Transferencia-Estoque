import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Paraju{
    private static int tijolosKS = 100, tijolosParaju = 50;
    private static JFrame frame;
    private static JTextArea outputArea;

    public static void main(String[] args) {
        frame = new JFrame("Gerenciamento de Estoque");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));

        JButton btnEntrada = new JButton("Entrada de Estoque");
        JButton btnSaida = new JButton("Saída de Estoque");
        JButton btnTransferencia = new JButton("Transferência de Estoque");
        JButton btnConsulta = new JButton("Consulta de Estoque");
        JButton btnFinalizar = new JButton("Finalizar");

        outputArea = new JTextArea();
        outputArea.setEditable(false);

        panel.add(btnEntrada);
        panel.add(btnSaida);
        panel.add(btnTransferencia);
        panel.add(btnConsulta);
        panel.add(btnFinalizar);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        btnEntrada.addActionListener(e -> entradaEstoque());
        btnSaida.addActionListener(e -> saidaEstoque());
        btnTransferencia.addActionListener(e -> transferenciaEstoque());
        btnConsulta.addActionListener(e -> consultaEstoque());
        btnFinalizar.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
    }

    private static void entradaEstoque() {
        String[] opcoes = {"Tijolos KS", "Tijolos Paraju"};
        int empresa = JOptionPane.showOptionDialog(frame, "Qual empresa?", "Entrada de Estoque",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

        if (empresa == -1) return;

        String quantidadeStr = JOptionPane.showInputDialog("Digite a quantidade de entrada:");
        if (quantidadeStr == null) return;

        int quantidade;
        try {
            quantidade = Integer.parseInt(quantidadeStr);
            if (quantidade <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Quantidade inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (empresa == 0) tijolosKS += quantidade;
        else tijolosParaju += quantidade;

        consultaEstoque();
    }

    private static void saidaEstoque() {
        String[] opcoes = {"Tijolos KS", "Tijolos Paraju"};
        int empresa = JOptionPane.showOptionDialog(frame, "Qual empresa?", "Saída de Estoque",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

        if (empresa == -1) return;

        String quantidadeStr = JOptionPane.showInputDialog("Digite a quantidade de saída:");
        if (quantidadeStr == null) return;

        int quantidade;
        try {
            quantidade = Integer.parseInt(quantidadeStr);
            if (quantidade <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Quantidade inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if ((empresa == 0 && tijolosKS < quantidade) || (empresa == 1 && tijolosParaju < quantidade)) {
            JOptionPane.showMessageDialog(frame, "Estoque insuficiente!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (empresa == 0) tijolosKS -= quantidade;
        else tijolosParaju -= quantidade;

        consultaEstoque();
    }

    private static void transferenciaEstoque() {
        String[] opcoes = {"Tijolos KS", "Tijolos Paraju"};
        int origem = JOptionPane.showOptionDialog(frame, "De qual empresa?", "Transferência de Estoque",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

        if (origem == -1) return;

        int destino = (origem == 0) ? 1 : 0;

        String quantidadeStr = JOptionPane.showInputDialog("Digite a quantidade para transferir:");
        if (quantidadeStr == null) return;

        int quantidade;
        try {
            quantidade = Integer.parseInt(quantidadeStr);
            if (quantidade <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Quantidade inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if ((origem == 0 && tijolosKS < quantidade) || (origem == 1 && tijolosParaju < quantidade)) {
            JOptionPane.showMessageDialog(frame, "Estoque insuficiente!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (origem == 0) {
            tijolosKS -= quantidade;
            tijolosParaju += quantidade;
        } else {
            tijolosParaju -= quantidade;
            tijolosKS += quantidade;
        }

        consultaEstoque();
    }

    private static void consultaEstoque() {
        outputArea.setText("Estoque Atual:\nTijolos KS: " + tijolosKS + " unidades\nTijolos Paraju: " + tijolosParaju + " unidades");
    }
}
