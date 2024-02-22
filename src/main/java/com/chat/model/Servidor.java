package com.chat.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;



public class Servidor {
    private List<NovoUsuario> listaDGerirClientes = new ArrayList<NovoUsuario>();
    private ServerSocket servidor;
    private int porta;
    private PrintStream saidaServidor;

    public Servidor(int porta){
        this.porta = porta;
    }

    public void initServer() throws IOException {

        servidor = new ServerSocket(porta);
        System.out.println("Porta " + porta + " aberta!");
        
        //Migrar para uma thread
        while(true){
            Socket cliente = servidor.accept();  
            novaConexao(cliente);
        }  

    }

    public void finalizarServidor() throws IOException{
        for(NovoUsuario cls : listaDGerirClientes){
            cls.getCliente().close();
        }
        servidor.close();
    }

    private void novaConexao(Socket socketCliente) throws FileNotFoundException, IOException{
            NovoUsuario novoCliente = new NovoUsuario(socketCliente, this);

            listaDGerirClientes.add(novoCliente);
            
            Thread novaThread = new Thread(novoCliente);
            novaThread.start();
            saidaServidor = new PrintStream(socketCliente.getOutputStream());
            saidaServidor.println("Escreva seu nome: ");
    }

    public void desconectar(NovoUsuario usuarioDesconectado) throws IOException{
        String mensagemDeSaida = usuarioDesconectado.getNickName() + " saiu do grupo de chat ";
        System.out.println(mensagemDeSaida);
        notificarTodos(mensagemDeSaida, usuarioDesconectado);
        listaDGerirClientes.remove(usuarioDesconectado);
    }


    public void notificarTodos(String message, NovoUsuario mensageiro) throws IOException{
        for(NovoUsuario cls : listaDGerirClientes){
            if(cls != mensageiro){
                saidaServidor = new PrintStream(cls.getCliente().getOutputStream());
                saidaServidor.println(message);
            }
        }
    }
    /**
     * 
     */
    public int getPorta() {
        return porta;
    }

    
}



