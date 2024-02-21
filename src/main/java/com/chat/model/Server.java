package com.chat.model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;



public class Server {
    private static List<GerirClientes> listaDGerirClientes = new ArrayList<GerirClientes>();
    public static void main(String[] args) throws IOException {

        ServerSocket servidor = new ServerSocket(12345);
        System.out.println("Porta 12345 aberta!");
        
        
        
        while(true){
            Socket cliente = servidor.accept();  
            
            GerirClientes novoCliente = new GerirClientes(cliente);

            listaDGerirClientes.add(novoCliente);

            Thread novaThread = new Thread(novoCliente);
            novaThread.start();
        }
        
       // servidor.close();

    }


    public static void messageToAll(String message, GerirClientes mensageiro){
        for(GerirClientes cls : listaDGerirClientes){
            if(cls != mensageiro){
                cls.sendMessage(message);
            }
        }
    }

    
}



