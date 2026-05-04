package com.pao.laboratory09.exercise3;

import com.pao.laboratory09.exercise1.TipTranzactie;
import com.pao.laboratory09.exercise1.Tranzactie;

public class ATMThread extends Thread {
    private final int idATM;
    private final CoadaTranzactii banda;

    public ATMThread(int id, CoadaTranzactii banda) {
        this.idATM = id;
        this.banda = banda;
    }

    @Override
    public void run() {
        try {
            for (int i=1; i<=4; i++) {
                Tranzactie t = new Tranzactie(idATM*1000+i, 100.0, "2024-05-04", "ATM", "Banca", TipTranzactie.DEBIT);
                banda.adauga(t, "ATM-" + idATM);
                System.out.println("[ATM-" + idATM + "] trimite: Tranzactie #" + t.getId() + " 100.0 RON");
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {}
    }
}