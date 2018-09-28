package com.example.estudiante.triki;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Observable;

public class Servidor  extends Observable implements Runnable{
    private DatagramSocket ds;
    boolean conectado;
    String x;
    public Servidor() {
        conectado = false;
    }

    @Override
    public void run() {
        while (true) {
            if (!conectado) {
                try {
                    ds = new DatagramSocket(5000);
                    System.out.println("Servidor iniciado");
                    conectado = true;
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    recibir();
                    Thread.yield();
                } /*
                 * catch (InterruptedException e) { e.printStackTrace(); }
                 */ catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void recibir() throws IOException {

        byte[] buf = new byte[64]; // 47k
        DatagramPacket p = new DatagramPacket(buf, buf.length);
        ds.receive(p);
        setChanged();
        ByteArrayInputStream bais = new ByteArrayInputStream(buf);
        DataInputStream entrada = new DataInputStream(bais);
         x =entrada.readUTF();

        notifyObservers(x);
        clearChanged();
    }
}
