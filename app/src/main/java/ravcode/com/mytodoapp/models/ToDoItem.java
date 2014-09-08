package ravcode.com.mytodoapp;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ravi on 8/31/14.
 */
@Table(name = "Items")
public class ToDoItem extends Model {

    @Column(name = "Text", index = true)
    public String text;

    @Column(name = "Completed")
    public Boolean completed;

    @Column(name = "DueDate")
    public String dueDate;

    @Column(name = "Created")
    private Date created;

    @Override
    public String toString() {
        return this.text;
    }

    public Boolean isDueDateInPast() {
        if (dueDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
            Date dueDateObj = null;
            try {
                dueDateObj = dateFormat.parse(dueDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return new Date().after(dueDateObj);
        }

        return false;
    }

    public static ToDoItem addItem(String text) {
        ToDoItem item = new ToDoItem();
        item.text = text;
        item.completed = false;
        item.dueDate = null;
        item.created = new Date();
        item.save();

        return item;
    }

    public static ToDoItem getItemById(long id) {
        return new Select()
                .from(ToDoItem.class)
                .where("id = ?", id)
                .executeSingle();
    }

    public static List<ToDoItem> getAll() {
        return new Select()
                .from(ToDoItem.class)
                .orderBy("Created ASC")
                .execute();
    }

    public static String getFormattedDueDate(String dueDateString, Boolean include_year) {
        if (dueDateString != null) {
            // First lets validate if the date is in expected format and is the correct date
            SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
            Date validDate = null;
            try {
                validDate = dateFormat.parse(dueDateString);
            }
            catch (ParseException e) {
                return null;
            }

            if (!dateFormat.format(validDate).equals(dueDateString)) {
                return null;
            }

            String pattern = "E, d MMM";
            if (include_year) {
                pattern += ", yyyy";
            }

            dateFormat.applyPattern(pattern);
            return dateFormat.format(validDate);
        }

        return null;
    }
}
