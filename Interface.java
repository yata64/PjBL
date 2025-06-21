import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

public class Interface extends JFrame {
    private Hotel hotel = new Hotel("Hotel Central", "Av. Principal, 120");

    public Interface() {
        setTitle("Sistema de Reservas");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for (int i = 1; i <= 15; i++){
        hotel.add_quarto(new Quarto(i, "Simples", 2, 100, Quarto.ocupacao.LIVRE));
        }

        for (int i = 16; i <= 30; i++){
            hotel.add_quarto(new Quarto(i, "Suíte", 4, 350, Quarto.ocupacao.LIVRE));
        }

        for (int i = 31; i <= 35; i++){
            hotel.add_quarto(new Quarto(i, "Simples", 2, 100, Quarto.ocupacao.MANUTENCAO));
        }

        for (int i = 36; i <= 40; i++){
            hotel.add_quarto(new Quarto(i, "Simples", 2, 100, Quarto.ocupacao.LIMPEZA));
        }

        for (int i = 41; i <= 45; i++){
            hotel.add_quarto(new Quarto(i, "Suíte", 4, 350, Quarto.ocupacao.MANUTENCAO));
        }

        for (int i = 46; i <= 50; i++){
            hotel.add_quarto(new Quarto(i, "Suíte", 4, 350, Quarto.ocupacao.LIMPEZA));
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

        JButton btnListarClientes = new JButton("Listar Clientes");
        btnListarClientes.addActionListener(e -> ListarClientes());
        botoes.add(btnListarClientes);

        JButton btnListarFuncionarios = new JButton("Listar Funcionários");
        btnListarFuncionarios.addActionListener(e -> ListarFuncionarios());
        botoes.add(btnListarFuncionarios);  

        JButton btnMudarOcupacao = new JButton("Mudar Ocupação do Quarto");
        btnMudarOcupacao.addActionListener(e -> MudarOcupacao());
        botoes.add(btnMudarOcupacao);

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

    private void cadastrarCliente(){
        String nome = JOptionPane.showInputDialog("Nome do cliente:");
        String cpf = JOptionPane.showInputDialog("CPF:");
        String email = JOptionPane.showInputDialog("Email:");

        if (nome != null && cpf != null && email != null && !nome.isEmpty()) {
            Cliente c = new Cliente(nome, cpf, email);
            hotel.add_cliente(c); 
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso.");
        }
    }


    private void cadastrarFuncionario(){
        String nome = JOptionPane.showInputDialog("Nome do funcionário:");
        String cpf = JOptionPane.showInputDialog("CPF:");
        String cargo = JOptionPane.showInputDialog("Cargo:");
        String salarioStr = JOptionPane.showInputDialog("Salário:");

        try{
            float salario = Float.parseFloat(salarioStr);
            Funcionario f = new Funcionario(nome, cpf, cargo, salario);
            hotel.add_funcionario(f);
            JOptionPane.showMessageDialog(this, "Funcionário cadastrado.");
        } 
        catch (Exception e){
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar funcionário.");
        }
    }

    private void ListarClientes(){
        StringBuilder ListaClientes = new StringBuilder("Clientes: \n");

        for (Cliente c : hotel.get_clientes()){
            ListaClientes.append(c.get_nome()).append(" - E-mail: ").append(c.get_email()).append(" - CPF: ").append(c.get_cpf());
            c.exibir_informacoes();

        }
        JOptionPane.showMessageDialog(this, ListaClientes.toString());
    }

    private void ListarFuncionarios(){
        StringBuilder ListaFuncionarios = new StringBuilder("Funcionários: \n");

        for (Funcionario f : hotel.get_funcionarios()){
            ListaFuncionarios.append(f.get_nome()).append(" - Cargo: ").append(f.get_cargo()).append(" - Salário: ").append(f.get_salario()).append(" - CPF: ").append(f.get_cpf());
            f.exibir_informacoes();
        
        }
        JOptionPane.showMessageDialog(this, ListaFuncionarios.toString());
    }

    private void MudarOcupacao(){
        String nquarto = JOptionPane.showInputDialog(this, "Digite o número do quarto: ");
        if (nquarto == null){
            return;
        }

        try{
            int numero = Integer.parseInt(nquarto);
            Quarto quarto = hotel.buscar_quarto(numero);

            if (quarto == null){
                JOptionPane.showMessageDialog(this, "Quarto não encontrado... ");
                return;
            }
        

            Quarto.ocupacao[] opcoes = Quarto.ocupacao.values();
            Quarto.ocupacao novaOcupacao = (Quarto.ocupacao) JOptionPane.showInputDialog(this,"Escolha a nova ocupação para o quarto " + numero + ":","Alterar Ocupação",JOptionPane.QUESTION_MESSAGE,null,opcoes,quarto.get_ocupacao());
        
            if (novaOcupacao != null){
                quarto.set_ocupacao(novaOcupacao);
                JOptionPane.showMessageDialog(this, "Alterado com sucesso! ");
            }
        }

        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Número inválido ");
        }        
    }

    private void adicionarReserva() {
        if (hotel.get_clientes().isEmpty()){
            JOptionPane.showMessageDialog(this, "Cadastre pelo menos um cliente antes de fazer a reserva.");
            return;
    }

        StringBuilder quartosLivres = new StringBuilder("Quartos Livres:\n");
        for (Quarto q : hotel.quartos_livres()){
            quartosLivres.append("Quarto ").append(q.get_numero()).append("\n");
        }

        if (quartosLivres.length() == 0){
            JOptionPane.showMessageDialog(this, "Sem quartos disponíveis.");
            return;
        }

        String[] nomesClientes = hotel.get_clientes().stream().map(Cliente::get_nome).toArray(String[]::new);
        String nomeSelecionado = (String)JOptionPane.showInputDialog(this, "Selecione o cliente:","Clientes", JOptionPane.QUESTION_MESSAGE, null, nomesClientes, nomesClientes[0]);

        if (nomeSelecionado == null) 
            return;

            Cliente clienteSelecionado = hotel.get_clientes().stream().filter(c -> c.get_nome().equals(nomeSelecionado)).findFirst().orElse(null);

        if (clienteSelecionado == null){
            JOptionPane.showMessageDialog(this, "Cliente não encontrado.");
            return;
        }

        String numStr = JOptionPane.showInputDialog(this, quartosLivres + "\nDigite o número do quarto para reservar:");
        try{
            int numero = Integer.parseInt(numStr);
            Quarto q = hotel.buscar_quarto(numero);
            if (q != null && q.get_ocupacao() == Quarto.ocupacao.LIVRE){
                q.set_ocupacao(Quarto.ocupacao.OCUPADO);
                Reserva r = new Reserva(hotel.get_reservas().size() + 1, q.get_tipo(), q.get_diaria(),clienteSelecionado.get_nome(), LocalDate.now(), LocalDate.now().plusDays(2));
                hotel.add_reserva(r);
                JOptionPane.showMessageDialog(this, "Reserva feita para " + clienteSelecionado.get_nome() + " no quarto " + numero);
            }

            else{
                JOptionPane.showMessageDialog(this, "Quarto inválido ou já ocupado.");
            }
        } 

        catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Entrada inválida.");
        }
    }


    private void verQuartos(){
        StringBuilder sb = new StringBuilder();

        for (Quarto.ocupacao estado : Quarto.ocupacao.values()){
            sb.append("Quartos em ").append(estado.name()).append(":\n");
            for (Quarto q : hotel.get_quartos()){
                if (q.get_ocupacao() == estado){
                    sb.append("Quarto ").append(q.get_numero()).append(" - Tipo: ").append(q.get_tipo()).append(" - Capacidade: ").append(q.get_capacidade()).append(" - Diária: R$ ").append(q.get_diaria()).append("\n");
                }
            }
            sb.append("\n");
        }

        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void carregarClientes(String caminho){
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))){
            String linha;

            while ((linha = br.readLine()) != null){
                String[] partes = linha.split(";");

                if (partes.length == 3){
                    String nome = partes[0];
                    String cpf = partes[1];
                    String email = partes[2];

                    Cliente c = new Cliente(nome, cpf, email);
                    hotel.add_cliente(c);
                }
            }
        }

        catch(IOException e){
            JOptionPane.showMessageDialog(this, "Erro ao carregar clientes..." + e.getMessage());
        }
    }

    public static void main(String[] args){
        new Interface();
    }
}
