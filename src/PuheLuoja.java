import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class PuheLuoja{

    //Tehvänä on luoda pöhinää
    public HashMap<String,HashMap<String,Integer>> sanatHashMap; //Sanat hashMap
    Random satunnainen;

    public PuheLuoja(){
    }
    
    public PuheLuoja(HashMap<String,HashMap<String,Integer>> sanat){
        sanatHashMap = new HashMap<String,HashMap<String,Integer>>();
        sanatHashMap = sanat;
    }

    //TODO tarkenna input vaatimus.
    public annaSanatHashMap(HashMap<String,HashMap<String,Integer>> sanat){
        sanatHashMap = new HashMap<String,HashMap<String,Integer>>();
        sanatHashMap = (HashMap<String,HashMap<String,Integer>>)sanat;
    }

    public String luoPuhetta(Integer lauseenPituus){
        ArrayList<String> lause = new ArrayList<String>(); //Luotu lause listattuna
       
        satunnainen = new Random();

        lause.add(0,valitseEnsimmainenSana());

        for (int i = 1; i < lauseenPituus; i++) {

            //valitaan seuraavaSana edellisen sanan perusteella
            String seuraavaSana = valitseSeuraavaSana(lause.get(i-1));
            if(seuraavaSana != null){
                lause.add(i,seuraavaSana);
            }else{
                break;
            }
            
        }

        
        return kokoaLause(lause); //TODO lisää piste ja ensimmäinen kirjain suureksi.


    }

    private String valitseSeuraavaSana(String edellinenSana){
        //EDELLINEN SANA ON SANA JOLLE VALITAAN SEURAAVA SANA.

        Integer seuraavienSanojenYleisyysMaara = 0;

        if(edellinenSana != null){
            seuraavienSanojenYleisyysMaara = seuraavienSanojenYleisyysMaara(edellinenSana);
            //System.out.println("Sanalla : " + edellinenSana + " on" + seuraavienSanojenYleisyysMaara(edellinenSana)+"seuraavaa sanaa");
        }

        ArrayList<String> avaimet = new ArrayList<String>();
        if(sanatHashMap.containsKey(edellinenSana) == false){
            //sanalla ei ole avainta, joten peruutetaan, jotta ei tule arvoa null.
            return null;
        }
        if(sanatHashMap.get(edellinenSana).isEmpty()){
            return null;
        }
        avaimet.addAll( sanatHashMap.get(edellinenSana).keySet());
        //System.out.println("Sanan " + edellinenSana + "avaimetSet on :" + sanatHashMap.get(edellinenSana).keySet());
        //System.out.println("Sanan " + edellinenSana + "1 avain on :" + sanatHashMap.get(edellinenSana).keySet().toArray()[0]);
        //System.out.println("Sanan " + edellinenSana + "ensimmäisen avaimen arvo on :" + sanatHashMap.get(edellinenSana).values().toArray()[0]);

        //Loopataan mahdollisten seuraavien sanojen läpi ja tehdään operaatio tämänsananYleisyys/kaikkiSeuraavatSanatYht 
        //katsotaan onko saatu desimaali suurempi kuin arvottu luku, jos on valitataan se seuraavaksi sanaksi
        //Jos ei ole mennään seuraavaan sanaan sen desimaali arvo on  --> edellisetArvot +  tämänsananYleisyys/kaikkiSeuraavatSanatYht
        Float yleisyysDesimaali = 0f;
        Float satunnainenDesimaali = satunnainen.nextFloat();
        Float sananYleisyysDesimaali = 0f;
        for (int i = 0; i < avaimet.size(); i++) {

            
            sananYleisyysDesimaali = (float)(Integer)sanatHashMap.get(edellinenSana).values().toArray()[i]/seuraavienSanojenYleisyysMaara; //Montako prosenttia desimaalina. On käyty kaikista sana vaihtoehdoista läpi.
            yleisyysDesimaali += sananYleisyysDesimaali;

            //System.out.println("Sanan yleisyysDesimaali saadaan arvoista : " + sanatHashMap.get(edellinenSana).values().toArray()[i] + " / " +seuraavienSanojenYleisyysMaara+ " . Laskun tulos on : " + (float)(Integer)sanatHashMap.get(edellinenSana).values().toArray()[i]/seuraavienSanojenYleisyysMaara);


            if(yleisyysDesimaali > satunnainenDesimaali){
                //Onneksi olkoon sana on valittu se on avaimet.get(i)

                //System.out.println("Valittiin sana  : " + sanatHashMap.get(edellinenSana).keySet().toArray()[i].toString() + "Arvolla : " + yleisyysDesimaali + " kun vaatimus oli : " + satunnainenDesimaali);
                return sanatHashMap.get(edellinenSana).keySet().toArray()[i].toString();
            }
        }

        System.out.println("SANAA EI VALITTU!");

        return "LOPPU";


        //return avaimet.get(satunnainen.nextInt(sanatHashMap.get(edellinenSana).keySet().size())).toString();

    }

    private String valitseEnsimmainenSana(){
        String ensimmainenSana = "olen"; //TODO valitse ensimäinen sana satunnaisesti
        return ensimmainenSana;
    }

    public Integer seuraavienSanojenYleisyysMaara(String sana){
        //Laskee sanaa seuraavien sanojen määrän. 
        Integer maara = 0;

        //System.out.println("ETSITÄÄN SANALLA : " + sana);

        //System.out.println("Sanan :" + sana + " sanatHashMap.get("+sana+") on : " + sanatHashMap.get(sana));

        if(sanatHashMap.containsKey(sana) == false){
            //Sanalla ei ollut avainta joten sitä oli käytetty vain viimeisenäSanana? Joten annetaan nolla
            return 0;
        }
        if(sanatHashMap.get(sana).size() <= 0){

            //Sanalla ei ole seuraavia sanoja joten palautetaan nolla
            return 0;
        }else{
            for (Integer yleisyys : sanatHashMap.get(sana).values()) {
                //System.out.println("Sanalla : " + sana + " on" + yleisyys + " += yleisyys");
                maara += yleisyys;
            }
        }
        
        //System.out.println("Sanalla : " + sana + " on" + maara+ " yleisyys");
        return maara;
    }

    public String kokoaLause(ArrayList<String> lause){

        String lauseString = "";

        for (String sana : lause) {
            lauseString = lauseString + " "+ sana;
        }

        lauseString = lauseString.trim();
        //Lisätään piste ja iso alkukirjain
        lauseString += ".";

        String isoKirjainLause = lauseString.substring(0,1).toUpperCase() + lauseString.substring(1);

        return isoKirjainLause;

    }


}
