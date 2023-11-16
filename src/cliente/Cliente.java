package cliente;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("localhost"); // Direccion IP del servidor
            int serverPort = 9876; // Puerto del servidor
            Scanner sc = new Scanner(System.in);

            String message="";
            while(!message.equals("EXIT")) {

                message = sc.nextLine();
                byte[] sendData = message.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
                socket.send(sendPacket);

                // Aqui puedes esperar una respuesta del servidor si es necesario
                byte[] receiveData = new byte[1024];

                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length, serverAddress, serverPort);
                socket.receive(receivePacket);

                String serverMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Recibido desde el servidor: " + serverMessage);
            }

            // Cerrar el socket cuando termines de usarlo
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}