package twisk.simulation;

import twisk.monde.Monde;
import twisk.ui.Observateur;

import java.util.Iterator;

public interface InterfSimulation extends Iterable<Client>{

    void simuler(Monde w);
    void setNbClients(int nb);
    ClientManager getClientManager();
    void ajouterObservateur(Observateur obsv);
    Iterator<Client> iterator();
}
