package au.com.sydexplore;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import au.com.sydexplore.CategoryAdapter.ViewHolder;
import au.com.sydexplore.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	// List view for categories 
	GridView categoriesList; 

	// Array list containing the categories 
	static ArrayList<Category> categoryArray; 

	// ArrayAdapter 
	ArrayAdapter<String> categoriesAdapter;

	// Variables for grabbing data from cloud 
	static InputStream is = null;
	static String jsonin = "";

	// Selected view 
	View selectedView;

	// Old colour 
	static int oldColor; 

	//colour for the 'all' attraction category page
	static int allCategory = 2130837637;
	static int secAllCategory = 2130837638;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Set content view 
		setContentView(R.layout.activity_main);

		// reference the "listview" variable to the id-"listview" in the layout
		categoriesList = (GridView) findViewById(R.id.categoriesList);

		// Construct the data source
		categoryArray = new ArrayList<Category>();

		// Add in default categories 
		categoryArray.add(new Category("Adventurous","trekking",R.drawable.sunsetOrange,R.drawable.sunsetterOrange));
		categoryArray.add(new Category("Cultural","greek1",R.drawable.jade,R.drawable.emerald)); 
		categoryArray.add(new Category("Education","books30",R.drawable.jacksonspurple,R.drawable.jellybean));
		categoryArray.add(new Category("Fun","star83",R.drawable.not_so_electric_blue,R.drawable.electric_blue));
		categoryArray.add(new Category("Historical","time12",R.drawable.crusta,R.drawable.jaffa));      	
		categoryArray.add(new Category("Hungry","plate7",R.drawable.california,R.drawable.buttercup)); 
		categoryArray.add(new Category("Lazy","man271",R.drawable.curiousblue,R.drawable.pictonblue));
		categoryArray.add(new Category("Luxurious","banknotes",R.drawable.rebeccapurple,R.drawable.seance)); 
		categoryArray.add(new Category("Natural","tree101",R.drawable.mountainmeadow,R.drawable.carribeangreen));
		categoryArray.add(new Category("Social","party1",R.drawable.ripelemon,R.drawable.saffron));

		// Initialize array adapter for categories 
		categoriesAdapter = new CategoryAdapter(this,categoryArray);

		// Attach the adapter to a ListView
		categoriesList.setAdapter(categoriesAdapter);

		// Set up listener for on click 
		setupListViewListener(); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		// Inflate the menu 
		MenuInflater menuinf = getMenuInflater();
		menuinf.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menu){
		super.onOptionsItemSelected(menu);

		// Cases depending on which option was selected in menu 
		String category="";
		switch(menu.getItemId()){
		case R.id.All:
			displayAttractionPage("All");
			return true;
		case R.id.Adventurous:
			displayAttractionPage("Adventurous");
			return true;
		case R.id.Cultural:
			displayAttractionPage("Cultural");
			return true;
		case R.id.Education:
			displayAttractionPage("Education");
			return true;
		case R.id.Fun:
			displayAttractionPage("Fun");
			return true;
		case R.id.Historical:
			displayAttractionPage("Historical");
			return true;
		case R.id.Hungry:
			displayAttractionPage("Hungry");
			return true;
		case R.id.Lazy:
			displayAttractionPage("Lazy");
			return true;
		case R.id.Luxurious:
			displayAttractionPage("Luxurious");
			return true;
		case R.id.Natural:
			displayAttractionPage("Natural");
			return true;
		case R.id.Social:
			displayAttractionPage("Social");
			return true;
		}
		return false;
	}

	@Override
	public void onRestart() { 
		super.onRestart();
		//When back is pressed, the activity on stack restarts 

		// Set content view 
		setContentView(R.layout.activity_main);

		// reference the "listview" variable to the id-"listview" in the layout
		categoriesList = (GridView) findViewById(R.id.categoriesList);

		// Construct the data source
		categoryArray = new ArrayList<Category>();

		// Add in default categories 
		categoryArray.add(new Category("Adventurous","trekking",R.drawable.sunsetOrange,R.drawable.sunsetterOrange));
		categoryArray.add(new Category("Cultural","greek1",R.drawable.jade,R.drawable.emerald)); 
		categoryArray.add(new Category("Education","books30",R.drawable.jacksonspurple,R.drawable.jellybean));
		categoryArray.add(new Category("Fun","star83",R.drawable.not_so_electric_blue,R.drawable.electric_blue));
		categoryArray.add(new Category("Historical","time12",R.drawable.crusta,R.drawable.jaffa));      	
		categoryArray.add(new Category("Hungry","plate7",R.drawable.california,R.drawable.buttercup)); 
		categoryArray.add(new Category("Lazy","man271",R.drawable.curiousblue,R.drawable.pictonblue));
		categoryArray.add(new Category("Luxurious","banknotes",R.drawable.rebeccapurple,R.drawable.seance)); 
		categoryArray.add(new Category("Natural","tree101",R.drawable.mountainmeadow,R.drawable.carribeangreen));
		categoryArray.add(new Category("Social","party1",R.drawable.ripelemon,R.drawable.saffron));

		// Initialize array adapter for categories 
		categoriesAdapter = new CategoryAdapter(this,categoryArray);

		// Attach the adapter to a ListView
		categoriesList.setAdapter(categoriesAdapter);

		// Set up listener 
		setupListViewListener(); 
	}

	@Override
	protected void onResume()
	{   
		super.onResume();
		if (selectedView!=null){
			ImageView imageView = ((ViewHolder) selectedView.getTag()).icon;
			TextView textView = ((ViewHolder) selectedView.getTag()).categoryName;
			imageView.setBackgroundResource(oldColor);
			textView.setBackgroundResource(oldColor);
		}
	}


	/**
	 * Displays the category page when the menu item is clicked
	 * @param categoryClickedOn
	 */
	public void displayAttractionPage(String categoryClickedOn){
		try {
			sendJson(categoryClickedOn);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Intent intent = new Intent(MainActivity.this, ViewCategory.class); 
		if (intent != null) { 
			// put "extras" into the bundle for access in the edit activity
			intent.putExtra("jsonString",jsonin); 

			int i;
			int secondaryColor = 0;

			// Check if the category clicked on is ALL 
			if (categoryClickedOn.equals("All")){
				oldColor = allCategory;
				secondaryColor = secAllCategory;
			}
			else { // Otherwise, proceed like normal and create category array 
				for (i=0; i<categoryArray.size(); i++){
					Category cat = categoryArray.get(i);
					if (cat.getName().equals(categoryClickedOn)){
						oldColor = cat.getColor();
						secondaryColor = cat.secondaryColor;
					}
				}
			}
			
			// Send away the colors 
			intent.putExtra("primaryColor", oldColor);
			intent.putExtra("secondaryColor", secondaryColor);
			
			// brings up the second activity
			startActivity(intent); 
		} 

	}

	private void setupListViewListener() { 
		// Check for on category click 
		categoriesList.setOnItemClickListener(new OnItemClickListener() { 
			@Override
			public void onItemClick(AdapterView <? > parent, View view, int position, long id) { 
				selectedView = view;
				oldColor = categoryArray.get(position).getColor();
				int secondaryColor = categoryArray.get(position).secondaryColor;
				// Hover effect 
				ImageView imageView = ((ViewHolder) view.getTag()).icon;
				TextView textView = ((ViewHolder) view.getTag()).categoryName;
				imageView.setBackgroundResource(R.drawable.electric_blue);
				textView.setBackgroundResource(R.drawable.electric_blue);

				// Send data to heroku app
				String categoryClickedOn = categoryArray.get(position).getName();
				try {
					sendJson(categoryClickedOn);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Intent intent = new Intent(MainActivity.this, ViewCategory.class); 
				if (intent != null) { 
					// put "extras" into the bundle for access in the edit activity
					intent.putExtra("jsonString",jsonin); 
					intent.putExtra("primaryColor", oldColor);
					intent.putExtra("secondaryColor", secondaryColor);
					// brings up the second activity
					startActivity(intent); 
				} 
			} 
		}); 
	}

	/** 
	 * Send JSON to backend 
	 * @param category
	 * @throws InterruptedException 
	 */
	static void sendJson(final String category) throws InterruptedException {
		// Start new thread 
		Thread t = new Thread() {
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit

				//HttpResponse response;
				JSONObject json = new JSONObject();

				try {
					// Try link up to the heroku python site view function 
					HttpPost post = new HttpPost("http://lit-cove-9769.herokuapp.com/attractions/getAttractions/");
					// construct JSON output 
					json.put("category_name", category);
					StringEntity se = new StringEntity(json.toString());  
					se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
					post.setEntity(se);
					HttpResponse httpResponse = client.execute(post);
					HttpEntity httpEntity = httpResponse.getEntity();
					is = httpEntity.getContent();    
				} catch(Exception e) {
					e.printStackTrace();
				}

				// Try construct output from the heroku app 
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;
					// loop through the response and create string 
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					is.close();
					jsonin = sb.toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
		t.join();
	}
}
