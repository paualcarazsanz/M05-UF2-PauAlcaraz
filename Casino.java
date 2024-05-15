import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;

class Casino {
    private String nombre;
    private Provincia provincia;
    private double saldo;

    public Casino(String nombre, Provincia provincia, double saldo) {
        this.nombre = nombre;
        this.provincia = provincia;
        this.saldo = saldo;
    }

    public String getNombre() {
        return nombre;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public double getSaldo() {
        return saldo;
    }

    public void jugarRuleta(Ruleta ruleta) {
        double minApuesta = ruleta.getMinApuesta();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenido a la ruleta " + ruleta.getTipo() + " de " + nombre);
        System.out.println("El mínimo de apuesta es: " + minApuesta);

        try {
            System.out.println("Ingrese su apuesta:");
            double apuesta = scanner.nextDouble();

            if (apuesta < minApuesta) {
                System.out.println("La apuesta mínima es " + minApuesta + ". Inténtelo de nuevo.");
                return;
            }

            Random random = new Random();
            int resultado = random.nextInt(37);

            if (resultado == 0) {
                System.out.println("Ha salido el 0. Todos los jugadores pierden.");
                saldo -= apuesta;
            } else {
                System.out.println("¿A qué desea apostar?");
                System.out.println("1. Número específico");
                System.out.println("2. Par/Impar");
                int opcionApostar = scanner.nextInt();

                if (opcionApostar == 1) {
                    System.out.println("Ingrese el número al que desea apostar (entre 0 y 36):");
                    int numeroApostado = scanner.nextInt();
                    if (numeroApostado < 0 || numeroApostado > 36) {
                        System.out.println("Número inválido. Inténtelo de nuevo.");
                        return;
                    }
                    if (numeroApostado == resultado) {
                        System.out.println("¡Felicidades! Ha ganado apostando al número " + numeroApostado);
                        saldo += apuesta * 35;
                    } else {
                        System.out.println("El número ganador es: " + resultado);
                        System.out.println("Lo siento, ha perdido.");
                        saldo -= apuesta;
                    }
                    guardarApuesta("NumeroEspecifico", numeroApostado, resultado, saldo);
                } else if (opcionApostar == 2) {
                    System.out.println("Seleccione Par (P) o Impar (I):");
                    String opcionParImpar = scanner.next().toUpperCase();
                    if (!opcionParImpar.equals("P") && !opcionParImpar.equals("I")) {
                        System.out.println("Opción inválida. Inténtelo de nuevo.");
                        return;
                    }
                    boolean esPar = resultado % 2 == 0;
                    boolean haGanado = (opcionParImpar.equals("P") && esPar) || (opcionParImpar.equals("I") && !esPar);
                    if (haGanado) {
                        System.out.println("¡Felicidades! Ha ganado apostando a " + (esPar ? "Par" : "Impar"));
                        saldo += apuesta;
                    } else {
                        System.out.println("El número ganador es: " + resultado);
                        System.out.println("Lo siento, ha perdido.");
                        saldo -= apuesta;
                    }
                    guardarApuesta("ParImpar", opcionParImpar, resultado, saldo);
                } else {
                    System.out.println("Opción inválida. Inténtelo de nuevo.");
                    return;
                }
            }
            System.out.println("Saldo restante en el casino: " + saldo);
        } catch (InputMismatchException e) {
            System.out.println("Error: entrada no válida. Por favor, ingrese un valor numérico.");
            scanner.nextLine();
        }
    }

    private void guardarApuesta(String tipoApuesta, Object apuesta, int resultado, double saldo) {
        String userHome = System.getProperty("user.home");
        String filePath = userHome + File.separator + "Downloads" + File.separator + "apuestas.txt";

        File archivo = new File(filePath);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.out.println("Error al crear el archivo de apuestas: " + e.getMessage());
                return;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))) {
            writer.write(tipoApuesta + ": " + apuesta + ", Resultado: " + resultado + ", Saldo: " + saldo + "\n");
        } catch (IOException e) {
            System.out.println("Error al guardar la apuesta: " + e.getMessage());
        }
    }

}
