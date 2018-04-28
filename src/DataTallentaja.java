import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.util.Pair;

class DataTallentaja{

    HashMap<String,HashMap<String,Integer>> sanatHashMap;//Sanat tallennetaan tänne. Ne etsitään "sana" ja se palauttaa uuden hasmapin, jossa on sitä seuraavia sanoja ja niiden yleisyydet.
    String tiedostoNimi;

    //Tehtävänä on tallentaa annetut sanat tietokantaan luettavassa muodossa.
    
    public DataTallentaja(String _tiedostoNimi){
        this.sanatHashMap = new HashMap<String,HashMap<String,Integer>>();
        this.tiedostoNimi = _tiedostoNimi;s

    }

    /**
     * Tallentaa annetusta sanojen listan listasta perakkaiset sanat tietokantaa.
     */
    public void tallenna(ArrayList<ArrayList<String>> sanat){
        for (int i = 0; i < sanat.size(); i++) {

            ArrayList<Pair> sanaParit = new ArrayList<Pair>();
            sanaParit = perakkaisetSanatLauseessa(sanat.get(i));
            if(sanaParit.size() > 0){
                luoSanatHashMap(sanaParit);
            }
                
            
        }
    }
    
    /**Palauttaa arraylistan jossa on sana parit [ekas,seuravaa sana] */
    private ArrayList<Pair> perakkaisetSanatLauseessa(ArrayList<String> lause){
        ArrayList<Pair> sanaParit = new ArrayList<Pair>();  
        
        for (int i = 0; i < lause.size()-1; i++) {

            //Tarkistetaan että ei olla menty listan yli ja lisätä tyhjiä objecteja
            if((lause.get(i) != null) && (lause.get(i+1) != null)){
                String ensimmainenSana = lause.get(i);
                String seuraavaSana = lause.get(i+1);
    
                //Luodaan uusi sanapari ja lisätään se listaan.
                Pair sanaPari = new Pair(ensimmainenSana,seuraavaSana);
                sanaParit.add(sanaPari);
            }
            
        }

        //System.out.println("Saatiin sanaparit :" + sanaParit.toString());
        return sanaParit;

    }
    
    /**
     * Tallentaa sanaparit tietokantaa.
     */
    private void luoSanatHashMap(ArrayList<Pair> sanaParit){
        
        //sanatHashMap = new HashMap<String,HashMap<String,Integer>>();          //Sanat tallennetaan tänne. Ne etsitään "sana" ja se palauttaa uuden hasmapin, jossa on sitä seuraavia sanoja ja niiden yleisyydet.
        HashMap<String,Integer> sanaYleisyyHashMap = new HashMap<String,Integer>();                                    //Sana ja sen yleisyys. Lisätään sanaHashMappiin.

        for (int i = 0; i < sanaParit.size(); i++) {

            //System.out.println("Käsitellään sanaa : " + sanaParit.get(i).getKey() + " seuraavaSana : " + sanaParit.get(i).getValue());

            //Luodaan uusi hashmap ["sana",[seuraavaSana,1]], koska sitä ei vielä olemassa.
            if(sanatHashMap.get(sanaParit.get(i).getKey()) == null){

                //1) Otetaan parin[Sana1,seuraavaSana] ensimmäinen arvo ja muutetaan se luokaksi String.
                //2)Tehdään uusi hashmap jolle annetaan arvoksi [seuraavaSana,1] ,
                //koska tarkastuksessa on huomattu että tätä ei ollut aikausemmin voidaan olettaa ja laittaa aloitus arvoksi 1
               
                //new HashMap<String,Integer>.put(sanaParit.get(i).getValue().toString(),1));

                sanaYleisyyHashMap.put(sanaParit.get(i).getValue().toString(),1);
                sanatHashMap.put(sanaParit.get(i).getKey().toString(),sanaYleisyyHashMap);

                //System.out.println("Lisätään hashMappiin uusi sana : " + sanaParit.get(i).getKey() + " sana sai myös uuden seuraavanSanan : " + sanaYleisyyHashMap.toString());
                

                     //Tarkistetaan onko sanarin esimmäisellä sanalla jo olemassa hasmap sitä seuraavalle sanalle.
                }else if(sanatHashMap.get(sanaParit.get(i).getKey()).get(sanaParit.get(i).getValue()) == null){
                        //Koska jo aikaisemmin tarkastettiin että nykyiselle sanalle oli jo olemassa oma hasmap lisätään sille uusi
                        //System.out.println("Sanalle : " + sanaParit.get(i).getKey() + " lisattiin uusi seuraavaSana: { " + sanaParit.get(i).getValue() + ", 1 }");
                        //seuraavan sanan hashmap, jonka arvoksi annetaan 1,koska tarkastettin jo että sitä ei ole olemassa.
                        sanatHashMap.get(sanaParit.get(i).getKey()).put(sanaParit.get(i).getValue().toString(), 1);
            }else{
                //Koska on tarkastettu, että nykyisellä sanalla on jo olemassa oma hashmap ja sitä seuraavalla sanalla on myös oma hashmap nykyisen sanan hasmapissa
                //,voidaan silloin vain kasvattaa esiintyvyyden arvoa yhdellä.

                
                int nykeinenYleisyys = sanatHashMap.get(sanaParit.get(i).getKey()).get(sanaParit.get(i).getValue());
               // System.out.println("Sanan : " + sanaParit.get(i).getKey() + " seuraavaSana ilmestyi uudestaan ja kasvatetiin sen yleisyystta : { " + sanaParit.get(i).getValue() +", "+ nykeinenYleisyys+1 +" }");
                sanatHashMap.get(sanaParit.get(i).getKey()).put(new String(sanaParit.get(i).getValue().toString()), nykeinenYleisyys+1);

            } 

            System.out.println("sanatHashMap koko on : " + sanatHashMap.size());

            sanaYleisyyHashMap.clear();
        }
        
    }

    public HashMap<String,HashMap<String,Integer>> annaSanatHashMap(){
        return this.sanatHashMap;
    }

    public void tallennaHashMap(){
        try{
        FileOutputStream fileOutputStream = new FileOutputStream(tiedostoNimi);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(sanatHashMap);
        fileOutputStream.close();
        objectOutputStream.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }

}
