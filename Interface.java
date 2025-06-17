import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class Interface extends JFrame {
    private Hotel hotel = new Hotel("Hotel Central", "Av. Principal, 120");

    public Interface() {
        setTitle("Sistema de Reservas");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Criar 50 quartos livres
        for (int i = 1; i <= 50; i++) {
            hotel.add_quarto(new Quarto(i, "Simples", 2, 100, Quarto.ocupacao.LIVRE));
        }

        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel botoes = new JPanel(new GridLayout(0, 1, 10, 10));

        JButton btnCadastrarCliente = new JButton("Cadastrar Cliente");
        btnCadastrarCliente.addActionListener(e -> cadastrarCliente());
        botoes.add(btnCadastrarCliente);

        JButton btnCadastrarFuncionario = new JButton("Cadastrar Funcionário");
        btnCadastrarFuncionario.addActionListener(e -> cadastrarFuncionario());
        botoes.add(btnCadastrarFuncionario);

        JButton btnListarTodos = new JButton("Listar Clientes e Funcionários");
        btnListarTodos.addActionListener(e -> listarPessoas());
        botoes.add(btnListarTodos);

        JButton btnAdicionarReserva = new JButton("Adicionar Reserva");
        btnAdicionarReserva.addActionListener(e -> adicionarReserva());
        botoes.add(btnAdicionarReserva);

        JButton btnVerQuartos = new JButton("Ver Quartos e Ocupação");
        btnVerQuartos.addActionListener(e -> verQuartos());
        botoes.add(btnVerQuartos);

        JButton btnSair = new JButton("Sair");
        btnSair.addActionListener(e -> System.exit(0));
        botoes.add(btnSair);

        painel.add(botoes, gbc);
        add(painel);
        setVisible(true);
    }

    private void cadastrarCliente() {
        String nome = JOptionPane.showInputDialog("Nome do cliente:");
        String cpf = JOptionPane.showInputDialog("CPF:");
        String email = JOptionPane.showInputDialog("Email:");
        if (nome != null && cpf != null && email != null && !nome.isEmpty()) {
            Cliente c = new Cliente(nome, cpf, email);
            hotel.add_cliente(c); // ✅ Adiciona o cliente ao hotel
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso.");
        }
    }


    private void cadastrarFuncionario() {
        String nome = JOptionPane.showInputDialog("Nome do funcionário:");
        String cpf = JOptionPane.showInputDialog("CPF:");
        String cargo = JOptionPane.showInputDialog("Cargo:");
        String salarioStr = JOptionPane.showInputDialog("Salário:");
        try {
            float salario = Float.parseFloat(salarioStr);
            Funcionario f = new Funcionario(nome, cpf, cargo, salario);
            hotel.add_funcionario(f);
            JOptionPane.showMessageDialog(this, "Funcionário cadastrado.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar funcionário.");
        }
    }

    private void listarPessoas() {
        StringBuilder sb = new StringBuilder();
        sb.append("Funcionários:\n");
        for (Funcionario f : hotel.get_funcionarios()){
            sb.append(f.get_nome()).append(" - ").append(f.get_cargo()).append("\n");
        }
        sb.append("Clientes: \n");
        for (Cliente f : hotel.get_clientes()){
            sb.append(f.get_nome()).append(" - ").append(f.get_cpf()).append(" - ").append(f.get_email()).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }
    private void adicionarReserva() {
    // Verificar se há clientes cadastrados
        if (hotel.get_clientes().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cadastre pelo menos um cliente antes de fazer a reserva.");
            return;
    }

    // Verificar se há quartos livres
        StringBuilder quartosLivres = new StringBuilder("Quartos Livres:\n");
        for (Quarto q : hotel.quartos_livres()) {
            quartosLivres.append("Quarto ").append(q.get_numero()).append("\n");
        }

        if (quartosLivres.length() == 0) {
            JOptionPane.showMessageDialog(this, "Sem quartos disponíveis.");
            return;
        }

    // Selecionar cliente
        String[] nomesClientes = hotel.get_clientes().stream().map(Cliente::get_nome).toArray(String[]::new);
        String nomeSelecionado = (String) JOptionPane.showInputDialog(this, "Selecione o cliente:","Clientes", JOptionPane.QUESTION_MESSAGE, null, nomesClientes, nomesClientes[0]);

        if (nomeSelecionado == null) 
            return;

            Cliente clienteSelecionado = hotel.get_clientes().stream().filter(c -> c.get_nome().equals(nomeSelecionado)).findFirst().orElse(null);

        if (clienteSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Cliente não encontrado.");
            return;
        }

    // Selecionar quarto
        String numStr = JOptionPane.showInputDialog(this, quartosLivres + "\nDigite o número do quarto para reservar:");
        try {
            int numero = Integer.parseInt(numStr);
            Quarto q = hotel.buscar_quarto(numero);
            if (q != null && q.get_ocupacao() == Quarto.ocupacao.LIVRE) {
                q.set_ocupacao(Quarto.ocupacao.OCUPADO);
                Reserva r = new Reserva(hotel.get_reservas().size() + 1, q.get_tipo(), q.get_diaria(),
                    clienteSelecionado.get_nome(), LocalDate.now(), LocalDate.now().plusDays(2));
                hotel.add_reserva(r);
                JOptionPane.showMessageDialog(this, "Reserva feita para " + clienteSelecionado.get_nome() + " no quarto " + numero);
            } 
            else{
                JOptionPane.showMessageDialog(this, "Quarto inválido ou já ocupado.");
            }
        } 
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Entrada inválida.");
        }
    }


    private void verQuartos() {
        StringBuilder sb = new StringBuilder("Quartos Ocupados:\n");
        for (Quarto q : hotel.get_quartos()) {
            if (q.get_ocupacao() == Quarto.ocupacao.OCUPADO) {
                sb.append("Quarto ").append(q.get_numero()).append(" - ").append(q.get_tipo()).append("\n");
            }
        }

        sb.append("\nQuartos Livres:\n");
        for (Quarto q : hotel.get_quartos()) {
            if (q.get_ocupacao() == Quarto.ocupacao.LIVRE) {
                sb.append("Quarto ").append(q.get_numero()).append(" - ").append(q.get_tipo()).append("\n");
            }
        }

        JOptionPane.showMessageDialog(this, sb.toString());
    }

    public static void main(String[] args) {
        new Interface();
    }
}
