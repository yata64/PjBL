public class Cliente extends Pessoa{
    private String email;

    public Cliente(String nome, String cpf, String email){
        super(nome, cpf);
        this.email = email;
    }

    @Override
    public void exibir_informacoes(){
        System.out.println("Cliente: " + nome + "E-mail: " + email);
    }

    @Override
    public String get_identificar(){
        return "Cliente: " + nome + " - E-Mail: " + email + " - CPF: " + cpf + "\n";
    }

    public String get_email(){
        return email;
    }  
}
