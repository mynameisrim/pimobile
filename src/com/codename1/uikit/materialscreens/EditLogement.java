/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.materialscreens;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
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
import com.codename1.uikit.entities.Equipement;
import com.codename1.uikit.entities.Logement;
import com.codename1.uikit.service.LogementService;
import java.util.ArrayList;

/**
 *
 * @author Yousra
 */
public class EditLogement extends SideMenuBaseForm {

    ArrayList<Equipement> liste = new ArrayList<>();

    public EditLogement(Logement log, Resources res, Form previous) {

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

        setTitle("Modifier logement");

        TextField tftitle = new TextField(log.getTitre(), "titre");
        tftitle.getAllStyles().setFgColor(ColorUtil.BLACK);
        TextField tfadresse = new TextField(log.getAddresse(), "Adresse");
        tfadresse.getAllStyles().setFgColor(ColorUtil.BLACK);
        TextField tfdescription = new TextField(log.getDescription(), "Description");
        tfdescription.getAllStyles().setFgColor(ColorUtil.BLACK);
        Button img1 = new Button("Choisir une image");
        CheckBox multiSelect = new CheckBox("Multi-select");

        ComboBox getCategoryComboBox = new ComboBox();
        ComboBox getCatInt = new ComboBox();
        getCategoryComboBox.setUIID("TextField");
        getCategoryComboBox.addItem("Choose Equipement");
        getCatInt.setUIID("TextField");

        setLayout(BoxLayout.y());
        refreshListEquipement();
        for (Equipement c : liste) {
            getCategoryComboBox.addItem(c.getId() + "-" + c.getNom());

        }
        getCategoryComboBox.addActionListener(e1 -> {
            System.out.println(getCategoryComboBox.getSelectedItem().toString());
        });

        Font fnt = Font.createTrueTypeFont("native:MainLight", "native:MainLight").
                derive(Display.getInstance().convertToPixels(5, true), Font.STYLE_PLAIN);
        Style s = new Style(0xFF33FF, 0, fnt, (byte) 0);
        Button btnValider = new Button(FontImage.createMaterial(FontImage.MATERIAL_CHECK, s).toImage());
        Button btnCancel = new Button(FontImage.createMaterial(FontImage.MATERIAL_CANCEL, s).toImage());

        //fonction ajouter
        btnValider.addActionListener((ActionListener) (ActionEvent evt) -> {
            if ((tftitle.getText().length() == 0) || (tfadresse.getText().length() == 0) || (tfdescription.getText().length() == 0) || (getCategoryComboBox.getSelectedItem().toString().length() == 0)) {
                ToastBar.getInstance().setPosition(BOTTOM);
                ToastBar.Status status = ToastBar.getInstance().createStatus();
                //status.setShowProgressIndicator(true);
                Image valid;
                valid = res.getImage("erreur.jpg");
                status.setIcon(valid.scaledSmallerRatio(Display.getInstance().getDisplayWidth() / 10, Display.getInstance().getDisplayWidth() / 15));
                status.setMessage("Veuiller verifier les champs ou bien cliquer sur annuler! ");
                status.setExpires(10000);  // only show the status for 3 seconds, then have it automatically clear
                status.show();
                //  iDialog.dispose(); //NAHIW LOADING BAED AJOUT
                refreshTheme();
            } else {

                String test = getCategoryComboBox.getSelectedItem().toString();
                log.setTitre(tftitle.getText());
                log.setDescription(tfdescription.getText());
                log.setAddresse(tfadresse.getText());
                //log.setEquipement(Integer.parseInt(test.substring(0, test.indexOf("-"))));

                LogementService.getInstance().updateLogement(log, Integer.parseInt(test.substring(0, test.indexOf("-"))));

                ToastBar.getInstance().setPosition(BOTTOM);
                ToastBar.Status status = ToastBar.getInstance().createStatus();
                //status.setShowProgressIndicator(true);
                Image valid;
                valid = res.getImage("valider.jpg");
                status.setIcon(valid.scaledSmallerRatio(Display.getInstance().getDisplayWidth() / 10, Display.getInstance().getDisplayWidth() / 15));
                status.setMessage("Logement a été modifier avec succès ");
                status.setExpires(10000);  // only show the status for 3 seconds, then have it automatically clear
                status.show();
                //  iDialog.dispose(); //NAHIW LOADING BAED AJOUT
                refreshTheme();
                previous.showBack();

            }
        });

        btnCancel.addActionListener((evt) -> {
            previous.showBack();
        });

        addAll(tftitle, tfadresse, tfdescription, getCategoryComboBox, img1, GridLayout.encloseIn(2, btnValider, btnCancel));

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

    private void refreshListEquipement() {
        liste.add(new Equipement(1, "aaa"));
        liste.add(new Equipement(2, "bbb"));
        liste.add(new Equipement(3, "ccc"));
    }

}
