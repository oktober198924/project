package com.restclient.dprri;

import java.net.URL;





import java.util.ArrayList;




import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.widget.ArrayAdapter;


public class DbRest extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "DbRest";
	
	public static final String NO = "no";
	public static final String ID = "id";
	public static final String NAMA = "nama";
	public static final String DAPIL = "dapil";
	public static final String KOMISI = "komisi";
	public static final String FRAKSI = "fraksi";
	public static final String FOTO = "foto";
	public static final String ANGGOTA = "anggota";
	
	
	String id;
	String nama;
	String dapil;
	String komisi;
	String fraksi;
	String foto;
	String alamat;
	String alamat2;
	URL url;
	URL url2;
	ArrayList<String> listItems=new ArrayList<String>();
	ArrayAdapter<String> adapter;
	
	
	//Constructor DataKamus untuk initiate database
	public DbRest(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}
	
	//Method createTable untuk membuat table kamus
	public void createTable(SQLiteDatabase db1){
		db1.execSQL("DROP TABLE IF EXISTS anggota");
		db1.execSQL("DROP TABLE IF EXISTS anggota2");
		db1.execSQL("DROP TABLE IF EXISTS anggota3");
		
		
		db1.execSQL("CREATE TABLE if not exists anggota (no INTEGER PRIMARY KEY AUTOINCREMENT, id VARCHAR(6), nama VARCHAR(30), fraksi VARCHAR(15), foto VARCHAR(60));");
		db1.execSQL("CREATE TABLE if not exists anggota2 (id VARCHAR(6), komisi INT(2));");
		db1.execSQL("CREATE TABLE if not exists anggota3 (id VARCHAR(6), dapil varchar(15));");
	}
	
	//Method generateData untuk mengisikan data ke kamus.
	public ArrayList<ArrayList<Object>> generateData(SQLiteDatabase db1) {
		
		String id = null;
    	String nama = null;
    	String fraksi = null;
    	String foto = null;
    	
    	
    	
    
    	
    	
    	    	
    	
    		
try {
    		
    		
    		alamat = "http://dpr.go.id/rest/anggota?method=getSemuaAnggota";  
    		
        	url = new URL(alamat);
        	 
        	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        	DocumentBuilder db = dbf.newDocumentBuilder();
        	Document doc = db.parse(new InputSource(url.openStream()));
        	doc.getDocumentElement().normalize();
        	NodeList nodeList = doc.getElementsByTagName("anggota");
        	
        	for (int i = 0; i < nodeList.getLength(); i++) {
        		Node node = nodeList.item(i);
        		Element fstElmnt = (Element) node;
        		
        		NodeList idList = fstElmnt.getElementsByTagName("id");
        		Element idElement = (Element) idList.item(0);
        		idList = idElement.getChildNodes();
        		id = ((Node) idList.item(0)).getNodeValue();
        		
        		NodeList namaList = fstElmnt.getElementsByTagName("nama");
        		Element namaElement = (Element) namaList.item(0);
        		namaList = namaElement.getChildNodes();
        		nama = ((Node) namaList.item(0)).getNodeValue();
        		
        		
        		NodeList fraksiList = fstElmnt.getElementsByTagName("fraksi");
        		Element fraksiElement = (Element) fraksiList.item(0);
        		fraksiList = fraksiElement.getChildNodes();
        		fraksi = ((Node) fraksiList.item(0)).getNodeValue();
        		
        		NodeList fotoList = fstElmnt.getElementsByTagName("foto");
        		Element fotoElement = (Element) fotoList.item(0);
        		fotoList = fotoElement.getChildNodes();
        		foto = ((Node) fotoList.item(0)).getNodeValue();
        		
        		
        		ContentValues cv = new ContentValues();
        		
        		
        		cv.put(ID, id);
        		cv.put(NAMA, nama);
        		cv.put(FRAKSI, fraksi);
        		cv.put(FOTO, foto);
        		
        		
        		db1.insert("anggota", ID, cv);
        		
        		
        		
        	}
        	
        	
        	
        	
    	}
    	catch (Exception e) {
    		
    	}
		return null;
    	
	}
