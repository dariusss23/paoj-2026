package com.pao.laboratory08.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";

    public static void main(String[] args) throws Exception {
        List<Student> lista = new ArrayList<>();
        
        BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
        String linie;

        while ((linie = br.readLine()) != null) {
            linie = linie.trim();

            if (linie.isEmpty()) {
                continue;
            }

            String[] date = linie.split(",");
            
            if (date.length < 4) {
                continue;
            }

            Adresa adr = new Adresa(date[2].trim(), date[3].trim());
            Student st = new Student(date[0].trim(), Integer.parseInt(date[1].trim()), adr);
            lista.add(st);
        }
        br.close();

        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextLine()) return;
        
        String input = sc.nextLine();
        String[] parti = input.split(" ", 2);
        String comanda = parti[0];

        if (comanda.equals("PRINT")) {
            for (Student s : lista) {
                System.out.println(s);
            }
        } 
        else if (comanda.equals("SHALLOW") && parti.length > 1) {
            String nume = parti[1];
            for (Student s : lista) {
                if (s.getNume().equals(nume)) {
                    Student clona = (Student) s.clone();
                    clona.getAdresa().setOras("MODIFICAT");
                    System.out.println("Original: " + s);
                    System.out.println("Clona: " + clona);
                }
            }
        } 
        else if (comanda.equals("DEEP") && parti.length > 1) {
            String nume = parti[1];
            for (Student s : lista) {
                if (s.getNume().equals(nume)) {
                    Student clona = s.deepClone();
                    clona.getAdresa().setOras("MODIFICAT");
                    System.out.println("Original: " + s);
                    System.out.println("Clona: " + clona);
                }
            }
        }
    }
}