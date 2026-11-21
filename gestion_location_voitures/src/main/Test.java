package main;

import model.Admin;
import model.Client;
import model.Voiture;
import model.DemandeLocation;
/*import service.GestionVoitures;
import service.GestionClients;
import service.GestionDemandesLocations;*/

import java.time.LocalDateTime;

public class Test {
    public static void main(String[] args){
        //GestionVoitures gestionVoitures=new GestionVoitures();
        //GestionClients gestionClients=new GestionClients();
        //GestionDemandesLocations gestionDemandesLocations=new GestionDemandesLocations();
        Admin ad= new Admin(1,"admin","admin","admin");
        Client cl= new Client(2,"aziz","aziz123","client","aziz","trabelsi",ad.getGestionVoitures(),ad.getGestionDemandesLocations());
        ad.getGestionClients().ajouterClient(cl);
        System.out.println("admin et client cree!!!");
        System.out.println("-----------------------------");
        System.out.println("liste de clients: ");
        ad.getGestionClients().afficherListeClients();
        System.out.println("-----------------------------");
        
        Voiture v1= new Voiture(1, "bmw", true, "sedan", 100);
        Voiture v2= new Voiture(2, "volkswagen", true, "sedan", 100);
        Voiture v3= new Voiture(3, "range rover", false, "SUV", 150);
        Voiture v4= new Voiture(4, "renault", false, "coupe", 80);
        ad.getGestionVoitures().ajouterVoiture(v1);
        ad.getGestionVoitures().ajouterVoiture(v2);
        ad.getGestionVoitures().ajouterVoiture(v3);
        ad.getGestionVoitures().ajouterVoiture(v4);
        
        cl.afficherListeVoituresDispo();
        System.out.println("-----------------------------");

        DemandeLocation dm1=new DemandeLocation(1,cl,v1,LocalDateTime.of(2025,5,15,10,0),LocalDateTime.of(2025,5,18,10,0),"week-end","carte");
        DemandeLocation dm2=new DemandeLocation(2,cl,v4,LocalDateTime.of(2025,5,15,10,0),LocalDateTime.of(2025,5,18,10,0),"week-end","espece");//test voiture non dispo
        ad.getGestionDemandesLocations().ajouterDemandeLocation(dm1);
        ad.getGestionDemandesLocations().ajouterDemandeLocation(dm2);
        ad.getGestionDemandesLocations().afficherListeDemandesLocations();
        ad.accepterDemandeLocation(dm1.getIdDemande(),"en ligne");
        System.out.println("-----------------------------");
        System.out.println("liste des locations:");
        ad.afficherListeLocations();
        cl.afficherFactures();
        
        

        

    }

}
