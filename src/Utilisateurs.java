
public class Utilisateurs {
    private String idUtilisateur;
    private int nbtw;
    private int nbtwssrt;
    private int nbrttot;
    
    public Utilisateurs(String idUtilisateur, int nbtw, int nbtwssrt, int nbrttot) {
        this.idUtilisateur = idUtilisateur;
        this.nbtw = nbtw;
        this.nbtwssrt = nbtwssrt;
        this.nbrttot = nbrttot;
    }
    
    public String getnom() {
    	return idUtilisateur;
    }
   
    public String toString() {
        return "Tweet{" +
                ", idUtilisateur='" + idUtilisateur + '\'' +
                ", nbtw='" + nbtw + '\'' +
                ", nbtwssrt='" + nbtwssrt+ '\'' +
                ", nbrttot='" + nbrttot + '\'' +
                '}';
    }
  
    public int setnbtw() {
    	nbtw++;
    	return nbtw;
    }
    
    public int setnbtwssrt() {
    	nbtwssrt++;
    	return nbtwssrt;
    }
    
    public int setnbrttot() {
    	nbrttot++;
    	return nbrttot;
    }
    
    
    
    
    
    
    
    
    
}
