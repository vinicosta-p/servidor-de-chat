package com.chat.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;



public class Servidor {
    private static List<NovoUsuario> listaDGerirClientes = new ArrayList<NovoUsuario>();
    private ServerSocket servidor;
    private int porta;

    public Servidor(int porta){
        this.porta = porta;
    }

    public void initServer() throws IOException {

        servidor = new ServerSocket(porta);
        System.out.println("Porta " + porta + " aberta!");
        
        while(true){
            Socket cliente = servidor.accept();  
            novaConexao(cliente);
        }  

    }

    public void finalizarServidor() throws IOException{
        servidor.close();
    }

    private void novaConexao(Socket socketCliente) throws FileNotFoundException, IOException{
            NovoUsuario novoCliente = new NovoUsuario(socketCliente);

            listaDGerirClientes.add(novoCliente);

            Thread novaThread = new Thread(novoCliente);
            novaThread.start();
    }


    public static void notificarTodos(String message, NovoUsuario mensageiro){
        for(NovoUsuario cls : listaDGerirClientes){
            if(cls != mensageiro){
                cls.sendMessage(message);
            }
        }
    }

    public int getPorta() {
        return porta;
    }

    
}



