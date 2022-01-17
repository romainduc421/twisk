package twisk.simulation;

import twisk.monde.Etape;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ClientManager implements Iterable<Client>{
    private List<Client> clients;
    private int ClientsNb;

    public ClientManager(){
        this.clients = new ArrayList<>(12) ;
    }
    public ClientManager(int nbClients) {
        this.clients = new ArrayList(nbClients);
    }

    public int getNbClients() {
        return this.clients.size();
    }

    public void setClients(int... tabClient) {
        for (int j : tabClient) {
            this.clients.add(new Client(j));
        }

    }

    public void setClients(List<Integer> tabCustomers)
    {
        if(tabCustomers.size()<=getClientsNb()){
            for(int pid : tabCustomers)
                getClients().add(new Client(pid));
        }
    }

    public void allerA(int numeroClient, Etape etape, int rang) {

        for(Client client : this) {
            if (client.getCustomer_nb() == numeroClient)
                client.allerA(etape, rang);
        }

    }

    public void reset(){
        getClients().removeAll(getClients());
        setNbClients(0);
    }

    public Iterator<Client> iterator() {
        return this.clients.iterator();
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for(Client c : this)
            s.append(c.toString());

        return s.toString();
    }

    public List<Client> getClients() {
        return clients;
    }

    public int getClientsNb()
    {
        return ClientsNb;
    }

    public void setNbClients(int ClientsNb)
    {
        this.ClientsNb = ClientsNb;
    }
}
