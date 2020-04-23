package vn.hust.edu.gmailrecycle;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.bloco.faker.Faker;
import vn.hust.edu.gmailrecycle.Model.GmailItem;

public class MainActivity extends AppCompatActivity {
    GmailAdapter gmailAdapter;
    List<GmailItem> items;
    List<GmailItem> fitems;

    RecyclerView recyclerView;
    EditText edtFind;
    Button btnFavorite;

    boolean showFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = GetListGmail();

        fitems = new ArrayList<>();
        fitems.addAll(items);

        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        gmailAdapter = new GmailAdapter(this, fitems);
        recyclerView.setAdapter(gmailAdapter);

        edtFind = findViewById(R.id.edt_keyword);
        btnFavorite = findViewById(R.id.btn_favorite);

        edtFind.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                gmailAdapter.getFilter().filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFavorite = !showFavorite;
                gmailAdapter.getFilter().filter("");
            }
        });
    }

    private ArrayList<GmailItem> GetListGmail() {
        ArrayList<GmailItem> listGmail = new ArrayList<>();
        Faker faker = new Faker();
        String fgmail = " ";
        String fsubject= " ";
        String fcontent= " ";

        for(int i = 0; i < 15; i++){
            fgmail = faker.name.name();
            fcontent = faker.lorem.paragraph();
            fsubject = faker.lorem.paragraph();
            listGmail.add(new GmailItem(fgmail, fsubject, fcontent, "12:43 AM"));
        }


        listGmail.add(new GmailItem("quannar178@gmail.com", "SubjectSubjectSubjectSubjectSubjectSubject", "ContentContentContentContentContentContentContent", "12:43 AM"));
        return listGmail;
    }

    private class GmailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
        Context context;
        List<GmailItem> itemsl;
        NewFilter mfilter;

        public GmailAdapter(List<GmailItem> itemsl) {
            this.itemsl = itemsl;
        }

        public GmailAdapter(Context context, List<GmailItem> itemsl) {
            this.context = context;
            this.itemsl = itemsl;
            mfilter = new NewFilter(GmailAdapter.this);
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
            GmailItem item = itemsl.get(position);

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
            return itemsl.size();
        }

        @Override
        public Filter getFilter() {
            return mfilter;
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
            public MainActivity.GmailAdapter mAdapter;

            public NewFilter(MainActivity.GmailAdapter mAdapter) {
                super();
                this.mAdapter = mAdapter;
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (showFavorite == false){
                    fitems.clear();
                    final FilterResults results = new FilterResults();
                    if(constraint.length() == 0 || constraint == null){
                        fitems.addAll(items);
                    }else{
                        final String filterPattern = constraint.toString().toLowerCase().trim();
                        for(GmailItem e : items){
                            if(e.getGmail().toLowerCase().contains(filterPattern)){
                                fitems.add(e);
                                continue;
                            }
                            if(e.getContent().toLowerCase().contains(filterPattern)){
                                fitems.add(e);
                                continue;
                            }
                            if(e.getSubject().toLowerCase().contains(filterPattern)){
                                fitems.add(e);
                                continue;
                            }
                        }
                    }
                    results.values = fitems;
                    results.count = fitems.size();
                    return results;
                }
                else{
                    fitems.clear();
                    final FilterResults results = new FilterResults();
                    {
                        for(GmailItem e : items){
                            if(e.isFavorite()){
                                fitems.add(e);
                            }
                        }
                    }
                    results.values = fitems;
                    results.count = fitems.size();
                    return results;
                }
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                this.mAdapter.notifyDataSetChanged();
            }
        }
    }
}
