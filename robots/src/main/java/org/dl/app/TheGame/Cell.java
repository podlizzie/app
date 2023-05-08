package org.dl.app.TheGame;

import org.dl.app.Model.EntityStateProvider;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Cell implements Positioned {

    private volatile double x;

    private EntityStateProvider provider;

    private boolean isSeek = false;

    private Parasite parasite;
    private volatile double y;
    private boolean isDead = false;

    Random random;

    private int ttl;

    Timer timer;

    public Cell() {
        random = new Random();
        ttl = random.nextInt(15, 90);
        this.x = random.nextInt(50, 300);
        this.y = random.nextInt(50, 300);
        timer = new Timer();
        toLiveALife(1000);
        //потом сделать рандом
    }

    public double getPositionX() {
        return x;
    }

    public double getPositionY() {
        return y;
    }

    private void toLiveALife(int period) {
        timer.scheduleAtFixedRate(new CloserToDeath(), 0, period);
    }

    public void setProvoder(EntityStateProvider provider) {
        this.provider = provider;
    }

    class CloserToDeath extends TimerTask {
        public void run() {
            ttl--;
            System.out.println("cell -" + ttl);
            if (ttl == 0) {
                isDead = true;
                timer.cancel();
                if (isSeek){parasite.toStarveAgain();}
                if (provider != null){
                    provider.changeCellCondition(Cell.this);
                }
            }

        }
    }

    public void toSlowlyDieCauseParasite(Parasite _parasite) {
        timer.cancel();
        timer = new Timer();
        toLiveALife(500);
        parasite = _parasite;
        isSeek = true;
    }

    public void toRecover() {
        timer.cancel();
        isSeek = false;
        timer = new Timer();
        toLiveALife(1000);
    }
    public boolean isDead() {
        return isDead;
    }
}