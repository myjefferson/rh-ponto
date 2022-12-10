package model;

public class RegistroPonto {

    private int codigo;
    private String cpfColaborador;
    private String inicioExpediente;
    private String inicioIntervalo;
    private String fimIntervalo;
    private String fimExpediente;
    private String dtCadastro;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getCpfColaborador() {
        return cpfColaborador;
    }

    public void setCpfColaborador(String cpfColaborador) {
        this.cpfColaborador = cpfColaborador;
    }

    public String getInicioExpediente() {
        return inicioExpediente;
    }

    public void setInicioExpediente(String inicioExpediente) {
        this.inicioExpediente = inicioExpediente;
    }

    public String getInicioIntervalo() {
        return inicioIntervalo;
    }

    public void setInicioIntervalo(String inicioIntervalo) {
        this.inicioIntervalo = inicioIntervalo;
    }

    public String getFimIntervalo() {
        return fimIntervalo;
    }

    public void setFimIntervalo(String fimIntervalo) {
        this.fimIntervalo = fimIntervalo;
    }

    public String getFimExpediente() {
        return fimExpediente;
    }

    public void setFimExpediente(String fimExpediente) {
        this.fimExpediente = fimExpediente;
    }

    public String getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(String dtCadastro) {
        this.dtCadastro = dtCadastro;
    }
}
