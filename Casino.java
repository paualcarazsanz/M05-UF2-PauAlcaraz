import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    //En el nostre cas no te sentit fer un fitxer amb Interface
    public static void main(String[] args) {
        ArrayList<Provincia> provincias = new ArrayList<>();

        provincias.add(new Provincia("Barcelona"));
        provincias.add(new Provincia("Girona"));

        Casino casino1 = new Casino("Golden Park", provincias.get(0), 100000);
        Casino casino2 = new Casino("Bet365", provincias.get(1), 150000);

        Ruleta ruletaNormal = new RuletaNormal();
        Ruleta ruletaVIP = new RuletaVIP();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Bienvenido al sistema de casinos.\nElige la provincia en la que quieres jugar:");
            for (int i = 0; i < provincias.size(); i++) {
                System.out.println((i + 1) + ". " + provincias.get(i).getNombre());
            }
            System.out.println(provincias.size() + 1 + ". Ver Registro de Apuestas");
            System.out.println(provincias.size() + 2 + ". Salir");
            System.out.println("Seleccione una opción:");
            int opcionMenu = scanner.nextInt();
            if (opcionMenu == provincias.size() + 2) {
                System.out.println("¡Gracias por jugar!");
                break;
            } else if (opcionMenu == provincias.size() + 1) {
                verRegistroApuestas();
                continue;
            }
            Provincia provinciaSeleccionada = provincias.get(opcionMenu - 1);

            System.out.println("Casinos disponibles en " + provinciaSeleccionada.getNombre() + ":");
            System.out.println("1. " + casino1.getNombre());
            System.out.println("2. " + casino2.getNombre());
            System.out.println("Seleccione un casino:");
            int opcionCasino = scanner.nextInt();
            Casino casinoSeleccionado = null;
            if (opcionCasino == 1) {
                casinoSeleccionado = casino1;
            } else if (opcionCasino == 2) {
                casinoSeleccionado = casino2;
            } else {
                System.out.println("Opción inválida. Por favor, seleccione un casino válido.");
                continue;
            }
            System.out.println("Seleccione el tipo de ruleta:");
            System.out.println("1. Ruleta Normal");
            System.out.println("2. Ruleta VIP");
            int opcionRuleta = scanner.nextInt();
            Ruleta ruletaSeleccionada = null;
            if (opcionRuleta == 1) {
                ruletaSeleccionada = ruletaNormal;
            } else if (opcionRuleta == 2) {
                ruletaSeleccionada = ruletaVIP;
            } else {
                System.out.println("Opción inválida. Por favor, seleccione un tipo de ruleta válido.");
                continue;
            }
            casinoSeleccionado.jugarRuleta(ruletaSeleccionada);

            System.out.println("¿Desea jugar nuevamente? (s/n)");
            String jugarOtraVez = scanner.next();
            if (!jugarOtraVez.equalsIgnoreCase("s")) {
                break;
            }
        }
    }
    
    private static void verRegistroApuestas() {
        String userHome = System.getProperty("user.home");
        String filePath = userHome + File.separator + "Downloads" + File.separator + "apuestas.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            System.out.println("Registro de Apuestas:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de registro de apuestas: " + e.getMessage());
        }
    }
}