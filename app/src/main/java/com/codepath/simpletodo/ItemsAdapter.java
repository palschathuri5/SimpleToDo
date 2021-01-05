//Adapter will connect the code and the user interface the user will view
package com.codepath.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//This class will display the data from the data model (in the list in MainActivity) and put it in the recycler view
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    public interface OnLongClickListener{
        void OnItemLongClicked(int position);
    }
    List<String> items;
    OnLongClickListener LongClickListener;

    //data about the model collected through string
    public ItemsAdapter(List<String> items, OnLongClickListener LongClickListener) {
        this.items = items;
        this.LongClickListener = LongClickListener;
    }

    @NonNull
    @Override

    //creates views
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflater converts XML files into View objects
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent, false);

        //wraps into view holder
        return new ViewHolder(todoView);
    }

    //takes data from a position and puts it into the view holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get the item at that position
        String item = items.get(position);
        //bind will write item inside the holder
        holder.bind(item);

    }

    @Override
    //the function below number of items in the data
    public int getItemCount() {
        return items.size();
    }

    //Create an interface (container) to access the views of each row in the list
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem= itemView.findViewById(android.R.id.text1);
        }
        //update the view in the view holder with data
        public void bind(String item) {
            tvItem.setText(item);
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //Shows which position item was long clicked
                    LongClickListener.OnItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
