package team1.rims.rims;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
public class Report extends AppCompatActivity {
    private Button get_location;
    private TextView txtLocation;
    int PLACE_PICKER_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        get_location = findViewById(R.id.btnMap);
        txtLocation = findViewById(R.id.txtLocation);
        get_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    Intent intent;

                    intent = builder.build(Report.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                String address = String.format("Location: %s %s", place.getAddress(), place.getLatLng());

                txtLocation.setText(address);
            }

        }

    }
}



