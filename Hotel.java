    import java.util.ArrayList;

    public class Hotel {
        private String nome;
        private String endereco;
        private ArrayList<Reserva> reservas;
        private ArrayList<Quarto> quartos;
        private ArrayList<Funcionario> funcionarios;
        private ArrayList<Cliente> clientes; 

        public Hotel(String nome, String endereco) {
            this.nome = nome;
            this.endereco = endereco;
            this.reservas = new ArrayList<>();
            this.quartos = new ArrayList<>();
            this.funcionarios = new ArrayList<>();
            this.clientes = new ArrayList<>(); 
        }

        public String get_nome() {
            return nome;
        }

        public String get_endereco() {
            return endereco;
        }

        public ArrayList<Reserva> get_reservas() {
            return reservas;
        }

        public ArrayList<Quarto> get_quartos() {
            return quartos;
        }

        public ArrayList<Funcionario> get_funcionarios() {
            return funcionarios;
        }

        public ArrayList<Cliente> get_clientes() {
            return clientes;
        }

        public void add_reserva(Reserva reserva) {
            reservas.add(reserva);
        }

        public void add_quarto(Quarto quarto) {
            quartos.add(quarto);
        }

        public void add_funcionario(Funcionario funcionario) {
            funcionarios.add(funcionario);
        }

        public void add_cliente(Cliente cliente) {
            clientes.add(cliente);
        }
        
        public ArrayList<Pessoa> get_pessoas() {
            ArrayList<Pessoa> pessoas = new ArrayList<>();
            pessoas.addAll(clientes);
            pessoas.addAll(funcionarios);
            return pessoas;
        }



        public Quarto buscar_quarto(int numero) {
            for (Quarto q : quartos) {
                if (q.get_numero() == numero) {
                    return q;
                }
            }
            System.out.println("Nenhum quarto encontrado com esse número " + numero);
            return null;
        }

        public ArrayList<Quarto> quartos_livres() {
            ArrayList<Quarto> livres = new ArrayList<>();
            for (Quarto q : quartos) {
                if (q.get_ocupacao() == Quarto.ocupacao.LIVRE) {
                    livres.add(q);
                }
            }
            return livres;
        }
    }
