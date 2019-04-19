package com.example.android.pets.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import com.example.android.pets.data.PetContract;
import android.database.sqlite.SQLiteDatabase;

public class PetProvider extends ContentProvider{
  private PetDbHelper mDbHelper;
  private static final int PETS = 100;
  private static final int PET_ID = 101;
  private static final UriMatcher sUriMatcher = new UriMatcher(UriMatchedr.NO_MATCH);
  
  static {
    sUriMatcher.addURI(PetContract.CONTENT_AUTHORITY, PetContract.PATH_PETS, PETS);
    sUriMatcher.addURI(PetContract.CONTENT_AUTHORITY, PetContract.PATH_PETS + "/#", PET_ID);
  }
  
  
  @Override
  public boolean onCreate(){
    mDbHelper = new PetDbHelper(getContext());
    return true;
  }
  
  @Override
  public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
    SQLiteDatabase db = mDbHelper.getReadableDatabase();
    Cursor cursor;
    int match = sUriMatcher.match(uri);
    switch(match){
      case PETS:
        cursor=db.query(PetEntry.TABLE_NAME, projection, selection, selectionArgsUpdate, null, null, sortOrder);
        break;
      case PET_ID:
        selection = PetEntry._ID + "=?";
        selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri)) };
        cursor = db.query(PetEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        break;
      default:
        throw new IllegalArgumentsException("Cannot query unknown URI : " + uri);
    }
    return cursor;
  }
  
  @Override
  public String getType(Uri uri){
    return null;
  }
  
  @Override
  public Uri insert(Uri uri, ContentValues contentValues) {
    return null;
  }
  
  @Override
  public int delete(Uri uri, String s, String[] strings) {
    return 0;
  }
  
  @Override
  public int update(Uri uri, ContentValues contentValues, String s, String[] strings){
    return 0;
  }
}
