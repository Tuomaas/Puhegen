
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;



class ExcelLukija{

    Scanner lukija;
    String tiedostoNimi; //Tiedosto jossa on lukematonta ja järjestemätöntä dataa.
    int riveja = 0;      //Montako riviä luettiin yhteensä
    String rivi = "";    //Nyt luettava rivi
    DataTallentaja dataTallentaja; //Tallentaa luetut sanat sql tietokantaan.
    

    public ExcelLukija(String _tiedostoNimi){
        this.tiedostoNimi = _tiedostoNimi;
        dataTallentaja =  new DataTallentaja(this.tiedostoNimi);
    }

    public void lueExcel(){

        riveja = 0;
        
        System.out.println("Avataan tiedosto sijainnista : " + tiedostoNimi);

        try{
        File tiedosto = new File(tiedostoNimi); //Avattava tiedosto
        lukija = new Scanner(tiedosto);         //Input stream tiedostosta
        
        while(lukija.hasNext() && riveja < 10000000){ //Luetaan jos on luettavaa. //TODO MUUTA JOS HALUAT LISÄÄ RIVEJÄ KERRALLA
            rivi = lukija.nextLine(); //Luetaan seuraava rivi tiedostosta

            if(rivi != null){
                riveja +=1;                 //Pidetään kirjaa luetuista riveistä.
                rivi = puhdistaRivi(rivi);  //Poistetaan turvat merkit

                ArrayList<String> lauseet = erotteleLauseet(rivi);  //erotellaan rivin lauseet
                ArrayList<ArrayList<String>> sanat = erotteleSanat(lauseet); //Erotellaan lauseiden sanat  
                dataTallentaja.tallenna(sanat);
            }
        }



        System.out.println("Luettiin yhteensä : " + this.riveja);
        lukija.close();
        }catch(FileNotFoundException e){
            e.toString();
        }
    }

    public String puhdistaRivi(String rivi){

        String puhdistusRegex = "[-,<>()/*\"]";    //Regex jolla rivejä puhdistetaan turhista merkeistä.

        //Puhdistetaan rivin sen epäpuhtauksista esim tarpeettomat merkit,välit ja isot kirjaimet
        rivi = rivi.toLowerCase();
        rivi = rivi.trim();
        rivi = rivi.replaceAll(puhdistusRegex, "");

        return rivi;        
    }
    public ArrayList<String> erotteleLauseet(String rivi){

        ArrayList<String> lauseet;              //Rivin lauseet
        
        lauseet = new ArrayList<String>(Arrays.asList(rivi.split("[.]")));      //Rivin lauseet eroteltuna pisteellä.

        //Rivin lauseet muodossa["lause1 on tässä","Lause kaksi on kiva"]
        return lauseet;

    } 
    public ArrayList<ArrayList<String>> erotteleSanat(ArrayList<String> lauseet){

        ArrayList<ArrayList<String>> sanat = new ArrayList<ArrayList<String>>();     //Lista jossa on lauseen sanat eriteltynä eri listaan.

        //Erotellaan lauseiden sanat omiin listoihinsa.
        for (int i = 0; i < lauseet.size(); i++) {

            String nykyinenLause = lauseet.get(i); //Nykyinen lause jota käsitellään.

            //1) Otetaan nykyinen lause ja pilkotaan se välilyinneillä --> saadaan lista sanoista.
            //2) Lisätään sanalista sanalistojen listaan.
            ArrayList<String> nykyisetSanat = new ArrayList<String>(Arrays.asList(nykyinenLause.split("[ ]")));
            
            //Poistetaan tyhjät elementit ennenkuin ne asetetaan listaan.
            nykyisetSanat.removeAll(Arrays.asList(null,""," "));
            
            //Lisätään nykyisestä lauseesta saatu sanojen lista listojen listaan.
            sanat.add(puhdistaSanat(nykyisetSanat));


            //System.out.println("Lauseesta : " + nykyinenLause + " keräättiin sanat : " + sanat.get(i).toString());
        }

        //Palautetaan sanat muodossa [[sana1,sana2],[kana1,kana2],[kala1,kala2]]
        return sanat;
    }

    public ArrayList<String> puhdistaSanat(ArrayList<String> sanat){
        for (int i = 0; i < sanat.size(); i++) {

            //System.out.println("Puhdistetaan " + sanat.get(i) + " --> " + sanat.get(i).trim());
            sanat.set(i, sanat.get(i).trim());
            
        }
        //System.out.println(sanat.toString());
        return sanat;
    }

}
