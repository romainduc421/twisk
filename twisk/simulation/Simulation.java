package twisk.simulation;

import twisk.monde.Etape;
import twisk.monde.Guichet;
import twisk.monde.Monde;
import twisk.mondeIG.Sujet;
import twisk.outils.FabriqueNumero;
import twisk.outils.KitC;

import java.nio.file.Paths;
import java.util.Iterator;

@SuppressWarnings("all")
public class Simulation extends Sujet implements InterfSimulation, Iterable<Client> {

    private KitC kit;
    private ClientManager cl_manager;
    private int nbClients;

    /**
     * Constructeur de Simulation
     */
    public Simulation(){
    }

    /**
     * fonction permettant de simuler en sortie standard l'acheminement des clients
     * demande generation du code C à partir du monde créé
     * puis crée le fichier /tmp/twisk/client.c
     * compile dynamiquement ce fichier
     * construit la librairie dynamique /tmp/twisk/libTwisk.so
     * charge cette librairie
     * puis appelle les fonctions de la librairie et effectue l'equivalent du main() seance 4
     */
    public void simuler(Monde m)
    {
        this.kit = new KitC();
        kit.creerEnvironnement();
        System.out.println(m.toString());
        kit.creerFichier(m.toC());
        kit.compiler();
        kit.construireLaLibrairie();

        if(kit.osWind()){
            String pathTo = Paths.get(System.getProperty("java.io.tmpdir")+ "/twisk/") + "\\";
            System.load(pathTo + "libTwisk"+kit.getCpt_lib()+".so");
        }
        else if(kit.osUnix()){
            System.load("/tmp/twisk/libTwisk"+kit.getCpt_lib()+".so");
        }
        boolean running = true;
        int nbGuichets = m.nbGuichets();
        int nbEtapes = m.nbEtapes() + 2;
        int[] tabJetonsGuichet = new int[m.nbGuichets()];
        int indiceGuichet = -1;

        for (Etape e : m) {
            if (e.estUnGuichet()) {
                ++indiceGuichet;
                tabJetonsGuichet[indiceGuichet] = ((Guichet) e).getNbJetons();
            }
        }

        this.cl_manager.setClients(this.start_simulation(nbEtapes, nbGuichets, this.nbClients, tabJetonsGuichet));
        int[] emplacement = this.ou_sont_les_clients(nbEtapes, this.nbClients);
        this.notifierObservateurs();

        int rang;
        int numeroEtape;
        for(Client c : cl_manager) {
            rang = 0;

            for(int i = 1; i <= this.nbClients; ++i) {
                if (emplacement[i] == c.getCustomer_nb())
                    this.cl_manager.allerA(c.getCustomer_nb(), m.getEntry(), rang % (this.nbClients + 1) + 1);
                ++rang;
            }
        }

        int k;
        int i;
        int nbClientsEtape;
        int saveRang;
        try {
            while (this.nbClients != emplacement[this.nbClients + 1]) {
                try {
                    Thread.sleep(815);
                } catch (InterruptedException excp) {
                    kit.killPID(this.cl_manager);
                    notifierObservateurs();
                    cl_manager.reset();
                    FabriqueNumero.getInstance().reset();
                    Thread.currentThread().interrupt();
                }
                emplacement = this.ou_sont_les_clients(nbEtapes, this.nbClients);
                this.notifierObservateurs();

                for (Client c : cl_manager) {

                    rang = 0;
                    nbClientsEtape = 0;
                    saveRang = 0;

                    for (k = 0; k < emplacement.length; ++k) {
                        i = emplacement[k];
                        if (rang != 0 && rang % (this.nbClients + 1) != 0) {
                            if (rang / (this.nbClients + 1) == 1) {
                                if (i == c.getCustomer_nb()) {
                                    this.cl_manager.allerA(c.getCustomer_nb(), m.getExit(), rang % (this.nbClients + 1));
                                }
                            } else if (rang / (this.nbClients + 1) == 0) {
                                if (i == c.getCustomer_nb() && rang <= saveRang + nbClientsEtape) {
                                    this.cl_manager.allerA(c.getCustomer_nb(), m.getEntry(), rang % (this.nbClients + 1));
                                }
                            } else if (rang <= saveRang + nbClientsEtape && i == c.getCustomer_nb()) {
                                numeroEtape = rang / (this.nbClients + 1);

                                for (Etape st : m) {
                                    if (st.getNo() == numeroEtape) {
                                        this.cl_manager.allerA(c.getCustomer_nb(), st, rang % (this.nbClients + 1));
                                    }
                                }
                            }
                        } else {
                            nbClientsEtape = emplacement[rang];
                            saveRang = rang;
                        }

                        ++rang;
                    }
                }
            }
            try {
                Thread.sleep(1002);
            } catch (InterruptedException excp) {
                kit.killPID(cl_manager);
                cl_manager.reset();
                notifierObservateurs();
                FabriqueNumero.getInstance().reset();
                Thread.currentThread().interrupt();
            }
            emplacement = this.ou_sont_les_clients(nbEtapes, this.nbClients);
            this.notifierObservateurs();

            for (Client c : this.cl_manager) {
                rang = 0;
                nbClientsEtape = 0;
                saveRang = 0;

                for (k = 0; k < emplacement.length; ++k) {
                    i = emplacement[k];
                    if (rang != 0 && rang % (this.nbClients + 1) != 0) {
                        if (rang / (this.nbClients + 1) == 1) {
                            if (i == c.getCustomer_nb()) {
                                this.cl_manager.allerA(c.getCustomer_nb(), m.getExit(), rang % (this.nbClients + 1));
                            }
                        } else if (rang / (this.nbClients + 1) == 0) {
                            if (i == c.getCustomer_nb() && rang <= saveRang + nbClientsEtape) {
                                this.cl_manager.allerA(c.getCustomer_nb(), m.getEntry(), rang % (this.nbClients + 1));
                            }
                        } else if (rang <= saveRang + nbClientsEtape && i == c.getCustomer_nb()) {
                            numeroEtape = rang / (this.nbClients + 1);
                            for (Etape et : m) {
                                if (et.getNo() == numeroEtape)
                                    this.cl_manager.allerA(c.getCustomer_nb(), et, rang % (this.nbClients + 1));
                            }
                        }
                    } else {
                        nbClientsEtape = emplacement[rang];
                        saveRang = rang;
                    }

                    ++rang;
                }
            }
            Thread.sleep(1002);
        }catch(InterruptedException excp){
            kit.killPID(getClientManager());
            nettoyage();
            this.notifierObservateurs();
            FabriqueNumero.getInstance().reset();
        }

        nettoyage();
        kit.killPID(this.getClientManager());
        this.getClientManager().reset();
        FabriqueNumero.getInstance().reset();
        this.notifierObservateurs();
    }

