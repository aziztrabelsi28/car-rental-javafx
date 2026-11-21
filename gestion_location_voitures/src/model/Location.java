package model;

import java.time.temporal.ChronoUnit;


public class Location {
    private int idLocation;
    private DemandeLocation demandeLocation;


    public Location(int idLocation,DemandeLocation demandeLocation){
        this.idLocation=idLocation;
        this.demandeLocation=demandeLocation;
    }

    public Location(){}


    public int getIdLocation(){
        return idLocation;
    }

    public void setIdLocation(int idLocation){
        this.idLocation=idLocation;
    }

    public DemandeLocation getDemandeLocation(){
        return demandeLocation;
    }

    public void setDemandeLocation(DemandeLocation demandeLocation){
        this.demandeLocation=demandeLocation;
    }

    public double calculerPrixBase(){
        long duree=ChronoUnit.DAYS.between(demandeLocation.getDateDebutLocation(),demandeLocation.getDateFinLocation())+1;
        return demandeLocation.getVoiture().getPrixJour()*duree;
    }


    public double calculerPrixTotaleLocation(){
        double prixBase=calculerPrixBase();
        String typeVoiture = demandeLocation.getVoiture().getTypeVoiture();
        String typeLocation = demandeLocation.getTypeLocation();

        switch (typeVoiture.toLowerCase()) {
            case "luv":
                prixBase *= 1.15;
                break;
            case "sedan":
                prixBase *= 1.05;
                break;
            default:
                prixBase *= 1.0;
                break;
        }

                switch (typeLocation.toLowerCase()) {
            case "premium":
                prixBase *= 1.5;
                break;
            case "week-end":
                prixBase *= 1.2;
                break;
            case "standard":
                prixBase *=1.0;
                break;
        }
        return prixBase;
    }







}
