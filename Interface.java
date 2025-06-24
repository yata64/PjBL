import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class Interface extends JFrame {
    private Hotel hotel = new Hotel("Hotel Central", "Av. Principal, 120");

    public Interface() {
        setTitle("Sistema de Reservas");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        carregarClientes();
        carregarFuncionarios();
        carregarQuartos();
        carregarReservas();

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

        JButton btnSalvarDados = new JButton("Salvar dados");
        btnSalvarDados.addActionListener(e -> salvarDados());
        botoes.add(btnSalvarDados);


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
            ListaClientes.append(c.get_identificar());
            c.exibir_informacoes();

        }
        JOptionPane.showMessageDialog(this, ListaClientes.toString());
    }

    private void ListarFuncionarios(){
        StringBuilder ListaFuncionarios = new StringBuilder("Funcionários: \n");

        for (Funcionario f : hotel.get_funcionarios()){
            ListaFuncionarios.append(f.get_identificar());
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

    private void adicionarReserva(){
        if(hotel.get_clientes().isEmpty()){
            JOptionPane.showMessageDialog(this, "Cadastre pelo menos um cliente antes de fazer uma reserva");
            return;
        }

        StringBuilder quartosLivres = new StringBuilder("Quartos livres: ");

        for(Quarto q : hotel.quartos_livres()){
            quartosLivres.append("Quarto ").append(q.get_numero()).append("\n");
        }

        if(hotel.quartos_livres().isEmpty()){
            JOptionPane.showMessageDialog(this, "Sem quartos disponíveis. ");
            return;
        }

        String[] nomesClientes = hotel.get_clientes().stream().map(Cliente::get_nome).toArray(String[]::new);
        String nomeSelecionado = (String) JOptionPane.showInputDialog(this, "Selecione o cliente: ", "Clientes",JOptionPane.QUESTION_MESSAGE, null, nomesClientes, nomesClientes[0]);
        
        if(nomeSelecionado == null){
            return;
        }

        Cliente clienteSelecionado = hotel.get_clientes().stream().filter(c -> c.get_nome().equals(nomeSelecionado)).findFirst().orElse(null);
        String num = JOptionPane.showInputDialog(this, quartosLivres + "\n Digite o quarto que deseja reservar: ");
        
        try{
            if(clienteSelecionado == null){
                throw new ReservaInvalida("Cliente não encontrado...");
            }

            int numero = Integer.parseInt(num);
            Quarto q = hotel.buscar_quarto(numero);

            if(q == null || q.get_ocupacao() != Quarto.ocupacao.LIVRE){
                throw new ReservaInvalida("Quarto inválido ou ocupado.");
            }

            q.set_ocupacao(Quarto.ocupacao.OCUPADO);
            Reserva r = new Reserva(hotel.get_reservas().size() + 1, q.get_tipo(), q.get_diaria(), clienteSelecionado.get_nome(), LocalDate.now(), LocalDate.now().plusDays(2));

            hotel.add_reserva(r);

            JOptionPane.showConfirmDialog(this, "Reserva feita para " + clienteSelecionado.get_nome() + "no quarto " + numero);
        }

        catch(ReservaInvalida e){
            JOptionPane.showMessageDialog(this, "ERROR " + e.getMessage());
        }

        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Número do quarto inválido...");
        }

    }
    
    private void verQuartos(){
        StringBuilder sb = new StringBuilder();

        for (Quarto.ocupacao estado : Quarto.ocupacao.values()){
            sb.append("Quartos em ").append(estado.name()).append(":\n");

            for (Quarto q : hotel.get_quartos()){
                if (q.get_ocupacao() == estado){
                    sb.append("Quarto ").append(q.get_numero()).append(" - Tipo: ").append(q.get_tipo()).append(" - Capacidade: ").append(q.get_capacidade()).append(" - Diária: R$ ").append(q.get_diaria()).append("\n");
                    
                    if(estado == Quarto.ocupacao.OCUPADO){
                        String ocupante = buscarOcupante(q.get_numero());
                        
                        if(ocupante != null){
                            sb.append(" - Ocupado por: ").append(ocupante);
                        }

                        else{
                            sb.append("Ocupado por [desconhecido]");
                        }
                    }
                }

                sb.append("\n");
            }

            sb.append("\n");
        }

        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private String buscarOcupante(int numeroQuarto){
        for(Reserva r : hotel.get_reservas()){
            if(r.get_numero() == numeroQuarto){
                return r.get_cliente();
            }
        }

        return null;
    }

    private void carregarClientes(){
        String caminho = "clientes.csv";

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

    private void carregarFuncionarios(){
        String caminho = "funcionarios.csv";

        try(BufferedReader br = new BufferedReader(new FileReader(caminho))){
            String linha;

            while ((linha = br.readLine()) != null){
                String[] partes = linha.split(";");

                if (partes.length == 4){
                    String nome = partes[0];
                    String cpf = partes[1];
                    String cargo = partes[2];
                    float salario = Float.parseFloat(partes[3]);

                    Funcionario f = new Funcionario(nome, cpf, cargo, salario);
                    hotel.add_funcionario(f);
                }
            }
        }

        catch(IOException e){
            JOptionPane.showMessageDialog(this, "Erro ao carregar funcionario..." + e.getMessage());
        }
    }

    private void carregarQuartos(){
        String caminho = "quartos.csv";

        try(BufferedReader br = new BufferedReader(new FileReader(caminho))){
            String linha;

            while((linha = br.readLine()) != null){
                String[] partes = linha.split(";");
                
                if (partes.length == 5){
                    int numero = Integer.parseInt(partes[0]);
                    String tipo = partes[1];
                    int capacidade = Integer.parseInt(partes[2]);
                    float diaria = Float.parseFloat(partes[3]);
                    Quarto.ocupacao ocupacao = Quarto.ocupacao.valueOf(partes[4]);

                    Quarto q = new Quarto(numero, tipo, capacidade, diaria, ocupacao);
                    hotel.add_quarto(q);
                }
            }
        }

        catch(IOException e){
            JOptionPane.showMessageDialog(this, "Erro ao carregar quarto..." + e.getMessage());
        }
    }

    private void carregarReservas(){
        String caminho = "reservas.csv";

        try(BufferedReader br = new BufferedReader(new FileReader(caminho))){
            String linha;

            while((linha = br.readLine()) != null){
                String[] partes = linha.split(";");

                if(partes.length == 7){
                    int numero = Integer.parseInt(partes[0]);
                    String tipo = partes[1];
                    float preco = Float.parseFloat(partes[2]);
                    String cliente = partes[4];
                    LocalDate dataEntrada = LocalDate.parse(partes[5]);
                    LocalDate dataSaida = LocalDate.parse(partes[6]);

                    Reserva r = new Reserva(numero, tipo, preco, cliente, dataEntrada, dataSaida);
                    hotel.add_reserva(r);
                }
            }
        }
        
        catch(IOException e){
            JOptionPane.showMessageDialog(this, "Erro ao carregar..." + e.getMessage());
        }
    }

    private void salvarClientes(String caminho){

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))){

            for (Cliente c : hotel.get_clientes()){
                String linha = c.get_nome() + ";" + c.get_cpf() + ";" + c.get_email();
                bw.write(linha);
                bw.newLine();
            }
        }

        catch(IOException e){
        JOptionPane.showMessageDialog(this, "Erro ao salvar clientes..." + e.getMessage());
        }
    }

    private void salvarFuncionarios(String caminho){

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))){

            for (Funcionario f : hotel.get_funcionarios()){
                String linha = f.get_nome() + ";" + f.get_cpf() + ";" + f.get_cargo() + ";" + f.get_salario();
                bw.write(linha);
                bw.newLine();
            }
        }

        catch(IOException e){
            JOptionPane.showMessageDialog(this, "Erro ao salvar Funcionarios..." + e.getMessage());
        }
    }

    private void salvarQuartos(String caminho){

        try(BufferedWriter br = new BufferedWriter(new FileWriter(caminho))){

            for (Quarto q : hotel.get_quartos()){
                String linha = q.get_numero() + ";" + q.get_tipo() + ";" + q.get_capacidade() + ";" + q.get_diaria() + ";" + q.get_ocupacao();
                br.write(linha);
                br.newLine();
            }
        }
        
        catch(IOException e){
            JOptionPane.showMessageDialog(this, "Erro ao salvar quarto..." + e.getMessage());
        }
    }

    private void salvarReservas(String caminho){

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))){

            for(Reserva r : hotel.get_reservas()){
                String linha = r.get_numero() + ";" + r.get_tipo() + ";" + r.get_preco() + ";" + r.get_cliente() + ";" + r.get_entrada() + ";" + r.get_saida();
                bw.write(linha);
                bw.newLine();
            }
        }

        catch(IOException e){
            JOptionPane.showMessageDialog(this, "Erro ao carregar reserva..." + e.getMessage());
        }
    }

    private void salvarDados(){
        salvarClientes("clientes.csv");
        salvarFuncionarios("funcionarios.csv");
        salvarQuartos("quartos.csv");
        salvarReservas("reservas.csv");

        JOptionPane.showMessageDialog(this, "Todos os dados salvos com sucesso");
    }


    public static void main(String[] args){
        new Interface();
    }
}
