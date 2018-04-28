import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.util.Pair;

class DataLukija{

    //tehtävänä on lukea hashMap joissa on tarvittava data puheen luontiin.
    String tiedostoNimi = "hashMap.ser";
    
    public DataLukija(){

    }

    public HashMap<String,HashMap<String,Integer>> lueHashMapData(){
        HashMap<String,HashMap<String,Integer>> sanatHashMap = new HashMap<String,HashMap<String,Integer>>();

        try {

            System.out.println("Ladataan kohdetta : " + tiedostoNimi);
            FileInputStream fileInputStream = new FileInputStream(tiedostoNimi);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            sanatHashMap = (HashMap<String,HashMap<String,Integer>>) objectInputStream.readObject();

            fileInputStream.close();
            objectInputStream.close();

            System.out.println("sanatHashMap koko on : " + sanatHashMap.size());

           //System.out.println(sanatHashMap.get("koulutus:"));
           // System.out.println(sanatHashMap.get("olen").keySet());
           

         } catch (IOException e) {
            e.printStackTrace();
         }catch(ClassNotFoundException e){
            e.printStackTrace();
         }


         return sanatHashMap;


    }
}