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
}