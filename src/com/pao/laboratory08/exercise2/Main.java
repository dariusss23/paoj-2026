package com.pao.laboratory08.exercise2;

import com.pao.laboratory08.exercise1.Student;
import com.pao.laboratory08.exercise1.Adresa;
import java.io.*;
import java.util.*;

public class Main {
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";

    public static void main(String[] args) throws Exception {
        List<Student> toti = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
        String linie;

        while ((linie = br.readLine()) != null) {
            linie = linie.trim();
            if (linie.isEmpty()) continue;

            String[] date = linie.split(",");
            if (date.length < 4) continue;

            Adresa adr = new Adresa(date[2].trim(), date[3].trim());
            Student st = new Student(date[0].trim(), Integer.parseInt(date[1].trim()), adr);
            toti.add(st);
        }
        br.close();

        Scanner sc = new Scanner(System.in);
        int prag = sc.nextInt();

        List<Student> filtrati = new ArrayList<>();
        for (int i = 0; i < toti.size(); i++) {
            Student s = toti.get(i);
            if (s.getVarsta() >= prag) {
                filtrati.add(s);
            }
        }

        BufferedWriter fout = new BufferedWriter(new FileWriter("rezultate.txt"));
        for (int i = 0; i < filtrati.size(); i++) {
            fout.write(filtrati.get(i).toString());
            fout.newLine();
        }
        fout.close();

        System.out.println("Filtru: varsta >= " + prag);
        System.out.println("Rezultate: " + filtrati.size() + " studenti");
        System.out.println();

        for (int i = 0; i < filtrati.size(); i++) {
            System.out.println(filtrati.get(i));
        }

        System.out.println();
        System.out.println("Scris in: rezultate.txt");
    }
}