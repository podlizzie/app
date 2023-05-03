package org.dl.app.ViewModel;


import org.dl.app.Model.EntityStateProvider;
import org.dl.app.Model.GameModel;
import org.dl.app.View.GameView;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class ViewModel {
    private GameModel gameModel;
    private GameView gameView;

    private EntityStateProvider provider;
    private final Timer m_timer = initTimer();

    private static java.util.Timer initTimer() {
        java.util.Timer timer = new java.util.Timer("events generator", true);
        return timer;
    }

    public ViewModel(GameModel gameModel, GameView gameView, EntityStateProvider provider) {

        this.gameModel = gameModel;
        this.gameView = gameView;
        this.provider = provider;
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameView.update();
            }
        }, 0, 50);
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameModel.update();
            }
        }, 0, 10);

        gameView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameModel.change(e.getPoint());
                gameView.repaint();
            }
        });

    }
    public GameView getGameView(){return gameView;}
}