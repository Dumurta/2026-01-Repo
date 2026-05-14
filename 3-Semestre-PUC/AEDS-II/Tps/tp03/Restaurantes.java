import java.util.Locale;

import javax.xml.crypto.Data;

public class Restaurantes{
    private int id;
    private String nome;
    private String cidade;
    private int capacidade;
    private double avaliacao;
    private String[] tiposCozinha;
    private int faixaPreco;
    private Hora horarioAbertura;
    private Hora horarioFechamento;
    private Data dataAbertura;
    private boolean aberto;

    public Restaurantes(
     int id,
     String nome,
     String cidade,
     int capacidade,
     double avaliacao,
     String[] tiposCozinha,
     int faixaPreco,
     Hora horarioAbertura,
     Hora horarioFechamento,
     Data dataAbertura,
     boolean aberto)
    {
        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
        this.capacidade = capacidade;
        this.avaliacao = avaliacao;
        this.tiposCozinha = tiposCozinha;
        this.faixaPreco = faixaPreco;
        this.horarioAbertura = horarioAbertura;
        this.horarioFechamento = horarioFechamento;
        this.dataAbertura = dataAbertura;
        this.aberto = aberto;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCidade() { return cidade; }
    public int getCapacidade() { return capacidade; }
    public double getAvaliacao() { return avaliacao; }
    public String[] getTiposCozinha() { return tiposCozinha; }
    public int getFaixaPreco() { return faixaPreco; }
    public Hora getHorarioAbertura() { return horarioAbertura; }
    public Hora getHorarioFechamento() { return horarioFechamento; }
    public Data getDataAbertura() { return dataAbertura; }
    public boolean isAberto() { return aberto; }

    public static Restaurante parerRestaurante(String s){
        String[] campos = ;
        int id = ;
        String nome = ;
        String cidade = ;
        int capacidade = ;
        double avaliacao = ;


        String[] tiposCozinha = ;
        int faixaPreco = ;

        String horario = ;
        int dashPos = ;
        Hora horarioAbertura = ;
        Hora horarioFechamento = ;

        Data dataAbertura = ;

        boolean aberto = ;


        return new Restaurante(id, nome, cidade, capacidade, avaliacao, tiposCozinha, faixaPreco, horarioAbertura, horarioFechamento, dataAbertura, aberto);
    }

    
}