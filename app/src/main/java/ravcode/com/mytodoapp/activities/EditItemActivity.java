package ravcode.com.mytodoapp;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ravcode.com.mytodoapp.R;
import ravcode.com.mytodoapp.ToDoItem;
import ravcode.com.mytodoapp.fragments.DatePickerFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import android.util.Log;


public class EditItemActivity extends FragmentActivity {

    ToDoItem toDoItem;
    long item_id;
    private static final String TAG = "EditItemActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        item_id = getIntent().getLongExtra("item_id", 1L);
        toDoItem = ToDoItem.getItemById(item_id);

        if (toDoItem != null) {
            EditText etNewItem = (EditText) findViewById(R.id.itemEditTextInput);
            etNewItem.setText(toDoItem.text, TextView.BufferType.EDITABLE);

            if (toDoItem.dueDate != null) {
                Button datePickerButton = (Button) findViewById(R.id.datePicker);
                datePickerButton.setText(toDoItem.dueDate);
            }
        }

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveTodoItem(View v) {
        EditText itemEditTextInput = (EditText)findViewById(R.id.itemEditTextInput);
        String modifiedToDoText = itemEditTextInput.getText().toString();

        Button dueDateButton = (Button)findViewById(R.id.datePicker);
        String[] currentDate = ((String)dueDateButton.getText()).split("/");
        if (currentDate.length == 3) {
            toDoItem.dueDate = (String)dueDateButton.getText();
            setResult(RESULT_OK);
        }

        if (modifiedToDoText != null && !modifiedToDoText.isEmpty()) {
            toDoItem.text = modifiedToDoText;
            setResult(RESULT_OK);
        }

        toDoItem.save();
        finish();
    }

    public void showDatePicker(View view) {
        // Really hacky implementation to finish this part
        Button dueDateButton = (Button)findViewById(R.id.datePicker);
        String[] currentDate = ((String)dueDateButton.getText()).split("/");
        DialogFragment newFragment;
        if (currentDate.length == 3) {
            newFragment = DatePickerFragment.newInstance(Integer.parseInt(currentDate[2]), Integer.parseInt(currentDate[0]) - 1, Integer.parseInt(currentDate[1]));
        }
        else {
            final Calendar c = Calendar.getInstance();
            newFragment = DatePickerFragment.newInstance(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        }

        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void onDateSet(int year, int month, int day) {
        Button dueDateButton = (Button)findViewById(R.id.datePicker);
        dueDateButton.setText((month + 1) + "/" + day + "/" + year);
    }
}
