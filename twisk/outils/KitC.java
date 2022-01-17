package twisk.outils;

import twisk.simulation.Client;
import twisk.simulation.ClientManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

//import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class KitC {

    private String operatingSystem;
    private String path;
    private int cpt_lib = FabriqueNumero.getInstance().getNumeroLib();

    public KitC(){
        this.operatingSystem = System.getProperty("os.name").toLowerCase();
    }

    public KitC(String pathTo){
        this.path = pathTo;
        this.operatingSystem = System.getProperty("os.name").toLowerCase();
    }

    /**
     * Préparation de l'environnement de gestion des fichiers C
     */
    public void creerEnvironnement(){
        try {
            Path directories;
            String[] liste;
            int length, k;
            String nom;
            if(this.osUnix()) {
                // création du répertoire twisk sous /tmp. Ne déclenche pas d’erreur si le répertoire existe déjà
                directories = Files.createDirectories(Paths.get("/tmp/twisk"));
                // copie des deux fichiers programmeC.o et def.h depuis le projet sous /tmp/twisk
                liste = new String[]{"programmeC.o", "def.h", "codeNatif.o","delais.c"};
                length = liste.length;
                for (k=0; k < length; k++) {
                    nom = liste[k];
                    /*Path source = Paths.get(getClass().getResource("/codeC/" + nom).getPath());
                    Path newdir = Paths.get("/tmp/twisk/");
                    Files.copy(source, newdir.resolve(source.getFileName()), REPLACE_EXISTING);
                     */
                    InputStream source = Objects.requireNonNull(getClass().getResource("/codeC/" + nom)).openStream();
                    File destination = new File("/tmp/twisk/"+nom) ;
                    this.copier(source, destination);
                }
            }
            else if(this.osWind()){
                directories = Files.createDirectories(Paths.get(System.getProperty("java.io.tmpdir")+"/twisk/"));
                this.path = directories.toString() + "\\";
                liste = new String[]{"programmeC.o", "def.h", "codeNatif.o"};
                length = liste.length;
                for(k=0; k <= length-1; k++){
                    nom = liste[k];
                    /*InputStream source = this.getClass().getResource("/codeC/" + nom).openStream();
                    Path newdir = Paths.get(System.getProperty("java.io.tmpdir")+"/twisk/");
                    Files.copy(source,newdir.resolve(source.toString()));*/
                    InputStream source = Objects.requireNonNull(getClass().getResource("/codeC/" + nom)).openStream();
                    File destination = new File("/tmp/twisk/"+nom) ;
                    this.copier(source, destination);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Création automatique du fichier client.c
     * @param codeC string représentant le toC d'un monde considéré
     */
    public void creerFichier(String codeC){
        FileWriter fileWriter;
        String endLine = System.getProperty("line.separator");
        try{
            if(this.osUnix()) {
                fileWriter = new FileWriter("/tmp/twisk/client.c");
                fileWriter.append(codeC);
                fileWriter.close();
            }
            else if(this.osWind()){
                fileWriter = new FileWriter(new File(this.path+"client.c"));
                fileWriter.append(codeC);
                fileWriter.close();
            }
        }
        catch(IOException exc){
            exc.printStackTrace();
        }
    }

    /**
     * Compilation dynamique du fichier client.c
     */
    public void compiler(){
        Runtime runtime = Runtime.getRuntime();
        try{
            BufferedReader output, error;
            String line;
            if(this.osUnix()){
                for(int k=1; k<3; ++k) {
                    Process p;
                    if (k == 1)
                        p = runtime.exec("gcc -Wall -fPIC -c /tmp/twisk/client.c -o /tmp/twisk/client.o");
                    else p = runtime.exec("gcc -Wall -fPIC -c /tmp/twisk/delais.c -o /tmp/twisk/delais.o");

                    output = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                    while ((line = output.readLine()) != null)
                        System.out.println(line);
                    while ((line = error.readLine()) != null)
                        System.out.println(line);

                    p.waitFor();
                }
            }
            else if(this.osWind()){
                Process p;
                p = runtime.exec("gcc -Wall -fPIC -c " + this.path + "client.c -o " + this.path + "client.o");
                output = new BufferedReader(new InputStreamReader(p.getInputStream()));
                error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                while((line = output.readLine()) != null)
                    System.out.println(line);
                while((line = output.readLine()) != null)
                    System.out.println(line);

                p.waitFor();
            }
        }catch(IOException | InterruptedException excp){
            excp.printStackTrace();
        }
    }

    /**
     * Construction dynamique de la librairie
     */
    public void construireLaLibrairie(){
        Runtime runtime = Runtime.getRuntime();

        String command = "gcc -shared /tmp/twisk/programmeC.o /tmp/twisk/codeNatif.o /tmp/twisk/client.o -o /tmp/twisk/libTwisk"+this.cpt_lib+".so";
        Process p;
        BufferedReader output,error;
        String line;
        try{
            if(this.osUnix()){
                p = runtime.exec(command);
                output = new BufferedReader(new InputStreamReader(p.getInputStream()));
                error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                while ( !( ( line = output.readLine() ) == null ) )
                    System.out.println(line);
                while ((line = error.readLine()) != null)
                    System.out.println(line);
                p.waitFor();
            }
            else if(this.osWind()){
                System.err.println("Windows incompatible avec cette version, on ne peut créer la librairie !");
                System.exit(1);
            }
        }catch(IOException | InterruptedException excp){
            excp.printStackTrace();
        }
    }

    public boolean osUnix(){
        return this.operatingSystem.contains("nix") ||
        this.operatingSystem.contains("nux") ||
        this.operatingSystem.contains("aix");
    }

    public boolean osWind(){
        return this.operatingSystem.contains("win");
    }

    @Override
    public String toString() {
        return "KitC{" +
                "operatingSystem='" + operatingSystem + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public String getPath() {
        return path;
    }

    public int getCpt_lib() {
        return cpt_lib;
    }

    public void killPID(ClientManager clM)
    {
        Runtime r = Runtime.getRuntime();
        try{
            for(Client cl : clM)
            {
                Process p = r.exec("kill -9"+cl.getCustomer_nb());
                p.waitFor();
            }
        }catch(InterruptedException|IOException exc)
        {
            exc.printStackTrace();
        }
    }

    private void copier(InputStream source, File dest) throws IOException {
        InputStream sourceFile = source;
        OutputStream destinationFile = new FileOutputStream(dest) ;
        // Lecture par segment de 0.5Mo
        byte buffer[] = new byte[524288];
        int nbLecture;
        while ((nbLecture = sourceFile.read(buffer)) != -1)
            destinationFile.write(buffer, 0, nbLecture);

        destinationFile.close();
        sourceFile.close();
    }
}
