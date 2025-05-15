package src.server;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Var {
    public static byte posEsp32 = 0;
    public static int serverPort = 12345;
    public static List<Thread> clientes = new ArrayList<>();
    public static List<String> dados = new ArrayList<>();
}
