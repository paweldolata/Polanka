package et.put.poznan.pl.polanka;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLiteManager {
    private Context context;
    private static final int VERSION = 1;
    private SQLiteDatabase db;

    public SQLiteManager(Context context) {
        this.context = context;

        SQLiteOpenHelper helper = new SQLiteOpenHelper(context, StartActivity.DB_PATH, null, VERSION) {
            @Override
            public void onCreate(SQLiteDatabase db) {
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            }
        };

        //db = helper.getWritableDatabase();
        db = helper.getReadableDatabase();
    }

    public void closeConnection() {
        db.close();
    }

    public int getRoomsCount() { // testowe zapytanie
        Cursor cursor = db.rawQuery("SELECT COUNT(*) AS count FROM rooms;", null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public Room getRoomByNumber(String number) {
        Cursor cursor = db.rawQuery("SELECT room_number, floor FROM rooms WHERE room_number = ?;", new String[] {
            number
        });

        if(cursor.getCount() == 0)
            return null;

        cursor.moveToFirst();

        return new Room(cursor.getString(0), cursor.getInt(1), 0.f, 0.f); // TODO
    }

    public Lecturer getLecturerBySurname(String surname) {
        Cursor cursor = db.rawQuery("SELECT name, surname, email, room, chair FROM lecturers WHERE surname || ' ' || name = ?;", new String[] {
            surname
        });

        if(cursor.getCount() == 0)
            return null;

        cursor.moveToFirst();

        Cursor cursor2 = db.rawQuery("SELECT floor FROM rooms WHERE room_number = ?", new String []{
                cursor.getString(3)
        });

        if(cursor2.getCount() == 0)
            return null;

        cursor2.moveToFirst();

        return new Lecturer(cursor.getString(0), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor2.getString(0));
    }

    public Lecturer getLecturerByChair(String chair) {
        Cursor cursor = db.rawQuery("SELECT name, surname, email, room, chair FROM lecturers WHERE chair = ?;", new String[] {
            chair
        });

        if(cursor.getCount() == 0)
            return null;

        cursor.moveToFirst();

        Cursor cursor2 = db.rawQuery("SELECT floor FROM rooms WHERE room_number = ?", new String []{
                cursor.getString(3)
        });

        if(cursor2.getCount() == 0)
            return null;

        cursor2.moveToFirst();

        return new Lecturer(cursor.getString(0), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor2.getString(0));
    }

    public Room getRoomByFloor(int floor) {
        Cursor cursor = db.rawQuery("SELECT room_number, floor FROM rooms WHERE floor = ?;", new String[] {
            Integer.toString(floor)
        });

        if(cursor.getCount() == 0)
            return null;

        cursor.moveToFirst();

        return new Room(cursor.getString(0), cursor.getInt(1), 0.f, 0.f);
    }

    public ArrayList<String> getSurnames() {
        Cursor cursor = db.rawQuery("SELECT surname || ' ' || name FROM lecturers ORDER BY surname ASC;", null);

        ArrayList<String> surnames = new ArrayList<String>();

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            surnames.add(cursor.getString(0));
            cursor.moveToNext();
        }

        return surnames;
    }

    public List<String> getLecturersByRoomNumber(String which) {
        Cursor cursor = db.rawQuery("SELECT surname || ' ' || name FROM lecturers WHERE room = ? ORDER BY surname ASC;",
                new String[] {
                        which
                });

        ArrayList<String> surnames = new ArrayList<String>();
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            surnames.add(cursor.getString(0));
            cursor.moveToNext();
        }

        return surnames;
    }

    public ArrayList<String> getLecturersByChair(String which) {
        Cursor cursor = db.rawQuery("SELECT surname || ' ' || name FROM lecturers WHERE chair = ? ORDER BY surname ASC;",
                new String[] {
                        which
                });

        ArrayList<String> surnames = new ArrayList<String>();

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            surnames.add(cursor.getString(0));
            cursor.moveToNext();
        }

        return surnames;
    }

    public ArrayList<String> getLecturersByFloor(String which) {
        String q = "SELECT lecturers.surname || ' ' || lecturers.name FROM lecturers JOIN rooms ON rooms.room_number = lecturers.room WHERE rooms.floor = ? ORDER BY lecturers.surname ASC;";
        Cursor cursor = db.rawQuery(q,
            new String[] {
                    which
            });

        ArrayList<String> surnames = new ArrayList<String>();

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            surnames.add(cursor.getString(0));
            cursor.moveToNext();
        }

        return surnames;
    }
}