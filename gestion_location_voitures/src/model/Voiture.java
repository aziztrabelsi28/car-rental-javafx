package model;


public class Voiture {
    private int idVoiture;
    private String marque;
    private boolean isDispo;
    private String typeVoiture;
    private double prixJour;



    public Voiture(int idVoiture,String marque,boolean isDispo,String typeVoiture,double prixJour){
        this.idVoiture=idVoiture; //idVoiture deja autoinrement dans la base de donne
        this.marque=marque;
        this.isDispo=isDispo;
        this.typeVoiture=typeVoiture;
        this.prixJour=prixJour;
    }


    public int getIdVoiture(){
        return idVoiture;
    }

    public void setIdVoiture(int idVoiture){
        this.idVoiture=idVoiture;
    }

    public String getMarque(){
        return marque;
    }

    public void setMarque(String marque){
        this.marque=marque;
    }


    public boolean getIsDispo(){
        return isDispo;
    }


    public void setIsDispo(boolean isDispo){
        this.isDispo=isDispo;
    }

    public String getTypeVoiture(){
        return typeVoiture;
    }


    public void setTypeVoiture(String typeVoiture){
        this.typeVoiture=typeVoiture;
    }

    public double getPrixJour(){
        return prixJour;
    }

    public void setPrixJour(double prixJour){
        this.prixJour=prixJour;
    }

    public String toString() {
    return "Voiture{id= " + idVoiture + ", marque= " + marque + ", disponible= " + isDispo + ", type= " + typeVoiture + ", prix= " + prixJour + " }";
}




}
