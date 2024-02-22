package com.chat.controllers;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) 
                    throws UnknownHostException, IOException {
        Socket cliente = new Socket("127.0.0.1", 12345);
        
        System.out.println("O cliente se conectou ao servidor!");

        Scanner teclado = new Scanner(System.in);
        PrintStream saida = new PrintStream(cliente.getOutputStream());

        new ServerListener(cliente).start();
        
        while (teclado.hasNextLine()) {
            String inputUser = teclado.nextLine();
            saida.println(inputUser);
            //tratar no serverlistener
            if(inputUser.compareTo("sdc -exit") == 0){
                break;
            }
            
        }

        saida.close();
        teclado.close();
        cliente.close();
    }
}