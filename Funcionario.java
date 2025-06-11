public class Funcionario extends Pessoa{
    private String cargo;
    private float salario;

    public Funcionario(String nome, String cpf, String cargo, float salario){
        super(nome, cpf);
        this.cargo = cargo;
        this.salario = salario;
    }

    public String get_cargo(){
        return cargo;
    }

    public float get_salario(){
        return salario;
    }

    @Override
    public void exibir_informacoes(){
        System.out.println("Funcionário: " + nome + "Cargo: " + cargo + "Salário: " + salario);
    }
}
