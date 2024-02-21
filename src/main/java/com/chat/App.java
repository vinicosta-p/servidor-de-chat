package com.chat;

import java.io.IOException;

import com.chat.model.Servidor;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        Servidor servidor = new Servidor(12345);
        servidor.initServer();
    }
}