public ArrayList<ArrayList<Object>> generateData2(SQLiteDatabase db1) {
		
		String id = null;
    	String komisi = null;
    	
    	
    
    	
    	
    	    	
    	
    		
try {
    		
    		
    		alamat = "http://dpr.go.id/rest/anggota?method=getSemuaAnggota";  
    		
        	url = new URL(alamat);
        	 
        	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        	DocumentBuilder db = dbf.newDocumentBuilder();
        	Document doc = db.parse(new InputSource(url.openStream()));
        	doc.getDocumentElement().normalize();
        	NodeList nodeList = doc.getElementsByTagName("anggota");
        	
        	for (int i = 0; i < nodeList.getLength(); i++) {
        		Node node = nodeList.item(i);
        		Element fstElmnt = (Element) node;
        		
        		NodeList idList = fstElmnt.getElementsByTagName("id");
        		Element idElement = (Element) idList.item(0);
        		idList = idElement.getChildNodes();
        		id = ((Node) idList.item(0)).getNodeValue();
        		
        		NodeList komisiList = fstElmnt.getElementsByTagName("komisi");
        		Element komisiElement = (Element) komisiList.item(0);
        		komisiList = komisiElement.getChildNodes();
        		komisi = ((Node) komisiList.item(0)).getNodeValue();
        		
        		
        		ContentValues cv = new ContentValues();
        		
        		
        		cv.put(ID, id);
        		cv.put(KOMISI, komisi);
        		
        		
        		
        		db1.insert("anggota2", ID, cv);
        		
        		
        		
        	}
        	
        	
        	
        	
    	}
    	catch (Exception e) {
    		
    	}
		return null;
    	
	}
public ArrayList<ArrayList<Object>> generateData3(SQLiteDatabase db1) {
	
	String id = null;
	String dapil = null;
	
		
try {
		
		
		alamat = "http://dpr.go.id/rest/anggota?method=getSemuaAnggota";  
		
    	url = new URL(alamat);
    	 
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    	DocumentBuilder db = dbf.newDocumentBuilder();
    	Document doc = db.parse(new InputSource(url.openStream()));
    	doc.getDocumentElement().normalize();
    	NodeList nodeList = doc.getElementsByTagName("anggota");
    	
    	for (int i = 0; i < nodeList.getLength(); i++) {
    		Node node = nodeList.item(i);
    		Element fstElmnt = (Element) node;
    		
    		NodeList idList = fstElmnt.getElementsByTagName("id");
    		Element idElement = (Element) idList.item(0);
    		idList = idElement.getChildNodes();
    		id = ((Node) idList.item(0)).getNodeValue();
    		
    		NodeList dapilList = fstElmnt.getElementsByTagName("dapil");
    		Element dapilElement = (Element) dapilList.item(0);
    		dapilList = dapilElement.getChildNodes();
    		dapil = ((Node) dapilList.item(0)).getNodeValue();
    		
    		
    		
    		ContentValues cv = new ContentValues();
    		
    		
    		cv.put(ID, id);
    		cv.put(DAPIL, dapil);
    		
    		
    		
    		db1.insert("anggota3", ID, cv);
    		
    		
    		
    	}
    	
    	
    	
    	
	}
	catch (Exception e) {
		
	}
	return null;
	
}



    	
	
	
		
	
	
	

	public void upgradeData(SQLiteDatabase db1) {
		db1.execSQL("DROP TABLE IF EXISTS anggota");
		db1.execSQL("DROP TABLE IF EXISTS anggota2");
		db1.execSQL("DROP TABLE IF EXISTS anggota3");
		
		
		db1.execSQL("CREATE TABLE if not exists anggota (no INTEGER PRIMARY KEY AUTOINCREMENT, id VARCHAR(6), nama VARCHAR(30), fraksi VARCHAR(15), foto VARCHAR(60));");
		db1.execSQL("CREATE TABLE if not exists anggota2 (id VARCHAR(6), komisi INT(2));");
		db1.execSQL("CREATE TABLE if not exists anggota3 (id VARCHAR(6), dapil INT(2));");
		generateData(db1);
		generateData2(db1);
		generateData3(db1);
		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db1) {
		//TODO Auto-generated method sub
		 
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	
	
}
