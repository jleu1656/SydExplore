package au.com.sydexplore;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

public class FragmentReview extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

		// Inflate fragment review layout 
		View v = inflater.inflate(R.layout.fragment_review, container, false);

		//get the review details		
		Bundle reviewDetails = getArguments();

		// If there are review details 
		if (reviewDetails!=null){
			// Construct the strings for the review 
			String reviewTitle = "''" + reviewDetails.getString("reviewTitle") + "''";
			String reviewText = reviewDetails.getString("reviewText");
			String reviewRating = reviewDetails.getString("reviewRating");
			String reviewerName = reviewDetails.getString("reviewerName");

			// Get the rating float 
			float reviewStar = Float.parseFloat(reviewRating);

			// Set the review components e.g. title and body of review 
			TextView title = (TextView)v.findViewById(R.id.reviewTitle);
			title.setText(reviewTitle);

			TextView text = (TextView)v.findViewById(R.id.reviewText);
			text.setText(reviewText);

			TextView reviewer = (TextView)v.findViewById(R.id.reviewerName);
			reviewer.setText(reviewerName);

			RatingBar starRating = (RatingBar)v.findViewById(R.id.ratingBar);
			starRating.setRating(reviewStar);
		}
		return v;
	}
}
