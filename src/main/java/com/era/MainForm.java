package com.era;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class MainForm extends JFrame {
    private JButton openFileButton;
    private JPanel panelMain;
    private JTextField textFieldForVector;
    private JButton enterVectorButton;
    private JButton buttonVectorFromFile;
    private JTextArea textArea1;
    private JTable table1;


    public Process mainProcess;
    public List<String> vectorList = new ArrayList<>();

    public MainForm() {
        openFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser fc = new JFileChooser();
                    fc.setDialogTitle("Выберите файл");
                    int result = fc.showOpenDialog(panelMain);


                    // Если считали файл
                    if (result == JFileChooser.APPROVE_OPTION) {
                        //Получам файл
                        File file = fc.getSelectedFile();

                        //Для отладки
                        FileReader fileReader = null;
                        fileReader = new FileReader(file);
                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        bufferedReader.lines().forEach(System.out::println);


                        //Обучене
                        mainProcess = new DetermenicForMoreThanTwo(file);
                        mainProcess.study();


                        System.out.println("done");
                        textArea1.append("Обучение завершено!\n");


                    }
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
        enterVectorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String vector = textFieldForVector.getText();
                System.out.println(vector);
                String[] stringMasVector = vector.replaceAll("\\s", " ").split(" ");
                Double[] doubleVector = new Double[stringMasVector.length];
                for (int i = 0; i < doubleVector.length; i++) {
                    doubleVector[i] = Double.parseDouble(stringMasVector[i].replace(',', '.'));
                }


                int maxClass = mainProcess.work(doubleVector);

                if (maxClass == 0) {
                    System.out.println("Невозможно посчитать максимальный потенциал");
                } else {
                    System.out.println("Входной вектор принадлежит " + maxClass + " классу");
                    textArea1.append("Входной вектор принадлежит " + maxClass + " классу\n");
                }


            }
        });
        buttonVectorFromFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {


                    JFileChooser fc = new JFileChooser();
                    fc.setDialogTitle("Выберите файл");
                    int result = fc.showOpenDialog(panelMain);


                    // Если считали файл
                    if (result == JFileChooser.APPROVE_OPTION) {
                        //Получам файл
                        File file = fc.getSelectedFile();


                        Scanner s = new Scanner(file);
                        vectorList = new ArrayList<String>();
                        while (s.hasNext()) {
                            vectorList.add(s.nextLine());
                        }
                        s.close();

                        doStudyList(vectorList);


                    }
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void doStudyList(List<String> vectorList) {
        for (String strVector : vectorList) {

            String vector = strVector;
            System.out.println(vector);
            //textArea1.append(vector + "\n");
            String[] stringMasVector = vector.replaceAll("\\s", " ").split(" ");
            Double[] doubleVector = new Double[stringMasVector.length];
            for (int i = 0; i < doubleVector.length; i++) {
                doubleVector[i] = Double.parseDouble(stringMasVector[i].replace(',', '.'));
            }

            int maxClass = mainProcess.work(doubleVector);

            if (maxClass == 0) {
                System.out.println("Невозможно посчитать максимальный потенциал");
            } else {
                System.out.println("Входной вектор принадлежит " + maxClass + " классу");
                textArea1.append("Входной вектор принадлежит " + maxClass + " классу\n");
            }

        }

    }


    public static void main(String[] args) {

        MainForm form = new MainForm();
        form.setVisible(true);
        form.setContentPane(form.panelMain);
        form.setSize(300, 400);


    }


    public static int howManyClasses(File file) throws FileNotFoundException {
        Map<String, String> listClass = new HashMap<>();
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        bufferedReader.lines().forEach(line -> {
            if (!listClass.containsKey(line.substring(0, 1))) {
                listClass.put(line.substring(0, 1), line.substring(0, 1));
            }
        });
        return listClass.entrySet().size();
    }

}

