import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Interface extends JFrame {
    private Hotel hotel = new Hotel("Hotel Central", "Av. Principal, 120");
    private ArrayList<Cliente> clientes = new ArrayList<>();
    private ArrayList<Funcionario> funcionarios = new ArrayList<>();

    public Interface() {
        setTitle("Sistema de Reservas de Hotel");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JButton btnCliente = new JButton("Cadastrar Cliente");
        btnCliente.setBounds(20, 20, 200, 30);
        btnCliente.addActionListener(e -> cadastrarCliente());
        add(btnCliente);

        JButton btnFuncionario = new JButton("Cadastrar Funcionário");
        btnFuncionario.setBounds(20, 60, 200, 30);
        btnFuncionario.addActionListener(e -> cadastrarFuncionario());
        add(btnFuncionario);

        JButton btnListarClientes = new JButton("Listar Clientes");
        btnListarClientes.setBounds(20, 100, 200, 30);
        btnListarClientes.addActionListener(e -> listarClientes());
        add(btnListarClientes);

        JButton btnListarFuncionarios = new JButton("Listar Funcionários");
        btnListarFuncionarios.setBounds(20, 140, 200, 30);
        btnListarFuncionarios.addActionListener(e -> listarFuncionarios());
        add(btnListarFuncionarios);

        JButton btnReservar = new JButton("Adicionar Reserva");
        btnReservar.setBounds(20, 180, 200, 30);
        btnReservar.addActionListener(e -> adicionarReserva());
        add(btnReservar);

        JButton btnQuartos = new JButton("Ver Ocupação dos Quartos");
        btnQuartos.setBounds(20, 220, 200, 30);
        btnQuartos.addActionListener(e -> mostrarOcupacao());
        add(btnQuartos);

        setVisible(true);
    }

    private void cadastrarCliente() {
        String nome = JOptionPane.showInputDialog("Nome do cliente:");
        String cpf = JOptionPane.showInputDialog("CPF:");
        String email = JOptionPane.showInputDialog("Email:");
        Cliente c = new Cliente(nome, cpf, email);
        clientes.add(c);
        JOptionPane.showMessageDialog(this, "Cliente cadastrado!");
    }

    private void cadastrarFuncionario() {
        String nome = JOptionPane.showInputDialog("Nome do funcionário:");
        String cpf = JOptionPane.showInputDialog("CPF:");
        String cargo = JOptionPane.showInputDialog("Cargo:");
        float salario = Float.parseFloat(JOptionPane.showInputDialog("Salário:"));
        Funcionario f = new Funcionario(nome, cpf, cargo, salario);
        funcionarios.add(f);
        hotel.add_funcionario(f);
        JOptionPane.showMessageDialog(this, "Funcionário cadastrado!");
    }

    private void listarClientes() {
        StringBuilder sb = new StringBuilder();
        for (Cliente c : clientes) {
            sb.append(c.get_nome()).append(" - ").append(c.get_email()).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.length() > 0 ? sb.toString() : "Nenhum cliente cadastrado.");
    }

    private void listarFuncionarios() {
        StringBuilder sb = new StringBuilder();
        for (Funcionario f : funcionarios) {
            sb.append(f.get_nome()).append(" - ").append(f.get_cargo()).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.length() > 0 ? sb.toString() : "Nenhum funcionário cadastrado.");
    }

    private void adicionarReserva() {
        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum cliente cadastrado.");
            return;
        }
        try {
            int num = Integer.parseInt(JOptionPane.showInputDialog("Número da reserva:"));
            String tipo = JOptionPane.showInputDialog("Tipo da reserva:");
            float preco = Float.parseFloat(JOptionPane.showInputDialog("Preço da reserva:"));
            Cliente cliente = clientes.get(0); // exemplo usa o primeiro cliente
            LocalDate entrada = LocalDate.now();
            LocalDate saida = entrada.plusDays(3);
            Reserva r = new Reserva(num, tipo, preco, cliente.get_nome(), entrada, saida);
            hotel.add_reserva(r);
            JOptionPane.showMessageDialog(this, "Reserva adicionada!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar reserva.");
        }
    }

    private void mostrarOcupacao() {
        StringBuilder sb = new StringBuilder();
        for (Quarto q : hotel.get_quartos()) {
            sb.append("Quarto ").append(q.get_numero())
              .append(" - Ocupação: ").append(q.get_ocupacao())
              .append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.length() > 0 ? sb.toString() : "Nenhum quarto carregado.");
    }

    public static void main(String[] args) {
        new Interface();
    }
}
