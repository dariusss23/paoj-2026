package com.pao.laboratory09.exercise3;

import com.pao.laboratory09.exercise1.Tranzactie;

public class ProcessorThread implements Runnable {
    public volatile boolean activ = true;
    private final CoadaTranzactii banda;

    public ProcessorThread(CoadaTranzactii banda) {
        this.banda = banda;
    }

    @Override
    public void run() {
        try {
            while (activ || !banda.esteGoala()) {
                Tranzactie t = null;
                synchronized (banda) {
                    if (banda.esteGoala() && !activ) break;
                    if (banda.esteGoala()) {
                        banda.wait(100);
                        if (banda.esteGoala()) continue;
                    }
                    t = banda.extrage();
                }
                if (t != null) {
                    System.out.println("[Processor] Factura #" + t.getId() + " - 100.0 RON | " + t.getData());
                    Thread.sleep(80);
                }
            }
        } catch (InterruptedException e) {}
    }
}