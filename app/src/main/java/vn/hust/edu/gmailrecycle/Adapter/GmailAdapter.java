package vn.hust.edu.gmailrecycle.Adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.hust.edu.gmailrecycle.Model.GmailItem;
import vn.hust.edu.gmailrecycle.R;

import java.util.List;

public class GmailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    Context context;
    List<GmailItem> items;

    public GmailAdapter(List<GmailItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        return new GmailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GmailViewHolder viewHolder = (GmailViewHolder)  holder;
        GmailItem item = items.get(position);

        viewHolder.agg.setText(item.getGmail().substring(0, 1).toUpperCase());
        viewHolder.gmail.setText(item.getGmail());
        viewHolder.subject.setText(item.getSubject());
        viewHolder.content.setText(item.getContent());
        viewHolder.time.setText(item.getTime());
        Drawable background = viewHolder.agg.getBackground();
        background.setColorFilter(new PorterDuffColorFilter(item.getColor(), PorterDuff.Mode.SRC_ATOP));

        if(item.isFavorite())
            viewHolder.favorite.setImageResource(R.drawable.ic_star_selected);
        else
            viewHolder.favorite.setImageResource(R.drawable.ic_star_normal);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    class GmailViewHolder extends RecyclerView.ViewHolder {
        TextView agg;
        TextView gmail;
        TextView subject;
        TextView content;
        TextView time;
        ImageView favorite;

        public GmailViewHolder(@NonNull View itemView) {
            super(itemView);

            agg = itemView.findViewById(R.id.tv_agg);
            gmail = itemView.findViewById(R.id.tv_gmail);
            subject = itemView.findViewById(R.id.tv_subject);
            content = itemView.findViewById(R.id.tv_content);
            time = itemView.findViewById(R.id.tv_time);
            favorite = itemView.findViewById(R.id.iv_favorite);

            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isFavorite = items.get(getAdapterPosition()).isFavorite();
                    items.get(getAdapterPosition()).setFavorite(!isFavorite);
                    notifyDataSetChanged();

                }
            });
        }
    }
    class NewFilter extends Filter{
        public GmailAdapter gmailAdapter;

        public NewFilter(GmailAdapter gmailAdapter) {
            super();
            this.gmailAdapter = gmailAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            return null;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        }
    }
}
