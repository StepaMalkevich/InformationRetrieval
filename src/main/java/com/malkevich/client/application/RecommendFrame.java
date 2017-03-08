package com.malkevich.client.application;

import com.malkevich.client.connect.Connection;
import com.malkevich.server.model.Item;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Stepan on 24.04.16.
 */
public class RecommendFrame extends JFrame implements ActionListener {
    private JTextField inputtextField;
    private JLabel outputLabel;
    private JLabel titleLabel;

    public RecommendFrame() throws IOException {
        this.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("src/main/java/com/malkevich/server/utilities/background3.jpg")))));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        String html1 = "<html>\n" +
                "<head>\n" +
                "  <title>My first styled page</title>\n" +
                "  <style type=\"text/css\">\n" +
                "  body {\n" +
                "    font-family: Georgia, \"Times New Roman\",\n" +
                "          Times, serif;\n" +
                "    color: purple;}\n" +
                "  h1 {\n" +
                "    font-family: Helvetica, Geneva, Arial,\n" +
                "          SunSans-Regular, sans-serif }\n" +
                "\n" +
                ".one {background:#FFF4ED}\n" +
                ".one h1 {\n" +
                "  font-family: 'Comfortaa', cursive;\n" +
                "  position: relative;\n" +
                "  color: #3CA1D9; \n" +
                "  display: inline-block;\n" +
                "  border-top: 2px solid;\n" +
                "  border-bottom: 2px solid;\n" +
                "  font-size: 2em;\n" +
                "  padding: 11px 60px;\n" +
                "}\n" +
                ".one h1:before, .one h1:after {\n" +
                "  content: \"\"; \n" +
                "  position: absolute;\n" +
                "  top: 0;\n" +
                "  width: 30px;\n" +
                "  height: 100%;\n" +
                "  border-left: 2px solid;\n" +
                "  border-right: 2px solid;\n" +
                "  background: repeating-linear-gradient(180deg, transparent, transparent 2px, #3CA1D9 2px, #3CA1D9 4px);\n" +
                "}\n" +
                ".one h1:before {\n" +
                "  left: 100;\n" +
                "}\n" +
                ".one h1:after {\n" +
                "  right: 1000;\n" +
                "}\n" +
                "\n" +
                "  </style>\n" +
                "  <div class=\"one\"><h1>Imhonet Films</h1></div>\n" +
                "</head>\n" +
                "\n" +
                "<body>";

        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(null);

        JButton buttonToCompute = new JButton("Compute");
        buttonToCompute.setName("compute");
        buttonToCompute.addActionListener(this);

        buttonToCompute.setBounds(698, 146, 71, 45);
        getContentPane().add(buttonToCompute);

        inputtextField = new JTextField("Enter your name");
        inputtextField.setBounds(254, 151, 450, 35);
        getContentPane().add(inputtextField);
        inputtextField.setColumns(20);
        inputtextField.setName("input");

        outputLabel = new JLabel();
        outputLabel.setBounds(300, 100, 750, 500);
        getContentPane().add(outputLabel);
        outputLabel.setName("output");

        titleLabel = new JLabel();
        titleLabel.setBounds(243, 0, 3000, 150);
        getContentPane().add(titleLabel);
        titleLabel.setText(html1);

        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String user_id = inputtextField.getText();
        Connection connection = new Connection();
        List<String> answer = new ArrayList<>();
        try {
            answer = connection.getResult(user_id);
        } catch (ConnectException e1) {
            answer.add("Server is not connected. Please first start the server");
            outputLabel.setText(answer.get(0));
            return;
        } catch (IOException e1) {
            answer.add("The format of your user_id is incorrect. Please enter a valid query");
            outputLabel.setText(answer.get(0));
            return;
        }

        if (answer.size() == 1) {
            if (answer.get(0).equals("nothing")) {
                answer = new ArrayList<>();
                answer.add("The format of your user_id is incorrect. Please enter a valid query");
                outputLabel.setText(answer.get(0));
                return;
            }
        }

        for (String s : answer) {
            System.out.println(s);
        }

        List<Item> items = new ArrayList<>();
        for (String elem : answer) {
            String[] p = elem.split(" : ");
            Item item = new Item(p[0], Double.valueOf(p[1]), Double.valueOf(p[2]));
            items.add(item);
        }

        Collections.sort(items, (i1, i2) -> {
            if (i1.getTrue_score() < i2.getTrue_score()) {
                return 1;
            } else {
                if (i1.getTrue_score() > i2.getTrue_score()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        List<String> true_rang = new ArrayList<>();

        List<String> top_3_true_rang = new ArrayList<>();

        int top = 1;
        for (Item item : items) {
            String film_id = item.getFilm_id();
            if (top <= 3) {
                top_3_true_rang.add(film_id);
                String font = "<font size=\"5\" color=\"green\" face=\"Arial\">" + film_id + "</font>";
                true_rang.add(font);
            } else {
                true_rang.add(film_id);
            }

            top++;
        }

        Collections.sort(items, (i1, i2) -> {
            if (i1.getPred_score() < i2.getPred_score()) {
                return 1;
            } else {
                if (i1.getPred_score() > i2.getPred_score()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        List<String> pred_rang = new ArrayList<>();

        for (Item item : items) {
            String f_id = item.getFilm_id();
            if (top_3_true_rang.contains(f_id)){
                String font = "<font size=\"5\" color=\"green\" face=\"Arial\">" + f_id + "</font>";
                pred_rang.add(font);
            } else {
            pred_rang.add(f_id);
            }
        }

        String html = "<html>Список рекомендованных фильмов:    (true_rank : pred_rank) <ul>";

        for (int i = 0; i < true_rang.size(); i++) {
            html = html + " <li> " + true_rang.get(i) + "    " + pred_rang.get(i);

            if (i == 15){
                break;
            }
        }


        html = html + "</ul>";
        System.out.println(answer);
        outputLabel.setText(html);
    }
}
