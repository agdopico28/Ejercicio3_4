package servidor;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Servidor {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(9876); // Puerto del servidor

            byte[] receiveData = new byte[1024];

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                InetAddress clientAddress = receivePacket.getAddress(); // Extraer la dirección del cliente
                int clientPort = receivePacket.getPort(); // Extraer el puerto del cliente

                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Recibido desde el cliente: " + message);

                // Procesar el mensaje según tus necesidades
                String[] partes = message.split(":");

                String resultado = "";
                switch (partes[0]) {
                    case "SUMA":
                        resultado = "El resultado de la suma es: " + (Integer.parseInt(partes[1]) + Integer.parseInt(partes[2]));
                        break;
                    case "RESTA":
                        resultado = "El resultado de la resta es: " + (Integer.parseInt(partes[1]) - Integer.parseInt(partes[2]));
                        break;
                    case "EXIT":
                        resultado = "Fin del programa";
                        break;
                    default:
                        resultado="Formato incorrecto, utilice el formato OPERACION:num1:num2";
                }

                // Preparar y enviar una respuesta al cliente (si es necesario)
                byte[] sendData = resultado.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort); // Enviar la respuesta al cliente
                socket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
