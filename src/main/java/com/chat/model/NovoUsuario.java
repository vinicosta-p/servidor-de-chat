package com.chat.model;

import com.chat.model.Servidor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


public class NovoUsuario extends Thread {
        private Socket cliente;
        private Scanner entrada;
        private String nickName = "";
        private Servidor servidor;
        
        NovoUsuario(Socket cliente, Servidor servidor) throws FileNotFoundException, IOException{
            this.cliente = cliente;
            this.entrada = new Scanner(this.cliente.getInputStream());
            this.servidor = servidor;
        }

        public void run(){
            try{
                //evento
                nomeDoCliente();
                //evento
                listenerUsuario();

                entrada.close();
                this.cliente.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
       
        private void listenerUsuario() throws IOException{
            while (entrada.hasNextLine()) {
                String message = entrada.nextLine() + " - " + nickName;
                servidor.notificarTodos(message, this);
                System.out.println(message);
            }
        }


        public void nomeDoCliente() throws IOException{
                        
            //saidaServidor.println("Escreva seu nome: ");

            while (true) {
                if(entrada.hasNextLine()){
                    this.nickName = entrada.nextLine();
                    break;
                }   
            }
            
            servidor.notificarTodos(nickName + " entrou no chat", this);
            System.out.println(nickName + " entrou no chat");
        }
        
        public Socket getCliente() {
            return cliente;
        }
}
