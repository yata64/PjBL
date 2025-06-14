import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Hotel{
    private String nome;
    private String endereco;
    private ArrayList<Reserva> reservas;
    private ArrayList<Quarto> quartos;
    private ArrayList<Funcionario> funcionarios;

    public Hotel(String nome, String endereco){
        this.nome = nome;
        this.endereco = endereco;
        this.reservas = new ArrayList<>();
        this.quartos = new ArrayList<>();
        this.funcionarios = new ArrayList<>();

    }

    public String get_nome(){
        return nome;
    }

    public String get_endereco(){
        return endereco;
    }

    public ArrayList<Reserva> get_reservas(){
        return reservas;
    }

    public ArrayList<Quarto> get_quartos(){
        return quartos;
    }

    public void add_reserva(Reserva reserva){
        reservas.add(reserva);
    }

    public void add_quarto(Quarto quarto){
        quartos.add(quarto);
    }

    public void add_funcionario(Funcionario funcionario){
        funcionarios.add(funcionario);
    }

    public Quarto buscar_quarto(int numero){
        for (Quarto q : quartos){
            if (q.get_numero() == numero){
                return q;
            }
        }
        System.out.println("Nenhum quarto encontrado com esse número " + numero);
        return null;
    }

    public ArrayList<Quarto> quartos_livres(){
        ArrayList<Quarto> livres = new ArrayList<>();
        for (Quarto q : quartos){
            if (q.get_ocupacao() == Quarto.ocupacao.LIVRE){
                livres.add(q);
            }
        }
        return livres;
    }

    public void carregar_quartos(String caminho) throws Exception{
        BufferedReader br = new BufferedReader(new FileReader(caminho));
        String linha;

        while ((linha = br.readLine()) != null){
            String[] partes = linha.split(",");
            int numero = Integer.parseInt(partes[0]);
            String tipo = partes[1];
            int capacidade = Integer.parseInt(partes[2]);
            float diaria = Float.parseFloat(partes[3]);
            Quarto.ocupacao status = Quarto.ocupacao.valueOf(partes[4]);

            Quarto quarto = new Quarto(numero, tipo, capacidade, diaria, status);
            this.add_quarto(quarto);
        }

        br.close();
    }

    public void salvar_reservas(String arquivo) throws Exception {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo));
        out.writeObject(reservas);
        out.close();
    }

    public void carregar_reservas(String arquivo) throws Exception{
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo));
        reservas = (ArrayList<Reserva>) in .readObject();
        in.close();
    } 
    
}