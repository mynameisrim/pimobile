/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.lang.NullPointerException;
/**
 *
 * @author Yousra
 */
public class Logement {
    
    private int id;
    private String titre;
    private String description;
    private String addresse;
    private int hote;
    private ArrayList<Equipement> equipement;
    private String filename;
    public Date createdAt;
    public Date updatedAt;

    public Logement(int id, String titre, String description, String addresse) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.addresse = addresse;
    }

    public Logement(String titre, String description, String addresse) {
        this.titre = titre;
        this.description = description;
        this.addresse = addresse;
    }

    
    public Logement() {}

    public Logement(String titre, String description, String addresse, String filename) {
        this.titre = titre;
        this.description = description;
        this.addresse = addresse;
        this.filename = filename;
    }

    public Logement(int id, String titre, String description, String addresse, int hote, ArrayList<Equipement> equipement, String filename) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.addresse = addresse;
        this.hote = hote;
        this.equipement = equipement;
        this.filename = filename;
    }
    
    public Logement(int id, String titre, String description, String addresse, int hote, ArrayList<Equipement> equipement, String filename, Date createdAt, Date updatedAt) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.addresse = addresse;
        this.hote = hote;
        this.equipement = equipement;
        this.filename = filename;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    public int getHote() {
        return hote;
    }

    public void setHote(int hote) {
        this.hote = hote;
    }

    public ArrayList<Equipement> getEquipement() {
        return equipement;
    }

    public void setEquipement(ArrayList<Equipement> equipement) {
        this.equipement = equipement;
    }


    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    
    
    
    

    
    
        
}
