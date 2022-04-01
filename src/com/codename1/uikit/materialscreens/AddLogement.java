/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.materialscreens;

import com.codename1.capture.Capture;
import com.codename1.charts.util.ColorUtil;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Display;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import com.codename1.components.ToastBar;
import com.codename1.io.FileSystemStorage;
import static com.codename1.ui.Component.BOTTOM;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.ImageIO;
import com.codename1.uikit.entities.Equipement;
import com.codename1.uikit.entities.Logement;
import com.codename1.uikit.service.EquipementService;
import com.codename1.uikit.service.LogementService;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 *
 * @author Yousra
 */
public class AddLogement extends SideMenuBaseForm {

    String imageE = "";
    String filename = "";
    String path = "";
    String namePic;

    public AddLogement(Resources res, Form previous) {

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

        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton),
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY(
                                new Label("Jennifer Wilson", "Title"))
                ).add(BorderLayout.WEST, profilePicLabel)
        );

        setupSideMenu(res);

        //Button btnValider = new Button("Ajouter logement");
        Font fnt = Font.createTrueTypeFont("native:MainLight", "native:MainLight").
                derive(Display.getInstance().convertToPixels(5, true), Font.STYLE_PLAIN);
        Style s = new Style(0xFF33FF, 0, fnt, (byte) 0);
        Button btnValider = new Button(FontImage.createMaterial(FontImage.MATERIAL_CHECK, s).toImage());
        Button btnCancel = new Button(FontImage.createMaterial(FontImage.MATERIAL_CANCEL, s).toImage());
        FloatingActionButton valider = FloatingActionButton.createFAB(FontImage.MATERIAL_CHECK);
        FloatingActionButton cancel = FloatingActionButton.createFAB(FontImage.MATERIAL_CANCEL);
        setTitle("Ajouter logement");

        TextField tftitle = new TextField("", "titre");
        tftitle.getAllStyles().setFgColor(ColorUtil.BLACK);
        TextField tfadresse = new TextField("", "Adresse");
        tfadresse.getAllStyles().setFgColor(ColorUtil.BLACK);
        TextField tfdescription = new TextField("", "Description");
        tfdescription.getAllStyles().setFgColor(ColorUtil.BLACK);
        Button img1 = new Button("Choisir une image");
        CheckBox multiSelect = new CheckBox("Multi-select");
        ArrayList<Equipement> liste = EquipementService.getInstance().GetAllEquipement();

        ComboBox getCategoryComboBox = new ComboBox();
        ComboBox getCatInt = new ComboBox();
        getCategoryComboBox.setUIID("TextField");
        getCategoryComboBox.addItem("Choose Equipement");
        getCatInt.setUIID("TextField");
        setLayout(BoxLayout.y());
        for (Equipement c : liste) {
            getCategoryComboBox.addItem(c.getId() + "-" + c.getNom());

        }
        getCategoryComboBox.addActionListener(e1 -> {
            System.out.println(getCategoryComboBox.getSelectedItem().toString());
        });

        Button btCapture = new Button("capture");
        Label lbPhoto = new Label();
        ImageViewer image = new ImageViewer();

        btCapture.addActionListener((e) -> {
            String hh = "C:/Users/bouka/OneDrive/Desktop/mobile/Equipement+logement/src/pictures/";
            path = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);

            if (path != null) {

                Image img;
                try {
                    img = Image.createImage(path).scaledHeight(500);

                    lbPhoto.setIcon(img);
                    //img1.setText(path);
                    image.setImage(img);

                    Image logo;
                    String imageFile = "";

                    imageFile = FileSystemStorage.getInstance().getAppHomePath() + tftitle.getText() + ".png";
                    filename = imageFile;
                    try (OutputStream os = FileSystemStorage.getInstance().openOutputStream(imageFile)) {
                        System.out.println(imageFile);
                        ImageIO.getImageIO().save(img, os, ImageIO.FORMAT_PNG, 1);
                    } catch (IOException err) {
                    }

                } catch (IOException ex) {
                }

            }
        });

        //fonction ajouter
        btnValider.addActionListener((evt) -> {

            if ((tftitle.getText().length() == 0) || (tfadresse.getText().length() == 0) || (tfdescription.getText().length() == 0) || (getCategoryComboBox.getSelectedItem().toString().length() == 0)) {
                ToastBar.getInstance().setPosition(BOTTOM);
                ToastBar.Status status = ToastBar.getInstance().createStatus();
                //status.setShowProgressIndicator(true);
                Image valid;
                valid = res.getImage("erreur.jpg");
                status.setIcon(valid.scaledSmallerRatio(Display.getInstance().getDisplayWidth() / 10, Display.getInstance().getDisplayWidth() / 15));

                status.setMessage("Vérifier les champs");
                status.setExpires(10000);  // only show the status for 3 seconds, then have it automatically clear

                status.show();
                //  iDialog.dispose(); //NAHIW LOADING BAED AJOUT

                refreshTheme();
            } else {
                String extension = null;
                if (path.lastIndexOf(".") > 0) {
                    extension = path.substring(path.lastIndexOf(".") + 1);
                    StringBuilder hi = new StringBuilder(path);
                    if (path.startsWith("file://")) {
                        hi.delete(0, 7);
                    }
                    int lastIndexPeriod = hi.toString().lastIndexOf(".");
                    String ext = hi.toString().substring(lastIndexPeriod);
                    String hmore = hi.toString().substring(0, lastIndexPeriod - 1);

                    try {
                        namePic = saveFileToDevice(path, ext);
                        System.out.println(namePic);
                    } catch (IOException ex) {

                    } catch (URISyntaxException ex) {

                    }
                }

                String test = getCategoryComboBox.getSelectedItem().toString();

                int idEquipement = Integer.parseInt(test.substring(0, test.indexOf("-")));

                Logement l = new Logement();
                if (filename.length() != 0) {
                    l.setTitre(tftitle.getText());
                    l.setDescription(tfdescription.getText());
                    l.setAddresse(tfadresse.getText());
                    l.setFilename("a");
                } else {
                    l.setTitre(tftitle.getText());
                    l.setDescription(tfdescription.getText());
                    l.setAddresse(tfadresse.getText());
                    l.setFilename("a");
                }
                if (LogementService.getInstance().ajouterLogement(l, 2, idEquipement)) {

                    ToastBar.getInstance().setPosition(BOTTOM);
                    ToastBar.Status status = ToastBar.getInstance().createStatus();
                    //status.setShowProgressIndicator(true);
                    Image valid;
                    valid = res.getImage("valider.jpg");
                    status.setIcon(valid.scaledSmallerRatio(Display.getInstance().getDisplayWidth() / 10, Display.getInstance().getDisplayWidth() / 15));
                    status.setMessage("Logement a été ajouter avec succès ");
                    status.setExpires(10000);  // only show the status for 1 seconds, then have it automatically clear
                    status.show();
                    //  iDialog.dispose(); //NAHIW LOADING BAED AJOUT
                    refreshTheme();
                    previous.showBack();

                } else {
                    ToastBar.getInstance().setPosition(BOTTOM);
                    ToastBar.Status status = ToastBar.getInstance().createStatus();
                    //status.setShowProgressIndicator(true);
                    Image valid;
                    valid = res.getImage("erreur.jpg");
                    status.setIcon(valid.scaledSmallerRatio(Display.getInstance().getDisplayWidth() / 10, Display.getInstance().getDisplayWidth() / 15));

                    status.setMessage("Vérifier les champs");
                    status.setExpires(10000);  // only show the status for 3 seconds, then have it automatically clear

                    status.show();
                    //  iDialog.dispose(); //NAHIW LOADING BAED AJOUT

                    refreshTheme();
                }
                //}

            }

        });

        btnCancel.addActionListener((evt) -> {
            previous.showBack();
        });

        addAll(BoxLayout.encloseY(tftitle, tfadresse, tfdescription, getCategoryComboBox, btCapture, GridLayout.encloseIn(2, btnValider, btnCancel), image));

    }

    private Label createSeparator() {
        Label sep = new Label();
        sep.setUIID("Separator");
        // the separator line  is implemented in the theme using padding and background color, by default labels
        // are hidden when they have no content, this method disables that behavior
        sep.setShowEvenIfBlank(true);
        return sep;
    }

    protected String saveFileToDevice(String hi, String ext) throws IOException, URISyntaxException {
        URI uri;
        try {
            uri = new URI(hi);
            String path = uri.getPath();
            int index = hi.lastIndexOf("/");
            hi = hi.substring(index + 1);
            return hi;
        } catch (URISyntaxException ex) {
        }
        return "hh";
    }

    @Override
    protected void showOtherForm(Resources res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
