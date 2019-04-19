package com.example.android.pets.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import com.example.android.pets.data.PetContract;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public static final String LOG_TAG = PetProvider.class.getSimpleName();

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
        throw new IllegalArgumentException("Cannot query unknown URI : " + uri);
    }
    return cursor;
  }
  
  @Override
  public String getType(Uri uri){
    return null;
  }
  
  @Override
  public Uri insert(Uri uri, ContentValues contentValues) {
    final int match = sUriMatcher.match(uri);
    switch(match){
      case PETS:
        return insertPet(uri, contentValues);
        bresk;
      default:
        throw new IllegalArgumentException("Insertion is not supported for " + uri);
    }
  }
  
  private Uri insertPet(Uri uri, ContentValues contentValues){
    String name=contentValues.getAsString(PetEntry.COLUMN_PET_NAME);
    if(name == null)
      throw new IllegalArgumentException("Pet requires a name");
    int gender = contentValues.getAsInteger(PetEntry.COLUMN_PET_GENDER);
    if (gender == null || !PetEntry.isValidGender(gender))
      throw new IllegalArgumentException("Pet requires valid gender");
    int weight = contentValues.getAsInteger(PetEntry.COLUMN_PET_WEIGHT);
    if (weight != null && weight < 0)
      throw new IllegalArgumentExcpetion("Pet requires valid weight");
    SQLiteDatabase db = mDbHelper.getWritableDatabase();
    long id = db.insert(PetEntry.TABLE_NAME, null, contentValues);
    if(id == -1) {
      Log.e(LOG_TAG, "Failed to insert row for : " + uri);
      return null;
    }
    return ContentUris.withAppendedId(uri, id);
  }
  
  @Override
  public int delete(Uri uri, String s, String[] strings) {
    return 0;
  }
  
  @Override
  public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs){
    final int match = sUriMatcher.match(uri);
    switch(match){
      case PETS:
        return updatePet(uri, contentValues, selection, selectionArgs);
        break;
      case PET_ID:
        selection = PetEntry.ID + "=?";
        selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri)) };
        return updatePet(uri, contentValues, selection, selectionArgs);
        break;
      default:
        throw new IllegalArgumentExcpetion("Update is not supported for : " + uri);
    }
  }
  
  private int updatePet(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs{
    if (contentValues.containsKey(PetEntry.COLUMN_PET_NAME)){
      String name = contentValues.getAsString(PetEntry.COLUMN_PET_NAME);
      if (name == null)
        throw new IllegalArgumentException("Pet requires a name");
    }
    if (contentValues.containsKey(PetEntry.COLUMN_PET_GENDER)){
      int gender = contentValues.getAsInteger(PetEntry.COLUMN_PET_GENDER);
      if (gender == null || !PetEntry.isValidGender(gender))
        throw new IllegalArgumentException("Pet requires valid gender");
    }
    if (contentValues.containsKey(PetEntry.COLUMN_PET_WEIGHT)){
      int weight = contentValues.getAsInteger(PetEntry.COLUMN_PET_WEIGHT);
      if (weight != null && weight < 0)
        throw new IllegalArgumentException("Pet requires valid weight");
    }
    if (values.size() == 0) {
    return 0;
    }
    
    SQLiteDatabase database = mDbHelper.getWritableDatabase();
    return database.update(PetEntry.TABLE_NAME, values, selection, selectionArgs);
  }
}
