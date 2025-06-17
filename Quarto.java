public class Quarto{
    private int numero;
    private String tipo;
    private int capacidade;
    private float diaria;
    private ocupacao ocupacao;

    public enum ocupacao{
        OCUPADO, 
        LIVRE,
        LIMPEZA,
        MANUTENCAO
    }

    public Quarto(int numero, String tipo, int capacidade, float diaria, ocupacao ocupacao){
        this.numero = numero;
        this.tipo = tipo;
        this.capacidade = capacidade;
        this.diaria = diaria;
        this.ocupacao = ocupacao;

    }

    public void set_ocupacao(ocupacao nova){
        this.ocupacao = nova;
    }

    public int get_numero(){
        return numero;
    }

    public String get_tipo(){
        return tipo;
    }

    public int get_capacidade(){
        return capacidade;
    }

    public float get_diaria(){
        return diaria;
    }

    public ocupacao get_ocupacao(){
        return ocupacao;
    }
}
