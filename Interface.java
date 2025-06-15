import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Interface extends JFrame {
    private Hotel hotel = new Hotel("Hotel Central", "Av. Principal, 120");

    public Interface() {
        setTitle("Sistema de Reservas");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        //Cadastrar Cliente
        JButton botaoCliente = new JButton("Cadastrar Cliente");
        botaoCliente.setBounds(150, 30, 200, 30);
        botaoCliente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Cliente cliente = new Cliente("João", "09516452930", "joaomarioys@gmail.com");
                cliente.exibir_informacoes();
                JOptionPane.showMessageDialog(null, "Cliente criado com sucesso!");
            }
        });
        add(botaoCliente);

        //Cadastrar Funcionário
        JButton botaoFuncionario = new JButton("Cadastrar Funcionário");
        botaoFuncionario.setBounds(150, 70, 200, 30);
        botaoFuncionario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Funcionario func = new Funcionario("Maria", "12345678900", "Recepcionista", 2500);
                hotel.add_funcionario(func);
                func.exibir_informacoes();
                JOptionPane.showMessageDialog(null, "Funcionário cadastrado com sucesso!");
            }
        });
        add(botaoFuncionario);

        //Ver Quartos Livres
        JButton botaoQuartos = new JButton("Ver Quartos Livres");
        botaoQuartos.setBounds(150, 110, 200, 30);
        botaoQuartos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<Quarto> livres = hotel.quartos_livres();
                if (livres.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nenhum quarto livre.");
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (Quarto q : livres) {
                        sb.append("Quarto ").append(q.get_numero())
                          .append(" - ").append(q.get_tipo())
                          .append(" (Capacidade: ").append(q.get_capacidade())
                          .append(")\n");
                    }
                    JOptionPane.showMessageDialog(null, sb.toString());
                }
            }
        });
        add(botaoQuartos);

        //Ver Reservas
        JButton botaoReservas = new JButton("Ver Reservas");
        botaoReservas.setBounds(150, 150, 200, 30);
        botaoReservas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<Reserva> reservas = hotel.get_reservas();
                if (reservas.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nenhuma reserva cadastrada.");
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (Reserva r : reservas) {
                        sb.append("Reserva Quarto ").append(r.get_numero())
                          .append(" - Cliente: ").append(r.get_cliente())
                          .append(" (Entrada: ").append(r.get_entrada())
                          .append(" | Saída: ").append(r.get_saida())
                          .append(")\n");
                    }
                    JOptionPane.showMessageDialog(null, sb.toString());
                }
            }
        });
        add(botaoReservas);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Interface();
    }
}
