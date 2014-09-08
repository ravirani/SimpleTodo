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
    public Date dueDate;

    @Column(name = "Created")
    private Date created;

    @Override
    public String toString() {
        return this.text;
    }

    public Boolean isDueDateInPast() {
        if (dueDate != null) {
            return new Date().after(dueDate);
        }

        return false;
    }

    public String getFormattedDueDate() {
        if (dueDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("E, d MMM");
            return dateFormat.format(dueDate);
        }

        return null;
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
}
