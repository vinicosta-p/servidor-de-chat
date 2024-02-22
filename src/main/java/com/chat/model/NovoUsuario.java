package com.chat.model;

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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        private void exitChat() throws IOException{
            servidor.desconectar(this);
            nickName = null;
            entrada.close();
            this.cliente.close();
        }
       
        private void listenerUsuario() throws IOException {
            while (entrada.hasNextLine()) {
                String inputUser = entrada.nextLine();
                
                if(inputUser.compareTo("sdc -exit") == 0){
                    exitChat();
                    break;
                }
                
                sendMessage(inputUser);
            }
        }

        private void sendMessage(String inputUser) throws IOException{
            String message =  inputUser + " - " + nickName;
            servidor.notificarTodos(message, this);
            System.out.println(message);
        }


        public void nomeDoCliente() throws IOException{

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

        public String getNickName() {
            return nickName;
        }
}
