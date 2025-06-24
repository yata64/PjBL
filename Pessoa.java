public abstract class Pessoa{
    protected String nome;
    protected String cpf;

    public Pessoa(String nome, String cpf){
        this.nome = nome;
        this.cpf = cpf;        
    }

    public abstract void exibir_informacoes();

    public String get_nome(){
        return nome;
    }

    public String get_cpf(){
        return cpf;
    }

    public String get_identificar(){
        return "Pessoa: " + nome;
    }
}
