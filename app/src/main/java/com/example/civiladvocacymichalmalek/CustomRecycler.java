package com.example.civiladvocacymichalmalek;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.ArrayList;

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{

    private ArrayList<GovData> govDataArrayList = new ArrayList<>();
    private OfficialListener officialListener;
    public CardView cv;


    public CustomAdapter(ArrayList<GovData> data, OfficialListener officialListener){
        this.govDataArrayList = data;
        this.officialListener = officialListener;
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item,parent,false);
        return new ViewHolder(item, officialListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.pos.setText(govDataArrayList.get(position).getOffice());
        holder.n.setText(govDataArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() { return govDataArrayList.size();}

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView pos, n;
        OfficialListener officialListener;

        public ViewHolder(@NonNull View item, OfficialListener officialListener) {
            super(item);
            // initializing each view of our recycler view.
            pos = item.findViewById(R.id.of_position);
            n = item.findViewById(R.id.name);
            cv = item.findViewById(R.id.cardView);
            cv.setOnClickListener(v -> {
                officialListener.onOfficialClick(getAdapterPosition());
            });
            //this.officialListener = officialListener;

            //item.setOnClickListener(this);
        }
        @Override
        public void onClick(View view){
            officialListener.onOfficialClick(getAdapterPosition());
        }
    }

    public interface OfficialListener{
        void onOfficialClick(int position);
    }
/*
class CustomAdapter extends ListAdapter<GovData, CustomAdapter.ViewHolder> {



    OnItemClickListener listener;

    CustomAdapter(ArrayList<GovData> govDataArrayList) {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<GovData> DIFF_CALLBACK = new DiffUtil.ItemCallback<GovData>() {
        @Override
        public boolean areItemsTheSame(GovData oldItem, GovData newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(GovData oldItem, GovData newItem) {
            // below line is to check the course name, description and course duration.
            return oldItem.getName().equals(newItem.getName())&&
                    oldItem.getOffice().equals(newItem.getOffice());
        }
    };
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        // below line is use to inflate our layout
        // file for each item of our recycler view.
        View item= LayoutInflater.from(parent.getContext())
        .inflate(R.layout.rv_item,parent,false);
        return new ViewHolder(item);
        }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position){
        // below line of code is use to set data to
        // each item of our recycler view.
        GovData model=getCourseAt(position);
        holder.pos.setText(model.getOffice());
        holder.n.setText(model.getName());
        }

    // creating a method to get course modal for a specific position.
    public GovData getCourseAt(int position) {
        return getItem(position);
    }


    public void setClickItemListener(MainActivity.OnItemClickListener onItemClickListener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // view holder class to create a variable for each view.
        TextView pos, n;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing each view of our recycler view.
            pos = itemView.findViewById(R.id.of_position);
            n = itemView.findViewById(R.id.name);
        }
        @Override
        public void onClick(View view){

        }
    }

    public static abstract class OnItemClickListener {
        abstract void onItemClick(GovData data);
    }*/
}


