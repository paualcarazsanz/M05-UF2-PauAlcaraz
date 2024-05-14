class RuletaNormal extends Ruleta {
    public RuletaNormal() {
        super("normal", 10);
    }

    @Override
    public void metodoAbstracto() {
        System.out.println("Ruleta normal.");
    }
}