    /**
     * Fonction de soutien de simuler(Monde m)
     * //@param m
     */
    /*private void help_simu(Monde m)
    {

        /*int nbSteps = m.nbEtapes()+2, nbGuich = m.nbGuichets();
        int [] tabJetons = new int[nbGuich];
        int k=0;
        for(Etape e : m)
        {
            if(e.estUnGuichet())
            {
                tabJetons[k] = ((Guichet) e).getNbJetons();
                k+=1;
            }
        }
        this.getClientManager().setClients(this.start_simulation(nbSteps,nbGuich,this.nbClients,tabJetons));

        int size_tab = nbSteps*(nbClients+1);
        int [] tabWhere_client = new int[size_tab];
        tabWhere_client = ou_sont_les_clients(nbSteps, this.getNbClients());
        this.notifierObservateurs();

        System.out.println("Les clients : \t");
        k=0;
        while(k<nbClients-1)
        {
            System.out.print(getClientManager().getClients().get(k).getCustomer_nb()+", ");
            k++;
        }
        System.out.println(getClientManager().getClients().get(k).getCustomer_nb());

        List<Integer> customers = new ArrayList<>(nbClients);
        for(int l = 1; l<tabWhere_client[0]+1; l++)
            customers.add(tabWhere_client[l]);
        getClientManager().setClients(customers);
        */
        /*try
        {
            while(tabWhere_client[(nbClients+1)*m.getNoExit()] != nbClients)
            {
                tabWhere_client = ou_sont_les_clients(m.nbEtapes(), getNbClients());
                this.notifierObservateurs();
                for(k=0; k<=m.nbEtapes()-1; k++){
                    int shift_cust = tabWhere_client[k*(getNbClients()+1)] ;
                    for(int j = 0; j<=shift_cust-1; j++)
                        getClientManager().allerA(tabWhere_client[shift_cust+j+1],m.getStepFromNumber(k),j);
                    System.out.println("Etape "+m.getNameStepNo(k)+" ("+m.getNoStepFromNumber(k) + ") : "+shift_cust+" client"+
                    ((shift_cust ==1 || shift_cust == 0)?"":"s")+" ➡ ");
                    for(int p=0; p<shift_cust-1; p++)
                        System.out.print(tabWhere_client[(k*(getNbClients()+1))+p+1]+ ", ");
                    if(shift_cust != 0)
                        System.out.println(tabWhere_client[(k*(getNbClients()+1))+shift_cust]);
                }
                tabWhere_client = ou_sont_les_clients(nbSteps, nbClients);
                int n = 0, currentStep=0, finalEtapeNb=m.getExit().getNo(), finalEtapeId = (nbClients+1)*m.getExit().getNo();
                while(n < nbSteps*(nbClients+1) ) {

                    if (tabWhere_client[n] != 0 && n!=finalEtapeId)
                    {
                        System.out.print("Etape " + currentStep + " (" + m.getEt_gest().getStep(currentStep) + ") : " + tabWhere_client[n] + " client"+
                                ((tabWhere_client[n] == 1)?"":"s")+": ");
                        int i;
                        for (i = 1; i < tabWhere_client[n] ; i++)
                            System.out.print(tabWhere_client[i+n] + ", ");
                        System.out.println(tabWhere_client[tabWhere_client[n]+n]);

                        List<Integer> clients = new ArrayList<>(tabWhere_client[n]);
                        for (int l = n + 1; l < 1 + n + tabWhere_client[n]; l++)
                            clients.add(tabWhere_client[l]);
                        getCl_manager().setClients(clients);
                        for (Integer clientId : clients)
                            cl_manager.allerA(clientId, m.getStepFromNumber(currentStep), 0);

                    }
                    else if(tabWhere_client[n] == 0 && n!=finalEtapeId)
                    {
                        System.out.println("Etape " + currentStep + " (" + m.getEt_gest().getStep(currentStep).getNom() + ") : " + tabWhere_client[n] + " client");
                    }

                    n +=1+ getNbClients() ;
                    currentStep++;
                }
                if( tabWhere_client[finalEtapeId] != 0 )
                {
                    Etape sortie = m.getExit();
                    System.out.print("Etape "+finalEtapeNb+" ("+sortie.getNom()+") : "+tabWhere_client[finalEtapeId]+" client"
                    +((tabWhere_client[finalEtapeId] == 1)? "":"s")+": ");
                    for(int z = finalEtapeId+1; z<finalEtapeId+tabWhere_client[finalEtapeId]; z++)
                        System.out.print(tabWhere_client[z]+", ");
                    System.out.println(tabWhere_client[tabWhere_client[finalEtapeId]+finalEtapeId]);

                    List<Integer> cls = new ArrayList<>(tabWhere_client[finalEtapeId]);
                    for(int y=finalEtapeId+1; y<1+finalEtapeId+tabWhere_client[finalEtapeId]; y++)
                        cls.add(tabWhere_client[y]);
                    getCl_manager().setClients(cls);
                    for(Integer clientId : cls)
                        cl_manager.allerA(clientId, sortie,0);

                }
                else
                {
                    System.out.print("Etape "+m.getExit().getNo()+" ("+m.getExit().getNom()+") : "+tabWhere_client[finalEtapeId]+" client\n");
                }

                System.out.print("__________________________________________________________\n");
                Thread.sleep(945);
            }
        }
        catch(InterruptedException exc)
        {
            kit.killPID(this.cl_manager);
            this.getClientManager().reset();
            this.notifierObservateurs();
            FabriqueNumero.getInstance().reset();
            Thread.currentThread().interrupt();
        }

    }*/

    public native int[] start_simulation(int nbEtapes, int nbGuichets, int nbClients, int[] tabJetonsGuichet);
    public native int[] ou_sont_les_clients(int nbEtapes, int nbClients);
    public native void nettoyage();

    public ClientManager getClientManager() {
        return cl_manager;
    }
    public int getNbClients() {
        return nbClients;
    }
    public void setNbClients(int nbClients) {
        this.nbClients = nbClients;
        this.cl_manager = new ClientManager(this.getNbClients());
    }
    public Iterator<Client> iterator()
    {
        return this.cl_manager.iterator();
    }
}
