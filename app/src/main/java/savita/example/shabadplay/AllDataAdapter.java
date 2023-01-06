package savita.example.shabadplay;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class AllDataAdapter extends RecyclerView.Adapter<AllDataAdapter.MyViewHolder>{

    private Context context;
    public ArrayList<HashMap<String,String>> totalList;
    public ArrayList totalKey;
    public SomeFunction.dataReturn details = new SomeFunction.dataReturn(totalList,totalKey);
    public AllDataAdapter(Context context, SomeFunction.dataReturn details) {
        this.context = context;
        this.details = details;
    }
    @NonNull
    @Override
    public AllDataAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_data_adapter, parent, false);
        AllDataAdapter.MyViewHolder vh = new AllDataAdapter.MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AllDataAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
   //     Log.d("adapter", "getRealList: "+details.totalList);
       // holder.relativeLayout.setBackgroundColor(Color.GREEN);
        String datatext  = "data"+String.valueOf(position+1);
        int offset = details.totalKey.size()/2;
        String imageurl = "image"+String.valueOf(1+position);
        Log.d("adapter", "test: "+datatext+" ,"+imageurl);
        String data = details.totalList.get(position).get(datatext);
         holder.textView.setText(data);
        String link = details.totalList.get(position+offset).get(imageurl);
        Log.d("adapterdata", "onBindViewHolder: "+data+" :"+link);
        Picasso.get().load(link).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(context, result.class);
                myIntent.putExtra("data",data);
                myIntent.putExtra("number",position+1);
                context.startActivity(myIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (details.totalKey.size()/2);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        RelativeLayout relativeLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativelayout);
             textView = (TextView) itemView.findViewById(R.id.textView);
             imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

}
