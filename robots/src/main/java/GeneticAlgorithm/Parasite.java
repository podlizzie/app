package GeneticAlgorithm;

import Model.Robot;

import javax.swing.plaf.PanelUI;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Timer;
import java.util.TimerTask;

public class Parasite extends Robot {
    private int ttl = 20;

    public Condition condition;
    Timer timer;
    private PropertyChangeSupport propChangeDispatcher = new PropertyChangeSupport(this);
    private boolean isDead = false;

    public Parasite(){
        timer = new Timer();
        toLiveALife();
        condition = Condition.TO_MOVE;
    }

    class CloserToDeath extends TimerTask {
        public void run() {
            ttl--;
            if (ttl == 0){
                isDead = true;
                propChangeDispatcher.firePropertyChange("parasite", false, true);
                timer.cancel();
            }

            //timer.cancel(); //Terminate the timer thread
        }
    }

    class toWait extends TimerTask {
        public void run() {

        }
    }

    public void toParasitize(Cell cell){
        int hp = cell.toFeed(ttl);
        timer.cancel();
        timer.scheduleAtFixedRate(new toWait(), 0, hp);
        toLiveALife();
    }


    private void toLiveALife(){
        timer.scheduleAtFixedRate(new CloserToDeath(), 0, 1000);
    }
    public void addTextChangeListener(PropertyChangeListener listener)
    {
        propChangeDispatcher.addPropertyChangeListener("parasite", listener);
    }

    public boolean isAlive(){return !isDead;}

}
