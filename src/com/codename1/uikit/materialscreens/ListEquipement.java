/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.materialscreens;

import com.codename1.charts.util.ColorUtil;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.SwipeableContainer;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.entities.Equipement;
import com.codename1.uikit.entities.Logement;
import com.codename1.uikit.service.EquipementService;
import com.codename1.uikit.service.LogementService;
import java.util.ArrayList;

/**
 *
 * @author Yousra
 */
public class ListEquipement extends SideMenuBaseForm{

    ArrayList<Equipement> list = new ArrayList<>();
    public ListEquipement(Resources res){
        super(BoxLayout.y());
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
        
       
        
       
       

        Container remainingTasks = BoxLayout.encloseY(
                        new Label("", "CenterTitle")
                      
                );
        remainingTasks.setUIID("RemainingTasks");
        Container completedTasks = BoxLayout.encloseY(
                        new Label("", "CenterTitle")        );
        completedTasks.setUIID("CompletedTasks");
        Container titleCmp = BoxLayout.encloseY(
                        FlowLayout.encloseIn(menuButton),
                        BorderLayout.centerAbsolute(
                                BoxLayout.encloseY(
                                    new Label("Jennifer Wilson", "Title"),
                                    new Label("UI/UX Designer", "SubTitle")
                                )
                                
                            ).add(BorderLayout.WEST, profilePicLabel)
                );
        
        
       
        tb.setTitleComponent(titleCmp);
        setupSideMenu(res);
        refreshList();
        
        
        Button btnValider = new Button("Ajouter Equipement");        
        
        TextField tfNom = new TextField("", "Nom");
        tfNom.getAllStyles().setFgColor(ColorUtil.BLACK);
       
      
        Container contLogement = new Container(BoxLayout.y());
        
        addAll(GridLayout.encloseIn(2, tfNom,btnValider));
        
        btnValider.addActionListener((evt) -> {
            System.out.println(tfNom.getText());
            Equipement eq = new Equipement();
            eq.setNom(tfNom.getText());
            //list.add(eq);
            EquipementService.getInstance().ajouterEquipement(eq);
            refreshList();
            refreshTheme();
            this.revalidate();
        });
        
       
        for(Equipement eq : list) {
            
            contLogement.addComponent(BoxLayout.encloseY(createRankWidget(eq, res)));
        }
         Container container1 = BoxLayout.encloseY(contLogement);
            addAll(container1);
    }
    @Override
    protected void showOtherForm(Resources res) {
    }
    
    
     public Container addEqItem(Equipement eq, Resources res) {

        Container holder = new Container(BoxLayout.x());
        
        Container All = new Container(BoxLayout.y());

        Label Titre = new Label(eq.getNom());
        Label Id = new Label(String.valueOf(eq.getId()));
        
       
        Component lineseparator = createLineSeparator(0xeeeeee);

        
        GridLayout gr = new GridLayout(1, 2);
              gr.autoFit();
              Container details = new Container(gr);
              details.addComponent(GridLayout.encloseIn(2, Id,Titre));
        

        //holder.addAll(details);
        holder.addAll(BoxLayout.encloseY(details));
        
     
        All.addAll(holder, lineseparator);
        
        

        return All;
    }
     
     
     
     
     public final SwipeableContainer createRankWidget(Equipement eq, Resources res) {
        MultiButton button = new MultiButton(String.valueOf(eq.getId()));
        button.setTextLine2(eq.getNom());
        return new SwipeableContainer(FlowLayout.encloseCenterMiddle(createButtons(eq,res)),addEqItem(eq, res));
    }
    private Container createButtons(Equipement eq,Resources res) {
        Container slider = new Container();
        Font fnt = Font.createTrueTypeFont("native:MainLight", "native:MainLight").
                derive(Display.getInstance().convertToPixels(5, true), Font.STYLE_PLAIN);
        Style s = new Style(0xffff33, 0, fnt, (byte) 0);
        FloatingActionButton edit = FloatingActionButton.createFAB(FontImage.MATERIAL_EDIT);
        FloatingActionButton delete = FloatingActionButton.createFAB(FontImage.MATERIAL_DELETE);
        
        
        edit.addActionListener((evt) -> {
            new EditEquipement(eq,res,this).show();
        });
        
        delete.addActionListener((evt) -> {
            EquipementService.getInstance().deleteEquipement((int)eq.getId());
            refreshList();     
            revalidate();
            this.refreshTheme();

             ToastBar.getInstance().setPosition(BOTTOM);
                    ToastBar.Status status = ToastBar.getInstance().createStatus();
                    //status.setShowProgressIndicator(true);
                    Image valid;
                    valid = res.getImage("valider.jpg");
                    status.setIcon(valid.scaledSmallerRatio(Display.getInstance().getDisplayWidth() / 10, Display.getInstance().getDisplayWidth() / 15));
                    status.setMessage("equipement a été supprimer avec succès ");
                    status.setExpires(10000);  // only show the status for 3 seconds, then have it automatically clear
                    status.show();
                    //  iDialog.dispose(); //NAHIW LOADING BAED AJOUT
                    refreshTheme();

        });
        
        slider.addAll(edit, delete);
        return slider;
    }
    public void refreshList() {
        //list.add(new Equipement(1, "aaaaaaaa"));
        //list.add(new Equipement(2, "bbbbbbbb"));
        //list.add(new Equipement(3, "cccccccc"));
       
        list = EquipementService.getInstance().GetAllEquipement();
}
    
    public void addItemToList(Equipement e){
        list.add(e);
        
    }

}
