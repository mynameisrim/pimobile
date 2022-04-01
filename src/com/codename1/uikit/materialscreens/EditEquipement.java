/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.materialscreens;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.Display;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.util.Resources;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.uikit.entities.Equipement;
import com.codename1.uikit.service.EquipementService;
/**
 *
 * @author Yousra
 */
public class EditEquipement extends SideMenuBaseForm{

    public EditEquipement( Equipement equipement,Resources res,Form previous) {
        
        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
        Image profilePic = res.getImage("user-picture.jpg");
        Image mask = res.getImage("round-mask.png");
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label(profilePic, "ProfilePicTitle");
        profilePicLabel.setMask(mask.createMask());

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());
        
       
       

        Container titleCmp = BoxLayout.encloseY(
                        FlowLayout.encloseIn(menuButton),
                        BorderLayout.centerAbsolute(
                                BoxLayout.encloseY(
                                    new Label("Jennifer Wilson", "Title")                                )
                            ).add(BorderLayout.WEST, profilePicLabel)
                );
        
        
        setupSideMenu(res);
        
        
        
        setTitle("Modifier Equipement");
        
        
        
        
         Font fnt = Font.createTrueTypeFont("native:MainLight", "native:MainLight").
                derive(Display.getInstance().convertToPixels(5, true), Font.STYLE_PLAIN);
        Style s = new Style(0xFF33FF, 0, fnt, (byte) 0);
        Button btnValider = new Button(FontImage.createMaterial(FontImage.MATERIAL_CHECK, s).toImage());
        Button btnCancel = new Button(FontImage.createMaterial(FontImage.MATERIAL_CANCEL, s).toImage());
        
        
        
        
        TextField tfNom = new TextField(equipement.getNom(), "Nom");
        tfNom.getAllStyles().setFgColor(ColorUtil.BLACK);
       
        
       
      
       btnValider.addActionListener((evt) -> {
           equipement.setNom(tfNom.getText());
           EquipementService.getInstance().updateEquipement(equipement);
           previous.showBack();
       });
       
       btnCancel.addActionListener((evt) -> {
           previous.showBack();
       });

        addAll(tfNom, GridLayout.encloseIn(2, btnValider,btnCancel));


        

    }

 
    @Override
    protected void showOtherForm(Resources res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
