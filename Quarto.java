public class Quarto{
    private int numero;
    private String tipo;
    private int capacidade;
    private float diaria;

    public enum ocupacao{
        ocupado, 
        livre,
        limpeza,
        manutencao

    }

    private ocupacao ocupacao;

    public Quarto(int numero, String tipo, int capacidade, float diaria, ocupacao ocupacao){
        this.numero = numero;
        this.tipo = tipo;
        this.capacidade = capacidade;
        this.diaria = diaria;
        this.ocupacao = ocupacao;

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