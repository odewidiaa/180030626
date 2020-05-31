package com.bh183.widiantara;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "db_buku";
    private final static String TABLE_BUKU ="t_buku";
    private final static String KEY_ID_BUKU = "ID_buku";
    private final static String KEY_JUDUL = "Judul";
    private final static String KEY_TGL = "Tgl_Terbit";
    private final static String KEY_BUKU = "Buku";
    private final static String KEY_PENERBIT = "Penerbit";
    private final static String KEY_PENULIS = "Penulis";
    private final static String KEY_BAHASAN = "Bahasan";
    private Context context;


    public DatabaseHandler(Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_BUKU = "CREATE TABLE " + TABLE_BUKU
                + "(" + KEY_ID_BUKU + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_JUDUL + " TEXT, " + KEY_TGL + " DATE, "
                + KEY_BUKU + " TEXT, " + KEY_PENERBIT + " TEXT, "
                + KEY_PENULIS + " TEXT, " + KEY_BAHASAN + " TEXT);";

        db.execSQL(CREATE_TABLE_BUKU);
        inisialisasiBukuAwal(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_BUKU;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void tambahBuku(Buku dataBuku){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataBuku.getJudul());
        cv.put(KEY_TGL, dataBuku.getTerbit());
        cv.put(KEY_BUKU, dataBuku.getBuku());
        cv.put(KEY_PENERBIT, dataBuku.getPenerbit());
        cv.put(KEY_PENULIS, dataBuku.getPenulis());
        cv.put(KEY_PENULIS, dataBuku.getPenulis());
        cv.put(KEY_BAHASAN, dataBuku.getBahasan());


        db.insert(TABLE_BUKU, null, cv);
        db.close();
    }

    public void tambahBuku(Buku dataBuku, SQLiteDatabase db){
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataBuku.getJudul());
        cv.put(KEY_TGL, dataBuku.getTerbit());
        cv.put(KEY_BUKU, dataBuku.getBuku());
        cv.put(KEY_PENERBIT, dataBuku.getPenerbit());
        cv.put(KEY_PENULIS, dataBuku.getPenulis());
        cv.put(KEY_BAHASAN, dataBuku.getBahasan());
        db.insert(TABLE_BUKU, null, cv);
    }

    public void editBuku(Buku dataBuku){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataBuku.getJudul());
        cv.put(KEY_TGL, dataBuku.getTerbit());
        cv.put(KEY_BUKU, dataBuku.getBuku());
        cv.put(KEY_PENERBIT, dataBuku.getPenerbit());
        cv.put(KEY_PENULIS, dataBuku.getPenulis());
        cv.put(KEY_BAHASAN, dataBuku.getBahasan());

        db.update(TABLE_BUKU, cv, KEY_ID_BUKU + "=?", new String[]{String.valueOf(dataBuku.getIdBuku())});
        db.close();
    }

    public void hapusBuku(int idBuku){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_BUKU, KEY_ID_BUKU + "=?", new String[]{String.valueOf(idBuku)});
        db.close();
    }

    public ArrayList<Buku> getAllBuku(){
        ArrayList<Buku> dataBuku = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_BUKU;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if(csr.moveToFirst()){
            do {
                Buku tempBuku = new Buku(
                        csr.getInt(0),
                        csr.getString(1),
                        csr.getString(2),
                        csr.getString(3),
                        csr.getString(4),
                        csr.getString(5),
                        csr.getString(6)


                );

                dataBuku.add(tempBuku);
            } while (csr.moveToNext());
        }

        return dataBuku;
    }

    private String storeImageFile(int id) {
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    private void inisialisasiBukuAwal(SQLiteDatabase db){
        int idBuku = 0;

        // Menambahkan data film ke 1
        Buku buku1 = new Buku(
                idBuku,
                "99% Sukses Menghadapi UN SMK AKP 2020",
                "Terbitan : 2019",
                storeImageFile(R.drawable.buku1),
                "Penerbit : CMedia",
                "Penulis   : Ikah Atikah",
                "Buku ini mengupas kisi-kisi UNBK SMK AKP 2020 secara detail dan mudah dipahami. Berbagai keunggulan buku ini akan menjadi bekal berharga bagi siswa untuk mencapai nilai 10 dalam semua mata pelajaran UNBK SMK AKP 2020, lengkap dengan bonus software UNBK."
        );

        tambahBuku(buku1, db);
        idBuku++;

        // Menambahkan data film ke 2
        Buku buku2 = new Buku(
                idBuku,
                "Bahagia Is Me",
                "Terbitan : 2018",
                storeImageFile(R.drawable.buku2),
                "Penerbit : Lotus Publisher",
                "Penulis   : Habibun Nazar",
                "Jika biasanya mama selalu masak dua sejoli yang tak terpisahkan yaitu tahu dan tempe, pas gue ada di rumah dua sejoli itu tetap ada. Namun ada plus-nya seperti ayam goreng, ayam bakar. Bahkan ayam yang masih hidup mama ikat kakinya dan direbahkan di sebelah kami yang lagi makan. Gue tanya, “Ini untuk apa, Ma?” "
        );

        tambahBuku(buku2, db);
        idBuku++;

        // Menambahkan data film ke 3
        Buku buku3 = new Buku(
                idBuku,
                "Detektif Kindaichi",
                "Terbitan : 2019",
                storeImageFile(R.drawable.buku3),
                "Penerbit : Elex Media",
                "Penulis   : Seimaru Abagi",
                "Hajime menumpang kereta tujuan Hokkaido setelah menerima surat ancaman dari oknum yang menyebut dirinya sendiri Dalang dari Neraka. Kemudian ketua Grup Sulap Ilusi yang sama-sama menumpang terbunuh di kompartemen kereta yang tertutup. Tidak hanya itu, mayatnya menghilang dalam sekejap."
        );

        tambahBuku(buku3, db);
        idBuku++;

        // Menambahkan data film ke 4
        Buku buku4 = new Buku(
                idBuku,
                "Men Coblong",
                "Terbitan : 2017",
                storeImageFile(R.drawable.buku4),
                "Penerbit : Gramedia",
                "Penulis   : Oka Rusmini",
                "Dengan bahasa fasih yang merupakan ciri khas profesinya sebagai jurnalis dan sastrawan. Oka Rusmini menyampaikan tanggapan. Kritik dan sindiran tajam tanpa menyakiti terhadap banyak hal yang ada dan terjadi di sekitar kita. Buku ini menunjukan kualitasnya sebagai penulis esai. - Sapardi Djoko Damono, sastrawan."
        );

        tambahBuku(buku4, db);
        idBuku++;

        // Menambahkan data film ke 5
        Buku buku5 = new Buku(
                idBuku,
                "99+ Wonderful Mind",
                "Terbitan : 2018",
                storeImageFile(R.drawable.buku5),
                "Penerbit : Cikal Aksara",
                "Penulis   : Yurinda Rini",
                "99+ Wonderful Mind menguak rahasia kekuatan pikiran, membantu Anda memahami bagaimana pikiran menentukan arah hidup, mencapai kebahagiaan, dan meraih keberuntungan.Seperti multivitamin yang memelihara pikiran tetap jernih dan sehat, membacanya setiap hari memberi energi baru pada setiap tarikan napas, menghapus kepenatan, dan kesesakan dada."
        );

        tambahBuku(buku5, db);
        idBuku++;

    }
}
