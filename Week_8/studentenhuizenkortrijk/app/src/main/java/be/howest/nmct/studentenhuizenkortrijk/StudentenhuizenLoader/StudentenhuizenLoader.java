package be.howest.nmct.studentenhuizenkortrijk.StudentenhuizenLoader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.BaseColumns;
import android.util.JsonReader;
import android.util.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonathan on 23/04/2015.
 */
public class StudentenhuizenLoader extends AsyncTaskLoader<Cursor> {
    private Cursor mCursor;
    private static Object lock = new Object();
    private static final String url = "http://data.kortrijk.be/studentenvoorzieningen/koten.json";

    public static List<be.howest.nmct.studentenhuizenkortrijk.admin.Studentenhuis> getLijstStudentenhuizen() {
        return lijstStudentenhuizen;
    }

    public static List<be.howest.nmct.studentenhuizenkortrijk.admin.Studentenhuis> lijstStudentenhuizen = null;

    private final String[] mColumnNames = new String[] {
            BaseColumns._ID,
            Contract.Studentenhuis.COLUMN_STUDENTENHUIS_STRAAT,
            Contract.Studentenhuis.COLUMN_STUDENTENHUIS_HUISNUMMER,
            Contract.Studentenhuis.COLUMN_STUDENTENHUIS_GEMEENTE,
            Contract.Studentenhuis.COLUMN_STUDENTENHUIS_AANTALKAMERS
    };

    public StudentenhuizenLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        //super.onStartLoading(); //custom listview

        if (mCursor != null) {
            deliverResult(mCursor);
        }
        if (takeContentChanged() || mCursor == null) {
            forceLoad();
        }
    }

    @Override
    public Cursor loadInBackground() {
        if (mCursor == null) {
            loadCursor();
        }

        return mCursor;
    }

    private void loadCursor() {
        synchronized (lock) {
            if (mCursor != null) return;

            MatrixCursor cursor = new MatrixCursor(mColumnNames);
            InputStream input = null;
            JsonReader reader = null;

            try {
                input = new URL(url).openStream();
                reader = new JsonReader(new InputStreamReader(input, "UTF-8"));

                lijstStudentenhuizen = new ArrayList<be.howest.nmct.studentenhuizenkortrijk.admin.Studentenhuis>();

                reader.beginArray();
                while (reader.hasNext()) {

                    reader.beginObject();

                    String adres = "";
                    String huisnummer = "";
                    String gemeente = "";
                    int aantalKamers = 0;

                    while (reader.hasNext()) {
                        String name = reader.nextName();
                        if (name.equals("ADRES")) {
                            adres = reader.nextString();
                        } else if (name.equals("HUISNR")) {
                            //opgelet zoawel numerieke waarden (nextInt) als string-waarden (nextString) komen voor!
                            //Voer controle uit via 'peek' -methode: Return the type of the next token without consuming it
                            if (reader.peek().equals(JsonToken.NULL)) {
                                reader.skipValue();
                            } else if (reader.peek().equals(JsonToken.STRING)) {
                                huisnummer = reader.nextString();

                            } else if (reader.peek().equals(JsonToken.NUMBER)) {
                                huisnummer += reader.nextInt();
                            }
                        } else if (name.equals("GEMEENTE")) {
                            if (reader.peek().equals(JsonToken.NULL)) {
                                reader.skipValue();
                            } else if (reader.peek().equals(JsonToken.STRING)) {
                                gemeente = reader.nextString();

                            } else if (reader.peek().equals(JsonToken.NUMBER)) {
                                gemeente += reader.nextInt();
                            }
                        } else if (name.equals("aantal kamers")) {
                            if (reader.peek().equals(JsonToken.NULL)) {
                                reader.skipValue();
                            } else if (reader.peek().equals(JsonToken.STRING)) {
                                aantalKamers = Integer.parseInt(reader.nextString());

                            } else if (reader.peek().equals(JsonToken.NUMBER)) {
                                aantalKamers = reader.nextInt();
                            }
                        } else {
                            reader.skipValue();
                        }

                        be.howest.nmct.studentenhuizenkortrijk.admin.Studentenhuis huis = new be.howest.nmct.studentenhuizenkortrijk.admin.Studentenhuis(adres, huisnummer, gemeente, aantalKamers);
                        lijstStudentenhuizen.add(huis);
                    }

                    int id = 1;
                    MatrixCursor.RowBuilder row = cursor.newRow();
                    row.add(id);
                    row.add(adres);
                    row.add(huisnummer);
                    row.add(gemeente);
                    row.add(aantalKamers);
                    id++;

                    reader.endObject();
                }
                reader.endArray();

                mCursor = cursor;

            } catch(IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                }
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
