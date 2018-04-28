import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.sun.java_cup.internal.runtime.Symbol;



public class Puhegen{

    ExcelLukija excelLukija;
    PuheLuoja puheLuoja;
    DataLukija dataLukija;

    String alkuTiedostoSijainti = "\\Kuntavaali vastaukset\\miksi_sinut_pitaisi_valita.csv";
    String alkuTiedostoSijaintiKokonaan;

    public Puhegen(){

        String dataLahde = "";
        Integer sanaMaara = 0;
        Integer lauseMaara = 0;
        //Saadaan tiedoston oikea sijainti tietokoneessa.
        System.out.println("Tervetula vaalipohina ohjelmaan!");
        System.out.println("Luetaanko excel vai muisti? (e/m) HUOM: excel tiedostosta tallennetaan ohjelman oma versio muistiin.");

        Scanner lukija = new Scanner(System.in);

        dataLahde = lukija.next();
        //lukija.close();
        puheLuoja =  new PuheLuoja();
        alkuTiedostoSijaintiKokonaan = System.getProperty("user.dir") + alkuTiedostoSijainti;


        if(dataLahde.equals("e")){

            //Käynnistetään ohjelma lukemalla data.
            this.excelLukija = new ExcelLukija(alkuTiedostoSijaintiKokonaan);
            excelLukija.lueExcel();

            System.out.println("Avataan tiedosto sijainnista : " + alkuTiedostoSijaintiKokonaan);

            excelLukija.dataTallentaja.tallennaHashMap();
            dataLukija = new DataLukija();

            puheLuoja.sanatHashMap = dataLukija.lueHashMapData();

        }else if(dataLahde.equals("m")){
            dataLukija = new DataLukija();
            //puheLuoja.annaSanatHashMap(dataLukija.lueHashMapData()); //TODO jostain syystä ei toimi
            puheLuoja.sanatHashMap = dataLukija.lueHashMapData();
            //System.out.println("Sana olen esiintyy : " +  puheLuoja.seuraavienSanojenYleisyysMaara("tampere") + " kertaa");
        }

        System.out.println("Luodaan puhetta. Anna lauseen sana maara: (-1 lopettaa ohjelman)");

        while(true){

            //lukija = new Scanner(System.in);
            sanaMaara = lukija.nextInt();
            
            if(sanaMaara == -1){
                //Lopetetaan ohjelma.
                //lukija.close();
                return;
            }else{
                //lukija.close();
                System.out.println(puheLuoja.luoPuhetta(sanaMaara));
            }

            

        }

    }

    public static void main(String[] args) {
        Puhegen puhegen = new Puhegen();
        
    }
}