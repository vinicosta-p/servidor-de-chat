package com.chat.controllers;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ServerListener extends Thread{
    Socket cliente;
    ServerListener(Socket cliente) {
        this.cliente = cliente;
    }
    
    public void run() {
        try (Scanner servidor = new Scanner(cliente.getInputStream())) {
            while (servidor.hasNextLine()) {
                System.out.println(servidor.nextLine());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }
}
