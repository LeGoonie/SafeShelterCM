package com.miniapps.maze;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.safeshelter.R;

public class SelectMazeActivity extends ListActivity {
    private TiltMazesDBAdapter mDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDB = new TiltMazesDBAdapter(getApplicationContext()).open();

        setListAdapter(new CursorAdapter(getApplicationContext(), mDB.allMazes(), true) {

            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                final LayoutInflater inflater = LayoutInflater.from(context);
                final View rowView = inflater.inflate(R.layout.select_maze_row_layout, parent, false);

                bindView(rowView, context, cursor);
                return rowView;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                final MapDesign m = MapDesigns.designList.get(cursor.getPosition());

                final ImageView mazeSolvedTickbox = (ImageView) view.findViewById(R.id.maze_solved_tick);
                final TextView mazeName = (TextView) view.findViewById(R.id.maze_name);
                final TextView mazeSolutionSteps = (TextView) view.findViewById(R.id.maze_solution_steps);

                if (cursor.getInt(TiltMazesDBAdapter.SOLUTION_STEPS_COLUMN) == 0) {
                    mazeSolvedTickbox.setImageResource(android.R.drawable.checkbox_off_background);
                    mazeSolutionSteps.setText("");
                } else {
                    mazeSolvedTickbox.setImageResource(android.R.drawable.checkbox_on_background);
                    mazeSolutionSteps.setText(
                            "Solved in "
                                    + cursor.getString(TiltMazesDBAdapter.SOLUTION_STEPS_COLUMN)
                                    + " steps");
                }

                mazeName.setText(
                        cursor.getString(TiltMazesDBAdapter.NAME_COLUMN)
                                + " (" + m.getSizeX() + "x" + m.getSizeY() + "), "
                                + m.getGoalCount() + " goal" + (m.getGoalCount() > 1 ? "s" : "")
                );
            }
        });
        setTitle(R.string.select_maze_title);
        setContentView(R.layout.select_maze);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent result = new Intent();
        result.putExtra("selected_maze", position);
        setResult(RESULT_OK, result);
        finish();
    }
}
