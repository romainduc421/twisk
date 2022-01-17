package twisk.outils;
import java.util.ArrayList;
import java.util.List;

import javafx.concurrent.Task;

public class GestionnaireThreads {
    private static GestionnaireThreads INSTANCE = new GestionnaireThreads();
    private List<Thread> threads = new ArrayList<>(10);

    private GestionnaireThreads() {
    }

    public static GestionnaireThreads getInstance() {
        return INSTANCE;
    }

    public void lancer(Task task) {
        Thread thread = new Thread(task);
        this.threads.add(thread);
        thread.setDaemon(true);
        thread.start();
    }

    public void detruireTout() {
        for(Thread thread : this.threads) {
            thread.interrupt();
        }
        this.threads.clear();
    }
}
