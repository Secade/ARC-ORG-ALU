package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MainController {
    @FXML
    private MediaView mainVideo;

    @FXML
    private TextField decimalInput,exponentInput;

    @FXML
    private ComboBox signBox,signBoxExpo;

    @FXML
    private Label enterLbl,hexOutput,signOutput,combiOutput,exponentOutput,man1Output,man2Output,man3Output,man4Output,man5Output,decimalInvalid,exponentInvalid;

    @FXML
    private Button enterBtn;

    private static final Pattern decimalPattern = Pattern.compile("[0-9]+?\\.?[0-9]+?");
    private static final Pattern decimalPattern2 = Pattern.compile("[0-9]+");
    private static final Pattern exponentPattern = Pattern.compile("[0-9]+");

    private int additionalBits;
    private int sign;
    private boolean infinityCheck;
    private boolean nanCheck;
    private String MSD;
    private String exponentBit;
    private String input;
    private String combi;
    private String exponent2;
    private String ECB;
    private ArrayList binaryList;

    StringBuilder output1 = new StringBuilder("0000000000");
    StringBuilder output2 = new StringBuilder("0000000000");
    StringBuilder output3 = new StringBuilder("0000000000");
    StringBuilder output4 = new StringBuilder("0000000000");
    StringBuilder output5 = new StringBuilder("0000000000");

    public void initialize(){
        binaryList = new ArrayList();
        additionalBits=0;
        sign=0;
        infinityCheck=false;
        nanCheck=false;

        signBox.getItems().addAll("+","-");
        signBoxExpo.getItems().addAll("+","-");

        MediaPlayer mediaPlayer = new MediaPlayer(new Media(getClass().getResource("/media/mainVideo.mp4").toExternalForm()));
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setMute(true);
        mediaPlayer.setCycleCount(mediaPlayer.INDEFINITE);
        mainVideo.setMediaPlayer(mediaPlayer);
        mediaPlayer.setOnReady(()->{

        });

        decimalInput.setOnKeyPressed(event -> {
            decimalInvalid.setOpacity(0.0);
        });

        exponentInput.setOnKeyPressed(event -> {
            exponentInvalid.setOpacity(0.0);
        });

        enterBtn.setOnMouseEntered(event -> {
            enterLbl.setTextFill(Color.web("#323232"));
        });

        enterBtn.setOnMouseExited(event -> {
            enterLbl.setTextFill(Color.web("#FFFFFF"));
        });

        enterBtn.setOnAction(event -> {
            additionalBits=0;
            infinityCheck=false;
            nanCheck=false;
            if(decimalPattern.matcher(decimalInput.getCharacters()).matches()||decimalPattern2.matcher(decimalInput.getCharacters()).matches()||decimalInput.getText().compareToIgnoreCase("NaN")==0){
                if(decimalInput.getText().contains(".")&&decimalInput.getText().length()<16){
                    System.out.println("IF 1");
                    additionalBits=decimalInput.getText().length()-decimalInput.getText().indexOf(".")-1;
                    System.out.println(additionalBits);
                    String test =decimalInput.getText().replace(".","");
                    System.out.println(test);
                    input=String.format("%016d",Long.parseLong(test));

                }else if((decimalInput.getText().contains(".")&&decimalInput.getText().length()>16)){
                    System.out.println("IF 2");
                    additionalBits=-decimalInput.getText().indexOf(".")+16;
                    System.out.println(additionalBits);
                    String addOn=insertString(decimalInput.getText(),".",15);
                    System.out.println(addOn);
                    String[] tempStr=addOn.split("\\.");
                    System.out.println(tempStr[0]);
                    System.out.println(tempStr[1]);
                    if(Integer.parseInt(tempStr[1].charAt(0)+"")>5){
                        input=String.format("%016d",Long.parseLong(tempStr[0])+1);
                        System.out.println("IF ELSE 1");
                    }else if (Integer.parseInt(tempStr[1].charAt(0)+"")==5){
                        if(Integer.parseInt(tempStr[0].charAt(15)+"")%2==0){
                            input=String.format("%016d",Long.parseLong(tempStr[0]));
                            System.out.println("IF ELSE 2.1");
                        }else if(Integer.parseInt(tempStr[0].charAt(15)+"")%2==1){
                            input=String.format("%016d",Long.parseLong(tempStr[0])+1);
                            System.out.println("IF ELSE 2.2");
                        }
                    }else {
                        input=String.format("%016d",Long.parseLong(tempStr[0]));
                        System.out.println("IF ELSE 3");
                    }
                }
                else if(decimalInput.getText().length()>16){
                    System.out.println("IF 3");
                    String addOn=insertString(decimalInput.getText(),".",15);
                    System.out.println(addOn);
                    String[] tempStr=addOn.split("\\.");
                    additionalBits=-tempStr[1].length();
                    System.out.println(additionalBits);
                    System.out.println(tempStr[0]);
                    System.out.println(tempStr[1]);
                    if(Integer.parseInt(tempStr[1].charAt(0)+"")>5){
                        input=String.format("%016d",Long.parseLong(tempStr[0])+1);
                        System.out.println("IF ELSE 1");
                    }else if (Integer.parseInt(tempStr[1].charAt(0)+"")==5){
                        if(Integer.parseInt(tempStr[0].charAt(15)+"")%2==0){
                            input=String.format("%016d",Long.parseLong(tempStr[0]));
                            System.out.println("IF ELSE 2.1");
                        }else if(Integer.parseInt(tempStr[0].charAt(15)+"")%2==1){
                            input=String.format("%016d",Long.parseLong(tempStr[0])+1);
                            System.out.println("IF ELSE 2.2");
                        }
                    }else {
                        input=String.format("%016d",Long.parseLong(tempStr[0]));
                        System.out.println("IF ELSE 3");
                    }
                }
                else if (decimalInput.getText().compareToIgnoreCase("NaN")==0){
                    System.out.println("IF 4");
                    nanCheck=true;
                    input = String.format("%016d",0);
                    System.out.println("NAN DETECTED");
                }else if ((Integer.parseInt(exponentInput.getText())>399&&signBoxExpo.getValue()=="+")||(Integer.parseInt(exponentInput.getText())>398&&signBoxExpo.getValue()=="-")){
                    System.out.println("IF 5");
                    infinityCheck=true;
                    System.out.println("Infinity DETECTED");
                }else {
                    System.out.println("IF 6");
                    input = String.format("%016d", Long.parseLong(decimalInput.getText()));
                    System.out.println(input);
                }
                System.out.println("DECIMAL CORRECT");

                getSign();
                convertToBinary();
                getExponent();
                getCombi();
                getECB();
                getOutput1();
                getOutput2();
                getOutput3();
                getOutput4();
                getOutput5();
                output();
            }else {
                System.out.println("DECIMAL INVALID");
                decimalInvalid.setOpacity(1.0);
            }
            if(exponentPattern.matcher(exponentInput.getCharacters()).matches()){
                System.out.println("EXPONENT CORRECT");
            }else {
                System.out.println("EXPONENT INVALID");
                exponentInvalid.setOpacity(1.0);
            }
        });
    }

    public static String insertString(String originalString, String stringToBeInserted, int index) {
        String newString = new String();
        for (int i = 0; i < originalString.length(); i++) {
            newString += originalString.charAt(i);
            if (i == index) {
                newString += stringToBeInserted;
            }
        }
        return newString;
    }

    private void convertToBinary() {
        char[] inputList = input.toCharArray();
        binaryList.clear();
        for(int i=0;i<inputList.length;i++) {
            binaryList.add(String.format("%4s", Integer.toBinaryString(Integer.parseInt(inputList[i] + ""))).replace(' ', '0'));
        }
        System.out.println(binaryList);
        List<String> list1 = binaryList.subList(1,4);
        System.out.println(list1);
        System.out.println(list1.get(0));
        System.out.println(list1.get(0).charAt(3));
    }

    private void getSign(){
        if(signBox.getValue().toString().compareToIgnoreCase("+")==0){
            sign=0;
        }else if(signBox.getValue().toString().compareToIgnoreCase("-")==0){
            sign=1;
        }else if(nanCheck){
            sign=0;
        }
        System.out.println("SIGN: " +sign);
    }

    private void getCombi(){
        MSD=binaryList.get(0).toString();
        if(MSD.compareToIgnoreCase("1000")==0){
            combi="11"+exponent2+0;
        }else if(MSD.compareToIgnoreCase("1001")==0){
            combi="11"+exponent2+1;
        }else if(MSD.compareToIgnoreCase("0")==0) {
            combi=exponent2+"000";
        }
        else {
            combi=exponent2+MSD.substring(1,4);
        }
        if (infinityCheck){
            combi="11110";
        }
        else if (nanCheck){
            combi="11111";
        }
        System.out.println("COMBI: "+combi);
    }

    private void getExponent(){
        if (infinityCheck){
            exponentBit="1111111111";
            exponent2="11";
        }
        else if (nanCheck){
            exponentBit="1111111111";
            exponent2="11";
        }else {
            if (signBoxExpo.getValue() == "+") {
                exponentBit = String.format("%010d", Long.parseLong(Integer.toBinaryString(398 + (Integer.parseInt(exponentInput.getText()) - additionalBits))));
            } else if (signBoxExpo.getValue() == "-") {
                exponentBit = String.format("%010d", Long.parseLong(Integer.toBinaryString(398 - (Integer.parseInt(exponentInput.getText()) - additionalBits))));
            }
            char[] exponentStart = exponentBit.toCharArray();
            exponent2=String.valueOf(exponentStart,0,2);

        }
    }

    private void getECB(){
        char[] exponentStart = exponentBit.toCharArray();
        ECB=String.valueOf(exponentStart,2,8);
        if (infinityCheck){
            ECB="111111";
        }
        else if (nanCheck){
            ECB="111111";
        }
        System.out.println("ECB: "+ECB);
    }

    private void getOutput1() {
        List<String> list1 = binaryList.subList(1, 4);
        parseMantissa(list1, output1);
        if (infinityCheck){
            StringBuilder temp = new StringBuilder("0000000000");
            output1=temp;
        }
        else if (nanCheck){
            StringBuilder temp = new StringBuilder("1111111111");
            output1=temp;
        }
        System.out.println("OUTPUT1 " + output1);
    }

    private void getOutput2()
    {
        List<String> list2 = binaryList.subList(4,7);
        parseMantissa(list2, output2);
        if (infinityCheck){
            StringBuilder temp = new StringBuilder("0000000000");
            output2=temp;
        }
        else if (nanCheck){
            StringBuilder temp = new StringBuilder("1111111111");
            output2=temp;
        }
        System.out.println("OUTPUT3 " + output2);
    }

    private void getOutput3()
    {
        List<String> list3 = binaryList.subList(7,10);
        parseMantissa(list3, output3);
        if (infinityCheck){
            StringBuilder temp = new StringBuilder("0000000000");
            output3=temp;
        }
        else if (nanCheck){
            StringBuilder temp = new StringBuilder("1111111111");
            output3=temp;
        }
        System.out.println("OUTPUT3 " + output3);
    }

    private void getOutput4()
    {
        List<String> list4 = binaryList.subList(10,13);
        parseMantissa(list4, output4);
        if (infinityCheck){
            StringBuilder temp = new StringBuilder("0000000000");
            output4=temp;
        }
        else if (nanCheck){
            StringBuilder temp = new StringBuilder("1111111111");
            output4=temp;
        }
        System.out.println("OUTPUT4 " + output4);
    }

    private void getOutput5()
    {
        List<String> list5 = binaryList.subList(13,16);
        parseMantissa(list5, output5);
        if (infinityCheck){
            StringBuilder temp = new StringBuilder("0000000000");
            output5=temp;
        }
        else if (nanCheck){
            StringBuilder temp = new StringBuilder("1111111111");
            output5=temp;
        }
        System.out.println("OUTPUT5 " + output5);
    }

    private void parseMantissa(List<String> list1, StringBuilder output1) {
        int highcount = 0;
        for (int i = 0; i < list1.size(); i++) {
            if (list1.get(i).equals("1000") || list1.get(i).equals("1001"))
                highcount++;
        }
        output1.setCharAt(2, list1.get(0).charAt(3));
        output1.setCharAt(5, list1.get(1).charAt(3));
        output1.setCharAt(9, list1.get(2).charAt(3));
        switch (highcount) {
            case 0:
                output1.setCharAt(6, '0');

                output1.setCharAt(0, list1.get(0).charAt(1));
                output1.setCharAt(1, list1.get(0).charAt(2));

                output1.setCharAt(3, list1.get(1).charAt(1));
                output1.setCharAt(4, list1.get(1).charAt(2));

                output1.setCharAt(7, list1.get(2).charAt(1));
                output1.setCharAt(8, list1.get(2).charAt(2));
                break;
            case 1:
                output1.setCharAt(6, '1');
                for (int i = 0; i < list1.size(); i++) {
                    if (list1.get(0).equals("1000") || list1.get(0).equals("1001")) {
                        output1.setCharAt(0, list1.get(2).charAt(1));
                        output1.setCharAt(1, list1.get(2).charAt(2));
                        output1.setCharAt(3, list1.get(1).charAt(1));
                        output1.setCharAt(4, list1.get(1).charAt(2));
                        output1.setCharAt(7, '1');
                        output1.setCharAt(8, '0');
                    } else if (list1.get(1).equals("1000") || list1.get(1).equals("1001")) {
                        output1.setCharAt(0, list1.get(0).charAt(1));
                        output1.setCharAt(1, list1.get(0).charAt(2));
                        output1.setCharAt(3, list1.get(2).charAt(1));
                        output1.setCharAt(4, list1.get(2).charAt(2));
                        output1.setCharAt(7, '0');
                        output1.setCharAt(8, '1');
                    } else {
                        output1.setCharAt(0, list1.get(0).charAt(1));
                        output1.setCharAt(1, list1.get(0).charAt(2));
                        output1.setCharAt(3, list1.get(1).charAt(1));
                        output1.setCharAt(4, list1.get(1).charAt(2));
                        output1.setCharAt(7, '0');
                        output1.setCharAt(8, '0');
                    }
                }
                break;
            case 2:
                output1.setCharAt(6, '1');
                output1.setCharAt(7, '1');
                output1.setCharAt(8, '1');
                System.out.println(list1.get(0));
                for (int i = 0; i < list1.size(); i++) {
                    if (!(list1.get(0).equals("1000") || list1.get(0).equals("1001"))) {
                        output1.setCharAt(0, list1.get(0).charAt(1));
                        output1.setCharAt(1, list1.get(0).charAt(2));
                        output1.setCharAt(3, '1');
                        output1.setCharAt(4, '0');
                        System.out.println("Goes here1");
                    }
                    else if(!(list1.get(1).equals("1000") || list1.get(1).equals("1001")))
                    {
                        output1.setCharAt(0, list1.get(1).charAt(1));
                        output1.setCharAt(1, list1.get(1).charAt(2));
                        output1.setCharAt(3, '0');
                        output1.setCharAt(4, '1');
                        System.out.println("Goes here2");
                    }
                    else
                    {
                        output1.setCharAt(0, list1.get(2).charAt(1));
                        output1.setCharAt(1, list1.get(2).charAt(2));
                        output1.setCharAt(3, '0');
                        output1.setCharAt(4, '0');
                    }
                }
                break;
            case 3:
                output1.setCharAt(6, '1');
                output1.setCharAt(0, '0');
                output1.setCharAt(1, '0');
                output1.setCharAt(3, '1');
                output1.setCharAt(4, '1');
                output1.setCharAt(7, '1');
                output1.setCharAt(8, '1');
                break;
        }
    }

    private String hexOutput(){
        String hex = sign+combi+ECB+output1+output2+output3+output4+output5;
        System.out.println(hex);
        ArrayList<String> hexOut = new ArrayList<>();
        StringBuilder str = new StringBuilder();
        for(int i=1;i<=hex.length();i++){
            if(i%4==0){
                System.out.println("["+(i-4)+","+(i)+"]");
                System.out.println(hex.substring(i-4,i));
                hexOut.add(Integer.toHexString(Integer.parseInt(hex.substring(i-4,i),2)));
                System.out.println(Integer.toHexString(Integer.parseInt(hex.substring(i-4,i),2)));
            }
        }
        for (int z=0;z<hexOut.size();z++){
            str.append(hexOut.get(z));
        }
        return str.toString().toUpperCase();
    }

    private void output(){
        signOutput.setText(sign+"");
        combiOutput.setText(combi);
        exponentOutput.setText(ECB);
        man1Output.setText(output1+"");
        man2Output.setText(output2+"");
        man3Output.setText(output3+"");
        man4Output.setText(output4+"");
        man5Output.setText(output5+"");
        hexOutput.setText(hexOutput());
    }
}