public class Reserva {
    private int numero;
    private String tipo;
    private float preco;
    private String cliente;

    public Reserva(int numero, String tipo, float preco, String cliente){
        this.numero = numero;
        this.tipo = tipo;
        this.preco = preco;
        this.cliente = cliente;
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

    public String cliente(){
        return cliente;
    }

}
