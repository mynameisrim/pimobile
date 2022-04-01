/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.service;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.codename1.uikit.entities.Equipement;
import com.codename1.uikit.entities.Logement;
import com.codename1.uikit.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *
 * @author Yousra
 */
public class LogementService {

    public static LogementService instance = null;
    private final ConnectionRequest req;
    public static boolean resultOk = true;
    public ArrayList<Logement> listLog;
    public ArrayList<Logement> listLogAff ;
    public static LogementService getInstance() {
        if (instance == null) {
            instance = new LogementService();
        }
        return instance;
    }

    public LogementService() {
        req = new ConnectionRequest();
    }

    public boolean ajouterLogement(Logement a, int idUser, int idEquipement) {
        boolean ok = false;
        String url = Statics.BASE_URL + "/hote/logement/json_addlogement/"
                + idUser
                + "/" + a.getTitre()
                + "/" + a.getDescription()
                + "/" + a.getAddresse()
                + "/a"
                + "/" + idEquipement;
        System.out.println(url);
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                String responceData = new String(req.getResponseData());

                System.out.println("Ajout logement | data == " + responceData);
                req.removeResponseListener(this);

            }
        });
        try {
            NetworkManager.getInstance().addToQueueAndWait(req);
            ok = true;
        } catch (Exception e) {
            System.out.println("erreur lors de l'insertion de l admin");
        }
        return ok;

    }

    public boolean updateLogement(Logement a, int idEquipement) {
        boolean ok = false;

        String url = Statics.BASE_URL + "/hote/logement/json_updatelogement/"
                + a.getId()
                + "/" + a.getHote()
                + "/" + a.getTitre()
                + "/" + a.getDescription()
                + "/" + a.getAddresse()
                + "/" + a.getFilename()
                + "/" + idEquipement;
        System.out.println(url);
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                String responceData = new String(req.getResponseData());

                System.out.println("this is update Logement in LogementService | data == " + responceData);
                req.removeResponseListener(this);
            }
        });
        try {
            NetworkManager.getInstance().addToQueueAndWait(req);
            ok = true;
        } catch (Exception ex) {
            System.out.println("this is update Logement in LogementService | " + ex.getMessage());
        }
        return ok;
    }

    public ArrayList<Logement> GetLogementsByUser(int idUser) {
        String url = Statics.BASE_URL + "/hote/logement/LogementAllJSON";
        req.setUrl(url);
        req.setPost(false);
        //String data = new String(req.getResponseData());
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                listLogAff = parseCategories2(new String(req.getResponseData()));
                System.out.println(listLogAff.toString());
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        System.out.println("////////////////////");
        System.out.println(listLogAff);
        return listLogAff;
    }

    public ArrayList<Logement> parseCategories2(String jsonText) {
        try {
            listLog = new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");

            //Parcourir la liste des tâches Json
            int i = 0;
            for (Map<String, Object> obj : list) {
                i++;
                  Logement l = new Logement();
                       
                System.out.println(i);
                float id = Float.parseFloat(obj.get("id").toString());
                String titre = String.valueOf(obj.get("titre"));
                String description = String.valueOf(obj.get("description"));
                String addresse = String.valueOf(obj.get("addresse"));
                System.out.println("------------------------");
                System.out.println(obj);
                 l.setTitre(titre);
                 l.setDescription(description);
                 l.setAddresse(addresse);
                 listLog.add(l);
                 System.out.println(listLog);
              //  ArrayList<Equipement> equipement = new ArrayList<>();
               // String equipmentItem = String.valueOf(obj.get("equipements"));

               //String nom = equipmentItem.substring(equipmentItem.indexOf("nom=")+4, equipmentItem.lastIndexOf("}"));
               //Equipement e = new Equipement(1, nom);
               //equipement.add(e);
                //Logement a = new Logement((int)id, titre, description, addresse, idUser, equipement, filename);
                /* a.setId((int) id);
                        a.setTitre(titre);
                        a.setDescription(description);
                        a.setAddresse(addresse);
                        a.setHote((int)idUser);
                        a.setEquipement(equipement);
                        a.setFilename(filename);*/
                System.out.println(id + " " + titre + " " + description + " " + addresse + " " );
                //listLogAff.add(new Logement(titre, description, addresse));
            }
        } catch (IOException ex) {

        }

        return listLog;
    }

  

    public Logement DetailLogement(int id) {
        Logement a = new Logement();
        String url = Statics.BASE_URL + "/hote/logement/LogementByIdJSON/" + id;
        req.setUrl(url);
        String data = new String(req.getResponseData());
        req.addResponseListener((evt) -> {
            JSONParser json = new JSONParser();
            try {
                Map<String, Object> MapLogements = json.parseJSON(new com.codename1.io.CharArrayReader(data.toCharArray()));

                String titre = String.valueOf(MapLogements.get("titre"));
                String description = String.valueOf(MapLogements.get("description"));
                String addresse = String.valueOf(MapLogements.get("addresse"));
                String filename = String.valueOf(MapLogements.get("filename"));
                ArrayList<Equipement> equipement = (ArrayList<Equipement>) MapLogements.get("equipements");

               // float hote = Float.parseFloat(MapLogements.get("hote").toString());
                //float id_equipement = Float.parseFloat(MapLogements.get("id_equipement").toString());

                a.setId(id);
                a.setTitre(titre);
                a.setDescription(description);
                a.setAddresse(addresse);
               // a.setHote((int) hote);
                a.setEquipement(equipement);
                a.setFilename(filename);

            } catch (IOException ex) {

                System.out.println("this is detail fucnction in logement service || " + ex.getMessage());
            }
            System.out.println("detail Logement ==> " + data);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);

        return a;

    }

    public boolean deleteLogement(int id) {
        boolean ok = false;
        String url = Statics.BASE_URL + "/hote/logement/json_deletelogement/" + id;
        System.out.println(url);
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                String responceData = new String(req.getResponseData());

                System.out.println("this is delete Logement in LogementService | reponse == " + responceData);
                req.removeResponseListener(this);
            }
        });
        try {
            NetworkManager.getInstance().addToQueueAndWait(req);
            ok = true;
        } catch (Exception e) {
            System.out.println("erreur lors du suppression de Logement");
        }
        return ok;
    }
    
   
        
    

}
