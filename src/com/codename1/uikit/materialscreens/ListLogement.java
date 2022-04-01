/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.materialscreens;

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
import com.codename1.uikit.service.LogementService;
import java.util.ArrayList;

/**
 *
 * @author Yousra
 */
public class ListLogement extends SideMenuBaseForm{

    ArrayList<Logement> list = new ArrayList<>();
    public ListLogement(Resources res){
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

        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        Container titleCmp = BoxLayout.encloseY(
                        FlowLayout.encloseIn(menuButton),
                        BorderLayout.centerAbsolute(
                                BoxLayout.encloseX(BoxLayout.encloseY(
                                    new Label("Jennifer Wilson", "Title"),
                                    new Label("UI/UX Designer", "SubTitle")
                                ))
                                
                            ).add(BorderLayout.WEST, profilePicLabel),fab
                );
        
        
       
        tb.setTitleComponent(titleCmp);
        setupSideMenu(res);
        fab.addActionListener((evt) -> { new AddLogement( res,this).show();
        });
        refreshList();
        
        Container contLogement = new Container();
        for(Logement log : list) {
            
            //String urlImage = "file///C:/Users/ahmed/OneDrive/Bureau/MEEE/aaa/"+log.getFilename();
            /*String urlImage = "C:/Users/ahmed/OneDrive/Bureau/MEEE/aaa/"+log.getFilename();
            
            Image placeHolder = Image.createImage(300,300);
            EncodedImage enc = EncodedImage.createFromImage(placeHolder,false);
            URLImage urlimg = URLImage.createToStorage(enc, urlImage, urlImage, URLImage.RESIZE_SCALE);
            
            urlimg.fetch();
            
            
          
            ScaleImageLabel imagePub = new ScaleImageLabel(urlimg);
            Container containerImg = new Container();
            imagePub.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
            Image profilePic1 = res.getImage("user-picture.jpg");*/
            contLogement.addComponent(createRankWidget(log, res));
            
            
            
        }
        Container container1 = BoxLayout.encloseY(contLogement);
        addAll(container1);
    }
    @Override
    protected void showOtherForm(Resources res) {
    }
    
    
     public Container addLogItem(Logement log, Resources res) {

        Container holder = new Container(BoxLayout.x());
        Container details = new Container();
        Container All = new Container(BoxLayout.y());

        Label Titre = new Label(log.getTitre());
        Label Description = new Label(log.getDescription());
        Label Adresse = new Label(log.getAddresse());
        
       /* String noms = "";
         for (Equipement eq : log.getEquipement()) {
             noms+=eq.getNom()+" ";
         }
        */
       
        Component lineseparator = createLineSeparator(0xeeeeee);

        details= BoxLayout.encloseYCenter(Titre, Description, Adresse  );

        //EncodedImage enc = EncodedImage.createFromImage(MyApplication.theme.getImage("load.jpg"), false);
      //  Image img = res.getImage("aaaa.png");
       // ImageViewer image = new ImageViewer(img);
        //holder.addAll(image, details);
        holder.addAll(LayeredLayout.encloseIn(GridLayout.encloseIn(2,details)));
        
        
        //edit.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
        //delete.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
        //edit.getAllStyles().setMargin(LEFT, holder.getPreferredH() - edit.getPreferredH() / 2);
        //delete.getAllStyles().setMargin(RIGHT, holder.getPreferredH() - delete.getPreferredH() / 2);
        //buttons.addAll(GridLayout.encloseIn(2,edit,delete));
        All.addAll(holder, lineseparator);
        
        

        return All;
    }
     
     
     
     
     public final SwipeableContainer createRankWidget(Logement log, Resources res) {
        MultiButton button = new MultiButton(log.getTitre());
        button.setTextLine2(log.getDescription());
        button.setTextLine3(log.getAddresse());
        return new SwipeableContainer(FlowLayout.encloseCenterMiddle(createButtons(log,res)),addLogItem(log, res));
    }
    private Container createButtons(Logement log,Resources res) {
        Container slider = new Container();
        Font fnt = Font.createTrueTypeFont("native:MainLight", "native:MainLight").
                derive(Display.getInstance().convertToPixels(5, true), Font.STYLE_PLAIN);
        Style s = new Style(0xffff33, 0, fnt, (byte) 0);
        FloatingActionButton edit = FloatingActionButton.createFAB(FontImage.MATERIAL_EDIT);
        FloatingActionButton delete = FloatingActionButton.createFAB(FontImage.MATERIAL_DELETE);
        
        
        edit.addActionListener((evt) -> {
            new EditLogement(log,res,this).show();
        });
        
        delete.addActionListener((evt) -> {
            LogementService.getInstance().deleteLogement(log.getId());
            refreshList();     
            revalidate();
            this.refreshTheme();

             ToastBar.getInstance().setPosition(BOTTOM);
                    ToastBar.Status status = ToastBar.getInstance().createStatus();
                    //status.setShowProgressIndicator(true);
                    Image valid;
                    valid = res.getImage("valider.jpg");
                    status.setIcon(valid.scaledSmallerRatio(Display.getInstance().getDisplayWidth() / 10, Display.getInstance().getDisplayWidth() / 15));
                    status.setMessage("Logement a été supprimer avec succès ");
                    status.setExpires(10000);  // only show the status for 3 seconds, then have it automatically clear
                    status.show();
                    //  iDialog.dispose(); //NAHIW LOADING BAED AJOUT
                    refreshTheme();
        });
        
        slider.addAll(edit, delete);
        return slider;
    }
    public void refreshList() {
    /*list.add(new Logement(1, "titre", "aaaaaaaa", "aaaaaaaa", 1, 2, "aaaa.png"));
        list.add(new Logement(1, "titre", "aaaaaaaa", "aaaaaaaa", 1, 2, "aaaa.png"));
        list.add(new Logement(1, "titre", "aaaaaaaa", "aaaaaaaa", 1, 2, "aaaa.png"));
        list.add(new Logement(1, "titre", "aaaaaaaa", "aaaaaaaa", 1, 2, "aaaa.png"));
        list.add(new Logement(1, "titre", "aaaaaaaa", "aaaaaaaa", 1, 2, "aaaa.png"));   */
        list = LogementService.getInstance().GetLogementsByUser(2);
}

}
