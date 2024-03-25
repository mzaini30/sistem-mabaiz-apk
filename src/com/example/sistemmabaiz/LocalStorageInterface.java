package com.example.sistemmabaiz;

  import android.content.Context;
  import android.database.Cursor;
  import android.database.sqlite.SQLiteDatabase;
  import android.webkit.JavascriptInterface;
  import android.webkit.WebView;
  
  public class LocalStorageInterface {
  
      private Context context;
      private SQLiteDatabase database;
  
      public LocalStorageInterface(Context context) {
          this.context = context;
          // Buat atau buka database SQLite
          database = context.openOrCreateDatabase("localStorageDB", Context.MODE_PRIVATE, null);
          // Buat tabel jika belum ada
          database.execSQL("CREATE TABLE IF NOT EXISTS localStorage (key TEXT PRIMARY KEY, value TEXT)");
      }
  
      // Method untuk menyimpan data ke database
      @JavascriptInterface
      public void setItem(String key, String value) {
          // Ganti data jika key sudah ada
          database.execSQL("INSERT OR REPLACE INTO localStorage (key, value) VALUES ('" + key + "', '" + value + "')");
      }
  
      // Method untuk mengambil data dari database
      @JavascriptInterface
      public String getItem(String key) {
          Cursor cursor = database.rawQuery("SELECT value FROM localStorage WHERE key='" + key + "'", null);
          String value = "";
          if (cursor.moveToFirst()) {
              value = cursor.getString(0);
          }
          cursor.close();
          return value;
      }
  
      // Method untuk menghapus data dari database
      @JavascriptInterface
      public void removeItem(String key) {
          database.execSQL("DELETE FROM localStorage WHERE key='" + key + "'");
      }
  
      // Method untuk membersihkan semua data dari database
      @JavascriptInterface
      public void clear() {
          database.execSQL("DELETE FROM localStorage");
      }
  }
  