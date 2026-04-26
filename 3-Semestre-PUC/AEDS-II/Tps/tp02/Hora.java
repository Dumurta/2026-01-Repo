public class Hora {
    private int hora;
    private int minuto;

    public Hora(int hora, int minuto) {
        this.hora = hora;
        this.minuto = minuto;
    }

    public int getHora() { return hora; }
    public int getMinuto() { return minuto; }

    public static Hora parseHora(String s) {
        // formato: HH:mm
        int hora = Integer.parseInt(s.substring(0, 2));
        int minuto = Integer.parseInt(s.substring(3, 5));
        return new Hora(hora, minuto);
    }

    public String formatar() {
        return String.format("%02d:%02d", hora, minuto);
    }
}
