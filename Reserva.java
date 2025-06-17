import java.time.LocalDate;

public class Reserva{
    private int numero;
    private String tipo;
    private float preco;
    private String cliente;
    private LocalDate dataEntrada;
    private LocalDate dataSaida;

    public Reserva(int numero, String tipo, float preco, String cliente, LocalDate entrada, LocalDate saida){
        this.numero = numero;
        this.tipo = tipo;
        this.preco = preco;
        this.cliente = cliente;
        this.dataEntrada = entrada;
        this.dataSaida = saida;
    }

    public int get_numero(){
        return numero;
    }

    public String get_tipo(){
        return tipo;
    }

    public float get_preco(){
        return preco;
    }

    public String get_cliente(){
        return cliente;
    }

    public LocalDate get_entrada(){
        return dataEntrada;
    }

    public LocalDate get_saida(){
        return dataSaida;
    }
}
