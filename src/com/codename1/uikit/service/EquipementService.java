/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.service;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.codename1.uikit.entities.Equipement;
import com.codename1.uikit.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Yousra
 */
public class EquipementService {
      public static EquipementService instance = null;
    private final ConnectionRequest req;
    public static boolean resultOk = true;
    
public static EquipementService getInstance() {
        if (instance == null) {
            instance = new EquipementService();
        }
        return instance;
    }
 public EquipementService() {
        req = new ConnectionRequest();
    }
 
  public boolean ajouterEquipement(Equipement a) {
        boolean ok = false;
        String url = Statics.BASE_URL + "/hote/equipement/json_addequpement"
                + "/" + a.getNom();
               
        System.out.println(url);
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                String responceData = new String(req.getResponseData());

                System.out.println("Ajout Equipement | data == " + responceData);
                req.removeResponseListener(this);
               
            }
        });
        try {
            NetworkManager.getInstance().addToQueueAndWait(req);
            ok = true;
        } catch (Exception e) {
            System.out.println("erreur lors de l'insertion de l Equipement");
        }
        return ok;

    }
 public boolean updateEquipement(Equipement a) {
        boolean ok = false;


        String url = Statics.BASE_URL + "/hote/equipement/json_updateequipement"
                + "/"+a.getId()
                + "/" + a.getNom();
        System.out.println(url);
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                String responceData = new String(req.getResponseData());

                System.out.println("this is update Equipement in EquipementService | data == " + responceData);
                req.removeResponseListener(this);
            }
        });
        try {
            NetworkManager.getInstance().addToQueueAndWait(req);
            ok = true;
        } catch (Exception ex) {
            System.out.println("this is update Equipement in EquipementtService | " + ex.getMessage());
        }
        return ok;
    }
 
  public ArrayList<Equipement> GetEquipementById(int id) {
        ArrayList<Equipement> list = new ArrayList<>();
        String url = Statics.BASE_URL + "/hote/equipement/id="+id;
        req.setUrl(url);
        req.setPost(false);
        JSONParser json = new JSONParser();
        //String data = new String(req.getResponseData());
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    Map<String, Object> MapEquipement = json.parseJSON(new com.codename1.io.CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String, Object>> ListOfMap = (List<Map<String, Object>>) MapEquipement.get("root");

                     for (Map<String, Object> obj : ListOfMap) {
                        Equipement a = new Equipement();
                        float id = Float.parseFloat(obj.get("id").toString());
                        String nom = String.valueOf(obj.get("nom"));
                       

                        a.setId((int) id);
                        a.setNom(nom);
                        list.add(a);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);

        return list;
    }
  
   public ArrayList<Equipement> GetAllEquipement() {
        ArrayList<Equipement> list = new ArrayList<>();
        String url = Statics.BASE_URL + "/hote/equipement/EquipementAllJSON";
        req.setUrl(url);
        req.setPost(false);
        JSONParser json = new JSONParser();
        //String data = new String(req.getResponseData());
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    Map<String, Object> MapEquipement = json.parseJSON(new com.codename1.io.CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String, Object>> ListOfMap = (List<Map<String, Object>>) MapEquipement.get("root");

                     for (Map<String, Object> obj : ListOfMap) {
                        Equipement a = new Equipement();
                        float id = Float.parseFloat(obj.get("id").toString());
                        String nom = String.valueOf(obj.get("nom"));
                       

                        a.setId((int) id);
                        a.setNom(nom);
                        list.add(a);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);

        return list;
    }
  
  public boolean deleteEquipement(int id) {
        boolean ok = false;
        String url = Statics.BASE_URL + "/hote/equipement/json_deleteequipement/"+id;
        System.out.println(url);
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                String responceData = new String(req.getResponseData());

                System.out.println("this is delete Equipement in EquipementService | reponse == " + responceData);
                req.removeResponseListener(this);
            }
        });
try {
             NetworkManager.getInstance().addToQueueAndWait(req);
             ok = true;
        } catch (Exception e) {
            System.out.println("erreur lors du suppression de Equipement");
        }
       return ok;
    }
  
}
