package com.example.bs_36.cwc1.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bs_36.cwc1.R;

public class CatagoryArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
	private final String[] values;
 
	public CatagoryArrayAdapter(Context context, String[] values) {
		super(context, R.layout.activity_list_catagory, values);
		this.context = context;
		this.values = values;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.activity_list_catagory, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
		textView.setText(values[position]);
 
		// Change icon based on name
		String s = values[position];
		if (s.equals("Vehicles")) {
			imageView.setImageResource(R.drawable.ic_car);
		}else if (s.equals("Gadgets")) {
			imageView.setImageResource(R.drawable.ic_gadgets);
		}else if (s.equals("Real Estate")) {
            imageView.setImageResource(R.drawable.ic_real_estate);
        }
        else if(s.equals("Accessories")){
			imageView.setImageResource(R.drawable.ic_accessories);
		}

        else if(s.equals("Car")){
            imageView.setImageResource(R.drawable.ic_car);
        }

        else if(s.equals("Motorcycle")){
            imageView.setImageResource(R.drawable.ic_motor);
        }

        else if(s.equals("PC")){
            imageView.setImageResource(R.drawable.ic_pc);
        }

        else if(s.equals("Phone")){
            imageView.setImageResource(R.drawable.ic_gadgets);
        }
        else if(s.equals("Flats For Rent")){
            imageView.setImageResource(R.drawable.ic_real_estate);
        }
        else if(s.equals("Flats For Sale")){
            imageView.setImageResource(R.drawable.ic_real_estate);
        }
        else if(s.equals("Shoe")){
            imageView.setImageResource(R.drawable.ic_shoe);
        }
        else if(s.equals("Shade")){
            imageView.setImageResource(R.drawable.ic_shade);
        }
        else if(s.equals("Bag")){
            imageView.setImageResource(R.drawable.ic_accessories);
        }
		return rowView;
	}
}