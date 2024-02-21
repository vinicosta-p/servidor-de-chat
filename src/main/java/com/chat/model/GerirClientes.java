package com.chat.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class GerirClientes implements Runnable {
        private Socket cliente;
        private Scanner entrada;
        private String nickName = "";
        private PrintStream saidaServidor;
        
        GerirClientes(Socket cliente) throws FileNotFoundException, IOException{
            this.cliente = cliente;
            this.entrada = new Scanner(this.cliente.getInputStream());
            this.saidaServidor = new PrintStream(this.cliente.getOutputStream());
        }

        public void run(){
            try{
                System.out.println("Nova conexão com o cliente " +  
                this.cliente.getInetAddress().getHostAddress());

                nomeDoCliente();
                
                Server.messageToAll(nickName + " entrou no chat", this);
                System.out.println(nickName + " entrou no chat");

                while (entrada.hasNextLine()) {
                    String message = entrada.nextLine() + " - " + nickName;
                    Server.messageToAll(message, this);
                    System.out.println(message);
                }

                entrada.close();
                this.cliente.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void nomeDoCliente() throws IOException{
                        
            saidaServidor.println("Escreva seu nome: ");

            while (true) {
                if(entrada.hasNextLine()){
                    this.nickName = entrada.nextLine();
                    break;
                }   
            }
            
        }

        public void sendMessage(String message){
            this.saidaServidor.println(message);
        }
        
}
