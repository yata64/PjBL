import java.util.ArrayList;

public class Hotel{
    private String nome;
    private String endereco;
    private ArrayList<Reserva> reservas;
    private ArrayList<Quarto> quartos;

    public Hotel(String nome, String endereco){
        this.nome = nome;
        this.endereco = endereco;
        this.reservas = new ArrayList<>();
        this.quartos = new ArrayList<>();
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
    
}