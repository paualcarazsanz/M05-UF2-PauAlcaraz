abstract class Ruleta {
    private String tipo;
    private double minApuesta;

    public Ruleta(String tipo, double minApuesta) {
        this.tipo = tipo;
        this.minApuesta = minApuesta;
    }

    public String getTipo() {
        return tipo;
    }

    public double getMinApuesta() {
        return minApuesta;
    }
    public abstract void metodoAbstracto();
}

