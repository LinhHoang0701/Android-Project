package com.example.androidproject.DAL;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.content.res.AppCompatResources;

import com.example.androidproject.Entity.IEntity;
import com.example.androidproject.R;

import java.io.ByteArrayOutputStream;

public abstract class BaseSQLiteHelper<T extends IEntity> extends SQLiteOpenHelper  {
    private static final String DB_NAME = "PRM391_ProjectDB.db";

    protected Context context;

    protected BaseSQLiteHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }



    protected Cursor getData(String sql, String[] args) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, args);
    }

    protected void queryData(String sql, String[] args){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql, args);
    }

    protected byte[] imageViewToByte(Drawable image) {

        Bitmap bitmap = ((BitmapDrawable) image).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, stream);
        return stream.toByteArray();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS \"User\" (\n" +
                "\t\"username\"\tTEXT NOT NULL UNIQUE,\n" +
                "\t\"password\"\tTEXT NOT NULL,\n" +
                "\t\"fullname\"\tTEXT NOT NULL,\n" +
                "\t\"avatar\"\tBLOB NOT NULL DEFAULT '',\n" +
                "\t\"email\"\tTEXT NOT NULL DEFAULT '',\n" +
                "\t\"phone\"\tTEXT NOT NULL DEFAULT '',\n" +
                "\t\"address\"\tTEXT NOT NULL DEFAULT '',\n" +
                "\tPRIMARY KEY(\"username\")\n" +
                ");";
        db.execSQL(sql);
        sql = "CREATE TABLE IF NOT EXISTS \"PurchaseDetail\" (\n" +
                "\t\"master_id\"\tINTEGER NOT NULL,\n" +
                "\t\"product_id\"\tINTEGER NOT NULL,\n" +
                "\t\"purchase_quantity\"\tINTEGER NOT NULL,\n" +
                "\tFOREIGN KEY(\"product_id\") REFERENCES \"Product\"(\"product_id\"),\n" +
                "\tFOREIGN KEY(\"master_id\") REFERENCES \"PurchaseMaster\"(\"master_id\")\n" +
                ");";
        db.execSQL(sql);
        sql = "CREATE TABLE IF NOT EXISTS \"ProductImage\" (\n" +
                "\t\"product_id\"\tINTEGER NOT NULL,\n" +
                "\t\"image\"\tBLOB NOT NULL,\n" +
                "\tFOREIGN KEY(\"product_id\") REFERENCES \"Product\"(\"product_id\")\n" +
                ");";
        db.execSQL(sql);
        sql = "CREATE TABLE IF NOT EXISTS \"PurchaseMaster\" (\n" +
                "\t\"master_id\"\tTEXT NOT NULL PRIMARY KEY UNIQUE,\n" +
                "\t\"username\"\tTEXT NOT NULL,\n" +
                "\t\"purchase_time\"\tDATE NOT NULL DEFAULT (datetime('now','localtime')),\n" +
                "\t\"destination\"\tTEXT NOT NULL DEFAULT '',\n" +
                "\t\"payment_method\"\tTEXT NOT NULL DEFAULT 'Cash On Delivery',\n" +
                "\t\"status\"\tTEXT NOT NULL DEFAULT 'Pending',\n" +
                "\tFOREIGN KEY(\"username\") REFERENCES \"User\"(\"username\")\n" +
                ");";
        db.execSQL(sql);
        sql = "CREATE TABLE IF NOT EXISTS \"Admin\" (\n" +
                "\t\"username\"\tTEXT NOT NULL UNIQUE,\n" +
                "\t\"password\"\tTEXT NOT NULL,\n" +
                "\tPRIMARY KEY(\"Username\")\n" +
                ");";
        db.execSQL(sql);
        sql = "CREATE TABLE IF NOT EXISTS \"Product\" (\n" +
                "\t\"product_id\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                "\t\"name\"\tTEXT NOT NULL DEFAULT '',\n" +
                "\t\"price\"\tREAL NOT NULL DEFAULT 0,\n" +
                "\t\"description\"\tTEXT NOT NULL DEFAULT '',\n" +
                "\t\"category\"\tTEXT NOT NULL DEFAULT '',\n" +
                "\t\"provider\"\tTEXT NOT NULL DEFAULT '',\n" +
                "\t\"rating\"\tREAL NOT NULL DEFAULT 0,\n" +
                "\t\"rating_count\"\tINTEGER NOT NULL DEFAULT 0,\n" +
                "\t\"time\"\tDATE NOT NULL DEFAULT (datetime('now','localtime'))\n" +
                ");";
        db.execSQL(sql);

        sql = "CREATE TABLE \"Review\" (\n" +
                "\t\"username\"\tTEXT NOT NULL,\n" +
                "\t\"product_id\"\tINTEGER NOT NULL,\n" +
                "\t\"rating\"\tINTEGER NOT NULL DEFAULT 0,\n" +
                "\t\"comment\"\tTEXT,\n" +
                "\t\"time\"\tDATE NOT NULL DEFAULT (datetime('now','localtime'))," +
                "\tPRIMARY KEY(\"username\",\"product_id\"),\n" +
                "\tFOREIGN KEY(\"product_id\") REFERENCES \"Product\"(\"product_id\"),\n" +
                "\tFOREIGN KEY(\"username\") REFERENCES \"User\"(\"username\")\n" +
                ")";
        db.execSQL(sql);


        sql = "INSERT INTO \"User\" (\"username\", \"password\", \"fullname\", \"avatar\", \"email\", \"phone\", \"address\") VALUES ('duccmse06002', 'duccmse06002', 'th???ng g?? ??? nh?? th??? ?????c th??nh t???n vi??n', '', 'duccmse06002@fpt.edu.vn', '0123456798', 'DOM D, FPT University');";
        db.execSQL(sql);
        sql = "INSERT INTO \"Product\" (\"product_id\",\"name\",\"price\",\"description\",\"category\",\"provider\",\"rating\",\"rating_count\",\"time\") VALUES (1,'Iphone X 128Gb Rose Gold',999.99,'The iPhone X is one of the first phones from Apple to come with a glass back, and while it sports a brand new design and extra features like Face ID and wireless charging, the color choices are rather limited.','Smart Phone','Apple Inc.',4.5,50,'2019-06-24 08:50:07'),\n" +
                " (2, 'Samsung Galaxy S10 64Gb Canary Yellow', 969.99, 'The Galaxy S10, S10+, and S10e are available in a variety of striking colors. Whether you need some help deciding which style is best for you, or you''d just like to see everything that''s available, here are all the colors you can get for Samsung''s latest phones.', 'Smart Phone', 'Samsung Group',0.0,0, '2019-06-24 08:50:09'),\n" +
                " (3,'Seiko 5 Military Black 37mm',80.0,'Seiko 5 Military Black Dial Automatic Watch with Black Canvas Strap #SNK809K2. ... The watch is powered by a Seiko Automatic, self-winding movement. A scratch resistant Hardlex crystal is used along with an exhibition back that allows you to view the action of the movement. Case diameter: 37 mm.','Watch','Seiko Watch Corporation',0.0,0,'2019-06-24 10:17:36'),\n" +
                " (4,'Casio Men''s G-shock DW5600E-1V',100.0,'Shock Resistant, 200 Meter Water Resistant. Case diameter : 45 mm\n" +
                "\n" +
                "EL Backlight with Afterglow, Multi-function Alarm, 1/100 second stopwatch. TO set time and Date:1.Press A while in the Timekeeping Mode. The seconds digits flash on the display because they are selected 2. Press C to change the selection in the following sequence\n" +
                "\n" +
                "Countdown Timer, Auto-repeat function, Hourly Time Signal, Auto Calendar (pre-programmed until the year 2039)\n" +
                "\n" +
                "If you do not operate any button for a few minutes while a selection is flashing. the flashing stops and the watch goes back to the timekeeping mode automatically\n" +
                "\n" +
                "In an effort to avoid the navigation buttons being pushed accidentally they are purposely designed to be a bit harder to push in. This is not a defect or flaw.','Watch','Casio Computer Co., Ltd',0.0,0,'2019-06-24 10:20:05'),\n" +
                " (5,'Ch?? Ph??o',5.3,'Ch?? Ph??o l?? m???t truy???n ng???n n???i ti???ng c???a nh?? v??n Nam Cao vi???t v??o th??ng 2 n??m 1941. Ch?? Ph??o l?? m???t t??c ph???m xu???t s???c, th??? hi???n ngh??? thu???t vi???t truy???n ?????c ????o c???a Nam Cao, ?????ng th???i l?? m???t t???n bi k???ch c???a m???t ng?????i n??ng d??n ngh??o b??? tha h??a trong x?? h???i. Truy???n ???? ???????c ????a v??o s??ch gi??o khoa Ng??? v??n 11, t???p 1. Ch?? Ph??o c??ng l?? t??n nh??n v???t ch??nh c???a truy???n.','Book','Nh?? xu???t b???n ABC_XYZ',0.0,0,'2019-06-24 10:26:06'),\n" +
                " (6,'S??? ?????',3.9,'S??? ????? l?? m???t ti???u thuy???t v??n h???c c???a nh?? v??n V?? Tr???ng Ph???ng, ????ng ??? H?? N???i b??o t??? s??? 40 ng??y 7 th??ng 10 n??m 1936 v?? ???????c in th??nh s??ch l???n ?????u v??o n??m 1938. Nhi???u nh??n v???t v?? c??u n??i trong t??c ph???m ???? ??i v??o cu???c s???ng ?????i th?????ng v?? t??c ph???m ???? ???????c d???ng th??nh k???ch, phim. Nh??n v???t ch??nh c???a S??? ????? l?? Xu??n - bi???t danh l?? Xu??n T??c ?????, t??? ch??? l?? m???t k??? b??? coi l?? h??? l??u, b???ng nh???y l??n t???ng l???p danh gi?? c???a x?? h???i nh??? tr??o l??u ??u h??a c???a gi???i ti???u t?? s???n H?? N???i khi ????. T??c ph???m S??? ?????, c??ng nh?? c??c t??c ph???m kh??c c???a V?? Tr???ng Ph???ng ???? t???ng b??? c???m l??u h??nh t???i Vi???t Nam D??n ch??? C???ng h??a tr?????c n??m 1975 c??ng nh?? t???i Vi???t Nam th???ng nh???t cho ?????n n??m 1986.[1]','Book','Nh?? xu???t b???n ABC_XYZ',0.0,0,'2019-06-24 10:29:11');";
        db.execSQL(sql);

        //sql = "INSERT INTO \"PurchaseMaster\" (\"master_id\",\"username\",\"purchase_time\",\"destination\",\"status\") VALUES ('2s','duccmse06002','2019-06-24 11:02:26','''FPT University, Th???ch h??a th???ch th???t''','Delivered');";
        sql = "INSERT INTO \"PurchaseMaster\" (\"master_id\",\"username\",\"purchase_time\",\"destination\",\"status\",\"payment_method\") VALUES ('2s','duccmse06002','2019-06-24 11:02:26','''FPT University, Th???ch h??a th???ch th???t''','Delivered','Cash On Delivery');";
        db.execSQL(sql);

        sql = "INSERT INTO \"PurchaseMaster\" (\"master_id\",\"username\",\"purchase_time\",\"destination\",\"status\",\"payment_method\") VALUES ('2222','duccmse06002','2019-06-24 11:02:26','''FPT University, Th???ch h??a th???ch th???t''','Delivered','Cash On Delivery');";

        db.execSQL(sql);
        sql = "INSERT INTO \"PurchaseDetail\" (\"master_id\",\"product_id\",\"purchase_quantity\") VALUES ('2s',1,4);";
        db.execSQL(sql);

        sql = "INSERT INTO \"PurchaseDetail\" (\"master_id\",\"product_id\",\"purchase_quantity\") VALUES ('2222',3,4);";
        db.execSQL(sql);
        sql = "INSERT INTO \"PurchaseDetail\" (\"master_id\",\"product_id\",\"purchase_quantity\") VALUES ('2222',4,3);";
        db.execSQL(sql);
        sql = "INSERT INTO \"PurchaseDetail\" (\"master_id\",\"product_id\",\"purchase_quantity\") VALUES ('2222',5,8);";
        db.execSQL(sql);

        sql = "INSERT INTO \"Admin\" (\"username\",\"password\") VALUES ('duccm4','duccm4'),\n" +
                " ('trungtq','trungtq'),\n" +
                " ('dungdv11','dungdv11');";
        db.execSQL(sql);
        sql = "INSERT INTO \"Review\" (\"username\", \"product_id\", \"rating\", \"comment\", \"time\") VALUES ('duccmse06002', '1', '4', 'OK', '2019-07-11 10:33:05');";
        db.execSQL(sql);


        db.insert("ProductImage",null, initImageInsert(1, imageViewToByte(AppCompatResources.getDrawable(context , R.drawable.img1_1))));
        db.insert("ProductImage",null, initImageInsert(1, imageViewToByte(AppCompatResources.getDrawable(context , R.drawable.img1_2))));
        db.insert("ProductImage",null, initImageInsert(1, imageViewToByte(AppCompatResources.getDrawable(context , R.drawable.img1_3))));
        db.insert("ProductImage",null, initImageInsert(2, imageViewToByte(AppCompatResources.getDrawable(context , R.drawable.img2_1))));
        db.insert("ProductImage",null, initImageInsert(2, imageViewToByte(AppCompatResources.getDrawable(context , R.drawable.img2_2))));
        db.insert("ProductImage",null, initImageInsert(2, imageViewToByte(AppCompatResources.getDrawable(context , R.drawable.img2_3))));
        db.insert("ProductImage",null, initImageInsert(3, imageViewToByte(AppCompatResources.getDrawable(context , R.drawable.img3_1))));
        db.insert("ProductImage",null, initImageInsert(3, imageViewToByte(AppCompatResources.getDrawable(context , R.drawable.img3_2))));
        db.insert("ProductImage",null, initImageInsert(3, imageViewToByte(AppCompatResources.getDrawable(context , R.drawable.img3_3))));
        db.insert("ProductImage",null, initImageInsert(4, imageViewToByte(AppCompatResources.getDrawable(context , R.drawable.img4_1))));
        db.insert("ProductImage",null, initImageInsert(4, imageViewToByte(AppCompatResources.getDrawable(context , R.drawable.img4_2))));
        db.insert("ProductImage",null, initImageInsert(4, imageViewToByte(AppCompatResources.getDrawable(context , R.drawable.img4_3))));
        db.insert("ProductImage",null, initImageInsert(5, imageViewToByte(AppCompatResources.getDrawable(context , R.drawable.img5_1))));
        db.insert("ProductImage",null, initImageInsert(5, imageViewToByte(AppCompatResources.getDrawable(context , R.drawable.img5_2))));
        db.insert("ProductImage",null, initImageInsert(5, imageViewToByte(AppCompatResources.getDrawable(context , R.drawable.img5_3))));
        db.insert("ProductImage",null, initImageInsert(6, imageViewToByte(AppCompatResources.getDrawable(context , R.drawable.img6_1))));
        db.insert("ProductImage",null, initImageInsert(6, imageViewToByte(AppCompatResources.getDrawable(context , R.drawable.img6_2))));
        db.insert("ProductImage",null, initImageInsert(6, imageViewToByte(AppCompatResources.getDrawable(context , R.drawable.img6_3))));
        db.update("User", initAvatarUpdate(imageViewToByte(AppCompatResources.getDrawable(context, R.drawable.avatar))), "username = ?", new String[] {"duccmse06002"});


    }

    //for init database
    private ContentValues initImageInsert(int id, byte[] image) {
        ContentValues record = new ContentValues();
        record.put("product_id", id);
        record.put("image", image);
        return record;
    }
    //for init database
    private ContentValues initAvatarUpdate(byte[] avatar) {
        ContentValues record = new ContentValues();
        record.put("avatar", avatar);
        return record;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
