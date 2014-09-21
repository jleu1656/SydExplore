package au.com.sydexplore; 

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Attraction {
	// name of attraction 
    public String name;
    
    // location of attraction 
    public String location; 
    
    // url of image thumbnail
    public String thumbnailUrl;
    
    /** 
     * Constructor for normal attraction 
     * @param name
     * @param location
     */
    public Attraction(String name, String location, String url) {
       this.name = name;
       this.location = location;
       this.thumbnailUrl = url;
    }
    
    /**
     * Constructor for creating attraction from JSON object 
     * @param object
     */
    public Attraction(JSONObject object){
        try {
            this.name = object.getString("name");
            this.location = object.getString("location");
            this.thumbnailUrl = object.getString("thumbnail");
       } catch (JSONException e) {
            e.printStackTrace();
       }
    }
    
    /**
     * Constructor for creating attraction from JSONArray of json objects 
     * @param jsonObjects
     * @return
     */
    public static ArrayList<Attraction> fromJson(JSONArray jsonObjects) {
           ArrayList<Attraction> attractions = new ArrayList<Attraction>();
           for (int i = 0; i < jsonObjects.length(); i++) {
               try {
                  attractions.add(new Attraction(jsonObjects.getJSONObject(i)));
               } catch (JSONException e) {
                  e.printStackTrace();
               }
          }
          return attractions;
    }
    
    /**
     * Return name of attraction 
     * @return
     */
    public String getName(){
    	return name; 
    }
    
    /**
     * Return location of attraction 
     * @return
     */
    public String getLocation(){
    	return location;
    }
    
    /**
     * Return url of thumbnail
     * @return
     */
    public String getThumbnailUrl(){
    	return thumbnailUrl;
    }
}