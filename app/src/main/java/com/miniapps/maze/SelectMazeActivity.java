package com.miniapps.maze;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.safeshelter.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SelectMazeActivity extends ListActivity {
    private TiltMazesDBAdapter mDB;

    boolean currentFocus;
    boolean isPaused;
    Handler collapseNotificationHandler;

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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        currentFocus = hasFocus;

        if (!hasFocus) {
            collapseNow();
        }
    }

    public void collapseNow() {
        if (collapseNotificationHandler == null) {
            collapseNotificationHandler = new Handler();
        }

        if (!currentFocus && !isPaused) {
            collapseNotificationHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    Object statusBarService = getSystemService("statusbar");
                    Class<?> statusBarManager = null;

                    try {
                        statusBarManager = Class.forName("android.app.StatusBarManager");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    Method collapseStatusBar = null;

                    try {
                        if (Build.VERSION.SDK_INT > 16) {
                            collapseStatusBar = statusBarManager .getMethod("collapsePanels");
                        } else {
                            collapseStatusBar = statusBarManager .getMethod("collapse");
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }

                    collapseStatusBar.setAccessible(true);

                    try {
                        collapseStatusBar.invoke(statusBarService);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    if (!currentFocus && !isPaused) {
                        collapseNotificationHandler.postDelayed(this, 100L);
                    }

                }
            }, 1L);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Do nothing or catch the keys you want to block
        return false;
    }
}
