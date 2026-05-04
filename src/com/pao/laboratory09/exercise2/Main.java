package com.pao.laboratory09.exercise2;

import com.pao.laboratory09.exercise1.TipTranzactie;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();

        new File("output").mkdirs();
        String path = "output/lab09_ex2.bin";

        try (FileOutputStream fos = new FileOutputStream(path)) {
            for (int i=0; i<n; i++) {
                int id = sc.nextInt();
                double suma = sc.nextDouble();
                String data = sc.next();
                TipTranzactie tip = TipTranzactie.valueOf(sc.next());

                ByteBuffer bb = ByteBuffer.allocate(32).order(ByteOrder.LITTLE_ENDIAN);
                bb.putInt(id);
                bb.putDouble(suma);
                
                byte[] dataBytes = data.getBytes();
                for (int j=0; j<10; j++) {
                    bb.put(j < dataBytes.length ? dataBytes[j] : (byte) ' ');
                }

                bb.put((byte) (tip == TipTranzactie.CREDIT ? 0 : 1));
                bb.put((byte) 0);
                
                fos.write(bb.array());
            }
        }

        try (RandomAccessFile raf = new RandomAccessFile(path, "rw")) {
            while (sc.hasNext()) {
                String cmd = sc.next();
                if (cmd.equals("READ")) {
                    printRow(raf, sc.nextInt());
                } else if (cmd.equals("UPDATE")) {
                    int idx = sc.nextInt();
                    String statusStr = sc.next();
                    raf.seek(idx * 32 + 23);
                    raf.write(statusStr.equals("PENDING") ? 0 : (statusStr.equals("PROCESSED") ? 1 : 2));
                    System.out.println("Updated [" + idx + "]: " + statusStr);
                } else if (cmd.equals("PRINT_ALL")) {
                    for (int i=0; i<n; i++) printRow(raf, i);
                }
            }
        }
    }

    private static void printRow(RandomAccessFile raf, int idx) throws IOException {
        raf.seek(idx * 32);
        byte[] b = new byte[32];
        raf.readFully(b);
        ByteBuffer bb = ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN);

        int id = bb.getInt();
        double suma = bb.getDouble();
        byte[] d = new byte[10]; bb.get(d);
        String data = new String(d).trim();
        String tip = bb.get() == 0 ? "CREDIT" : "DEBIT";
        byte s = bb.get();
        String status = (s == 0) ? "PENDING" : (s == 1 ? "PROCESSED" : "REJECTED");

        System.out.format("[%d] id=%d data=%s tip=%s suma=%.2f RON status=%s\n", idx, id, data, tip, suma, status);
    }
}