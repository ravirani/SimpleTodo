package ravcode.com.mytodoapp;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import ravcode.com.mytodoapp.fragments.DatePickerFragment;


public class EditItemActivity extends FragmentActivity {

    ravcode.com.mytodoapp.ToDoItem toDoItem;
    long item_id;
    private String enteredDueDateString;
    private static final String TAG = "EditItemActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        item_id = getIntent().getLongExtra("item_id", 1L);
        toDoItem = ravcode.com.mytodoapp.ToDoItem.getItemById(item_id);

        if (toDoItem != null) {
            EditText etNewItem = (EditText) findViewById(R.id.itemEditTextInput);
            etNewItem.setText(toDoItem.text, TextView.BufferType.EDITABLE);
            setDueDate(toDoItem.dueDate);
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

        if (enteredDueDateString != null) {
            toDoItem.dueDate = enteredDueDateString;
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
        DialogFragment newFragment;
        if (enteredDueDateString != null) {
            String[] currentDate = enteredDueDateString.split("/");
            newFragment = DatePickerFragment.newInstance(Integer.parseInt(currentDate[2]), Integer.parseInt(currentDate[0]) - 1, Integer.parseInt(currentDate[1]));
        }
        else {
            final Calendar c = Calendar.getInstance();
            newFragment = DatePickerFragment.newInstance(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        }

        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void onDateSet(int year, int month, int day) {
        setDueDate((month + 1) + "/" + day + "/" + year);
    }

    private void setDueDate(String dueDateString) {
        String formattedDueDateString = ravcode.com.mytodoapp.ToDoItem.getFormattedDueDate(dueDateString, true);
        if (formattedDueDateString != null) {
            enteredDueDateString = dueDateString;
            Button datePickerButton = (Button) findViewById(R.id.dueDateButton);
            datePickerButton.setText("Due " + formattedDueDateString);
        } else {
            Toast.makeText(getApplicationContext(), "Invalid Due Date!", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
