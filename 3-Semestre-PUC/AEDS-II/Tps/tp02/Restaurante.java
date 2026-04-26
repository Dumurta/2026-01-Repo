import java.util.Locale;

public class Restaurante {
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

    public Restaurante(int id, String nome, String cidade, int capacidade,
                       double avaliacao, String[] tiposCozinha, int faixaPreco,
                       Hora horarioAbertura, Hora horarioFechamento,
                       Data dataAbertura, boolean aberto) {
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

    public static Restaurante parseRestaurante(String s) {
        // formato CSV: id,nome,cidade,capacidade,avaliacao,tipos_cozinha,faixa_preco,horario,data_abertura,aberto
        String[] campos = s.split(",");
        int id = Integer.parseInt(campos[0].trim());
        String nome = campos[1].trim();
        String cidade = campos[2].trim();
        int capacidade = Integer.parseInt(campos[3].trim());
        double avaliacao = Double.parseDouble(campos[4].trim());

        // tipos_cozinha separados por ;
        String[] tiposCozinha = campos[5].trim().split(";");

        // faixa_preco: $ -> 1, $$ -> 2, $$$ -> 3, $$$$ -> 4
        int faixaPreco = campos[6].trim().length();

        // horario: HH:mm-HH:mm
        String horario = campos[7].trim();
        int dashPos = horario.indexOf('-');
        Hora horarioAbertura = Hora.parseHora(horario.substring(0, dashPos));
        Hora horarioFechamento = Hora.parseHora(horario.substring(dashPos + 1));

        Data dataAbertura = Data.parseData(campos[8].trim());

        boolean aberto = Boolean.parseBoolean(campos[9].trim());

        return new Restaurante(id, nome, cidade, capacidade, avaliacao,
                tiposCozinha, faixaPreco, horarioAbertura, horarioFechamento,
                dataAbertura, aberto);
    }

    private static String faixaPrecoParaString(int faixa) {
        String faixaTexto = "$";
        if (faixa == 2) {
            faixaTexto = "$$";
        } else if (faixa == 3) {
            faixaTexto = "$$$";
        } else if (faixa == 4) {
            faixaTexto = "$$$$";
        }
        return faixaTexto;
    }

    public String formatar() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(id).append(" ## ");
        sb.append(nome).append(" ## ");
        sb.append(cidade).append(" ## ");
        sb.append(capacidade).append(" ## ");
        sb.append(String.format(Locale.US, "%.1f", avaliacao)).append(" ## ");
        sb.append("[");
        for (int i = 0; i < tiposCozinha.length; i++) {
            if (i > 0) sb.append(",");
            sb.append(tiposCozinha[i]);
        }
        sb.append("] ## ");
        sb.append(faixaPrecoParaString(faixaPreco)).append(" ## ");
        sb.append(horarioAbertura.formatar()).append("-").append(horarioFechamento.formatar()).append(" ## ");
        sb.append(dataAbertura.formatar()).append(" ## ");
        sb.append(aberto);
        sb.append("]");
        return sb.toString();
    }
}
