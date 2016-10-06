package example.slifers.firebase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.List;


public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {
    private List<Person> Persons;
    Context c;
    String firstLetter;
    private int[] mMaterialColors;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView first;
        TextView last;
        TextView dob;
        TextView zip;
        TextView phone;

        MaterialLetterIcon mIcon;

        private Person Person;

        public ViewHolder(View itemView) {
            super(itemView);
            first = (TextView) itemView.findViewById(R.id.tv_first);
            last = (TextView) itemView.findViewById(R.id.tv_last);
            dob = (TextView) itemView.findViewById(R.id.tv_dob);
            zip = (TextView) itemView.findViewById(R.id.tv_zip);
            phone = (TextView) itemView.findViewById(R.id.tv_phone);

            mIcon = (MaterialLetterIcon) itemView.findViewById(R.id.icon);
            itemView.setOnClickListener(this);

        }

        public void bind(Person Person) {
            this.Person = Person;
            first.setText(Person.getFirst());
            last.setText(Person.getLast());
            dob.setText(Person.getDob());
            zip.setText(Person.getZipcode());
            phone.setText(Person.getPhone());

        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            context.startActivity(PersonActivity.newInstance(context, Person));
        }
    }

    public PersonAdapter(Context c, List<Person> Persons) {
       // mMaterialColors = c.getResources().getIntArray(R.array.colors);
        this.Persons = Persons;
        this.c = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_person, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(Persons.get(position));
        String firstLetter;
        firstLetter = Persons.get(position).first.toString().substring(0, 2).toLowerCase();
        Log.d("ooriginal", " " + Persons.get(position).first.toString());

        Log.d("letter", " " + firstLetter);
       // holder.mIcon.setShapeColor(mMaterialColors[RANDOM.nextInt(mMaterialColors.length)]);
        holder.mIcon.setLetter(firstLetter);
        holder.mIcon.setInitials(true);
        holder.mIcon.setLettersNumber(2);

    }

    @Override
    public int getItemCount() {
        return Persons.size();
    }

    public void updateList(List<Person> Persons) {
        if (Persons.size() != this.Persons.size() || !this.Persons.containsAll(Persons)) {
            this.Persons = Persons;
            notifyDataSetChanged();
        }
    }

    public void removeItem(int position) {
        Persons.remove(position);
        notifyItemRemoved(position);
        Toast.makeText(c, "Deleted an entry!", Toast.LENGTH_SHORT).show();

    }

    public Person getItem(int position) {
        return Persons.get(position);
    }
}
