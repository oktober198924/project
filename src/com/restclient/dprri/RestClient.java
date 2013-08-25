package com.restclient.dprri;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;




import com.restclient.dprri.DbRest;
//The beginning of Rock n' Roll
public class RestClient extends Activity {
	protected static final String DbHelper = null;
	DbRest dbHelper;
	String alamat;
	String alamat2;
	URL url;
	URL url2;
	TextView tvTampil;
	protected Cursor cursor;
    protected Cursor cursor2;
    private SQLiteDatabase db1 = null;
    protected ListView List;
	
	ArrayList<String> listItems=new ArrayList<String>();
	ArrayAdapter<String> adapter;
	protected ListAdapter adapter2;
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {	
    	
        super.onCreate(savedInstanceState);
        
        dbHelper = new DbRest(this);
        db1 = dbHelper.getWritableDatabase();
        dbHelper.createTable(db1);
        dbHelper.generateData(db1);
        dbHelper.generateData2(db1);
        dbHelper.generateData3(db1);
        
        setContentView(R.layout.main);
        this.displayBerita();
        
       
        setContentView(R.layout.main);
         
      
        final Activity activity = this;
    }
    
    //
    //DISPLAY LIST BERITA
    //
    public void displayBerita() {
    	setTitle("Berita Terkini DPR RI");

    	String kategori;
    	String judul;
    	    	
    	ListView myList = (ListView)findViewById(R.id.listView1);
    	adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
    	myList.setAdapter(adapter);
    	listItems.clear();
    		
    	try {
    		TextView textViewStat = (TextView)findViewById(R.id.textViewStat);
    		textViewStat.setText("Mengambil berita terkini...");
    		
    		alamat = "http://www.dpr.go.id/rest/berita?method=getTerasBerita&kategori=terkini&beritaperhalaman=20&halaman=0";
        	url = new URL(alamat);
        	 
        	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        	DocumentBuilder db = dbf.newDocumentBuilder();
        	Document doc = db.parse(new InputSource(url.openStream()));
        	doc.getDocumentElement().normalize();
        	NodeList nodeList = doc.getElementsByTagName("berita");
        	
        	for (int i = 0; i < nodeList.getLength(); i++) {
        		Node node = nodeList.item(i);
        		Element fstElmnt = (Element) node;
        		
        		NodeList kategoriList = fstElmnt.getElementsByTagName("kategori");
        		Element kategoriElement = (Element) kategoriList.item(0);
        		kategoriList = kategoriElement.getChildNodes();
        		kategori = ((Node) kategoriList.item(0)).getNodeValue();
        		
        		NodeList judulList = fstElmnt.getElementsByTagName("judul");
        		Element judulElement = (Element) judulList.item(0);
        		judulList = judulElement.getChildNodes();
        		judul = ((Node) judulList.item(0)).getNodeValue();
        		
        		
        		listItems.add("("+kategori+") "+judul);
				adapter.notifyDataSetChanged();
        	}
        	
        	myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {  
        		public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) {
        			detailBerita(position);  
        		}
        	}); 
        	
        	textViewStat.setText("Selesai.");
    	} 
    	catch (Exception e) {
    		TextView textViewStat = (TextView)findViewById(R.id.textViewStat);
    		textViewStat.setText("Ex: " + e.toString());
    	}
    }

	public void detailBerita(int pos) {
		setContentView(R.layout.detailberita);
		
    	String idnya = null;
		
		String judul;
    	String isi;
    	
		try {
			TextView textViewStat = (TextView)findViewById(R.id.textViewStat);
    		textViewStat.setText("Mengambil detail berita...");
			
    		alamat = "http://www.dpr.go.id/rest/berita?method=getTerasBerita&kategori=terkini&beritaperhalaman="+(pos+1)+"&halaman=0";
	    	url = new URL(alamat);

	    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder db = dbf.newDocumentBuilder();
	    	Document doc = db.parse(new InputSource(url.openStream()));
	    	doc.getDocumentElement().normalize();
	    	NodeList nodeList = doc.getElementsByTagName("berita");
	    	
	    	for (int i = 0; i < (pos+1); i++) {
	    		Node node = nodeList.item(i);
	    		Element fstElmnt = (Element) node;
	    		
	    		NodeList idList = fstElmnt.getElementsByTagName("id");
	    		Element idElement = (Element) idList.item(0);
	    		idList = idElement.getChildNodes();
	    		idnya = ((Node) idList.item(0)).getNodeValue();
	    	}
	    	
			alamat2 = "http://www.dpr.go.id/rest/berita?method=getBerita&id=" + idnya;  
	    	url2 = new URL(alamat2);
	    	
	    	DocumentBuilderFactory dbf2 = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder db2 = dbf2.newDocumentBuilder();
	    	Document doc2 = db2.parse(new InputSource(url2.openStream()));
	    	doc2.getDocumentElement().normalize();
	    	NodeList nodeList2 = doc2.getElementsByTagName("berita");
	    	
        	Node node2 = nodeList2.item(0);
    		Element fstElmnt2 = (Element) node2;
    		
    		NodeList judulList = fstElmnt2.getElementsByTagName("judul");
    		Element judulElement = (Element) judulList.item(0);
    		judulList = judulElement.getChildNodes();
    		judul = ((Node) judulList.item(0)).getNodeValue();
    		
    		NodeList isiList = fstElmnt2.getElementsByTagName("isi");
    		Element isiElement = (Element) isiList.item(0);
    		isiList = isiElement.getChildNodes();
    		isi = ((Node) isiList.item(0)).getNodeValue();
    		
    		WebView webViewDetBer = (WebView)findViewById(R.id.webViewDetBer);
    		webViewDetBer.loadData("<b>" + judul + "</b><br/>" + isi, "text/html", "utf-8");
    		
    		textViewStat.setText("Selesai.");
		}
		catch (Exception e) {
			TextView textViewStat = (TextView)findViewById(R.id.textViewStat);
    		textViewStat.setText("Ex: " + e.toString());
		}
    }

	//
	//DISPLAY LIST AGENDA
	//
	public void displayAgenda() {
		setTitle("Agenda Hari Ini DPR RI");
		
		final String hari2;
		final String bulan2;
		final String tahun2;
		
		String waktu;
		String judul;
		
    	Date tanggal = new Date();
    	int hari = tanggal.getDate();
	    	if (hari < 10) { hari2 = "0" + Integer.toString(hari); }
	    	else { hari2 = Integer.toString(hari); }
    	int bulan = tanggal.getMonth();
    		bulan = bulan+1;
	    	if (bulan < 10) { bulan2 = "0" + Integer.toString(bulan); }
	    	else { bulan2 = Integer.toString(bulan); }
    	int tahun = tanggal.getYear();
    		tahun2 = Integer.toString(tahun+1900);

    	ListView myList = (ListView)findViewById(R.id.listView1);
    	adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
    	myList.setAdapter(adapter);
    	listItems.clear();
    	 
    	try {
    		TextView textViewStat = (TextView)findViewById(R.id.textViewStat);
    		textViewStat.setText("Mengambil agenda hari ini...");
    		
    		alamat = "http://www.dpr.go.id/rest/agenda?method=getAgendaPerHari&hari=" + hari2 + "&bulan=" + bulan2 +"&tahun=" + tahun2;  
			url = new URL(alamat);
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        	DocumentBuilder db = dbf.newDocumentBuilder();
        	Document doc = db.parse(new InputSource(url.openStream()));
        	doc.getDocumentElement().normalize();
        	NodeList nodeList = doc.getElementsByTagName("agenda");
    		
        	for (int i = 0; i < nodeList.getLength(); i++) {
        		Node node = nodeList.item(i);
        		Element fstElmnt = (Element) node;
        		
        		NodeList waktuList = fstElmnt.getElementsByTagName("waktu");
        		Element waktuElement = (Element) waktuList.item(0);
        		waktuList = waktuElement.getChildNodes();
        		waktu = ((Node) waktuList.item(0)).getNodeValue();
        		
        		NodeList judulList = fstElmnt.getElementsByTagName("judul");
        		Element judulElement = (Element) judulList.item(0);
        		judulList = judulElement.getChildNodes();
        		judul = ((Node) judulList.item(0)).getNodeValue();
        		
        		listItems.add("("+waktu+") "+judul);
				adapter.notifyDataSetChanged();
        	}
        	
        	myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {  
        		public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) {
        			
        			detailAgenda(hari2, bulan2, tahun2, position);  
        		}
        	}); 
        	
        	textViewStat.setText("Selesai.");
		}
    	catch (Exception e) {
    		TextView textViewStat = (TextView)findViewById(R.id.textViewStat);
    		textViewStat.setText("Ex: " + e.toString());
		}    	
    }
	
	public void detailAgenda(String hari, String bulan, String tahun, int pos) {
		setContentView(R.layout.detailagenda);
		
		String idnya = null;
		
		String waktu;
		String judul;
		String deskripsi;
		String pengisi;
		
		try {
			TextView textViewStat = (TextView)findViewById(R.id.textViewStat);
    		textViewStat.setText("Mengambil detail agenda...");
			
			alamat = "http://www.dpr.go.id/rest/agenda?method=getAgendaPerHari&hari=" + hari + "&bulan=" + bulan +"&tahun=" + tahun;  
	    	url = new URL(alamat);
	    	
	    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder db = dbf.newDocumentBuilder();
	    	Document doc = db.parse(new InputSource(url.openStream()));
	    	doc.getDocumentElement().normalize();
	    	NodeList nodeList = doc.getElementsByTagName("agenda");
	    	
	    	for (int i = 0; i < (pos+1); i++) {
	    		Node node = nodeList.item(i);
	    		Element fstElmnt = (Element) node;
	    		
	    		NodeList idList = fstElmnt.getElementsByTagName("id");
	    		Element idElement = (Element) idList.item(0);
	    		idList = idElement.getChildNodes();
	    		idnya = ((Node) idList.item(0)).getNodeValue();
	    	}
	    	
	    	alamat2 = "http://www.dpr.go.id/rest/agenda?method=getAgenda&id=" + idnya;  
	    	url2 = new URL(alamat2);
	    	
	    	DocumentBuilderFactory dbf2 = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder db2 = dbf2.newDocumentBuilder();
	    	Document doc2 = db2.parse(new InputSource(url2.openStream()));
	    	doc2.getDocumentElement().normalize();
	    	NodeList nodeList2 = doc2.getElementsByTagName("agenda");
	    	
        	Node node2 = nodeList2.item(0);
    		Element fstElmnt2 = (Element) node2;
    		
    		NodeList waktuList = fstElmnt2.getElementsByTagName("waktu");
    		Element waktuElement = (Element) waktuList.item(0);
    		waktuList = waktuElement.getChildNodes();
    		waktu = ((Node) waktuList.item(0)).getNodeValue();
    		
    		NodeList judulList = fstElmnt2.getElementsByTagName("judul");
    		Element judulElement = (Element) judulList.item(0);
    		judulList = judulElement.getChildNodes();
    		judul = ((Node) judulList.item(0)).getNodeValue();
    		
    		NodeList deskripsiList = fstElmnt2.getElementsByTagName("deskripsi");
    		Element deskripsiElement = (Element) deskripsiList.item(0);
    		deskripsiList = deskripsiElement.getChildNodes();
    		deskripsi = ((Node) deskripsiList.item(0)).getNodeValue();
    		
    		NodeList pengisiList = fstElmnt2.getElementsByTagName("pengisi");
    		Element pengisiElement = (Element) pengisiList.item(0);
    		pengisiList = pengisiElement.getChildNodes();
    		pengisi = ((Node) pengisiList.item(0)).getNodeValue();
    		
    		WebView webViewDetBer = (WebView)findViewById(R.id.webViewDetAg);
    		webViewDetBer.loadData(
    			"Jam: " + waktu + "<br/><br/>" +
    			"<b>" + judul + "</b><br/><br/>" +
    			deskripsi + "<br/><br/>Oleh: " + pengisi
    			, "text/html", "utf-8"
    		);
    		
    		textViewStat.setText("Selesai.");
		}
		catch(Exception e) {
			TextView textViewStat = (TextView)findViewById(R.id.textViewStat);
    		textViewStat.setText("Ex: " + e.toString());
		}
		
	}

	public void displayAnggota() {
		 
		 setTitle("Anggota DPR RI");
	    	
	    	ListView myList = (ListView)findViewById(R.id.listView1);
	    	adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
	    	myList.setAdapter(adapter);
	    	listItems.clear();
	    	try{
	    		SQLiteDatabase db2 = dbHelper.getReadableDatabase();
	    		
	    		cursor = db2.rawQuery("SELECT * FROM anggota",null);
	    		
	    		for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
	    		    // The Cursor is now set to the right position
	    		    listItems.add("("+cursor.getString(1)+") "+cursor.getString(2));
	    		   
	    		    
	    		}
	    		
	    		myList.setOnItemClickListener(new AdapterView.OnItemClickListener() { 
	    			
	        		public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) {
	        			detailAnggota(position);  
	        		}
	        	}); 
	        			
	    		
	    		
			
	    		
	    		}
	    		catch(Exception e)
	            {
	    			TextView textViewStat = (TextView)findViewById(R.id.textViewStat);
	        		textViewStat.setText("Ex: " + e.toString());
	            }

	                    
	 }

	public void detailAnggota(int pos) {
		 	
			setContentView(R.layout.detailanggota);
			
			
	    	
			try {
				TextView textViewStat = (TextView)findViewById(R.id.textViewStat);
				TextView isidata = (TextView)findViewById(R.id.tvTampil);
	    		textViewStat.setText("Mengambil detail berita...");
	    		
	    		SQLiteDatabase db2 = dbHelper.getReadableDatabase();
	 
	    		
	    		cursor = db2.rawQuery("SELECT * FROM anggota join anggota2 on (anggota.id=anggota2.id) join anggota3 on(anggota.id=anggota3.id) where no = "+(pos+1)+"",null);
	    		
	        	
	        		
				
	    		for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
	    		    // The Cursor is now set to the right position
	    			isidata.setText(cursor.getString(4)+"\n"+"\n"+
	    							"Id			: " +cursor.getString(1)+"\n"+
	    							"Nama	: " +cursor.getString(2)+"\n"+
	    							"Dapil	: " +cursor.getString(5)+"\n"+
	    							"Komisi	: " +cursor.getString(6)+"\n"+
	    							"Fraksi	: " +cursor.getString(3));
	    			}

	    		
	    		textViewStat.setText("Selesai.");
			}
			catch (Exception e) {
				TextView textViewStat = (TextView)findViewById(R.id.textViewStat);
	    		textViewStat.setText("Ex: " + e.toString());
			}
	    }

	public void update() {
	    	setTitle("Update");
	    	dbHelper.upgradeData(db1);
	    	
	    	
	    	ListView myList = (ListView)findViewById(R.id.listView1);
	    	adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
	    	myList.setAdapter(adapter);
	    	listItems.clear();
	    	try{
	    		SQLiteDatabase db2 = dbHelper.getReadableDatabase();
	    		
	    		cursor = db2.rawQuery("SELECT * FROM anggota",null);
	    		for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
	    		    // The Cursor is now set to the right position
	    		    listItems.add("("+cursor.getString(1)+") "+cursor.getString(2));
	    		    
	    		}
	    		
	    		myList.setOnItemClickListener(new AdapterView.OnItemClickListener() { 
	    			
	        		public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) {
	        			detailAnggota(position);  
	        		}
	        	}); 
   		
	    		}
	    		catch(Exception e)
	            {
	    			TextView textViewStat = (TextView)findViewById(R.id.textViewStat);
	        		textViewStat.setText("Ex: " + e.toString());
	            }

	    }

	public void credits() {
	    	setTitle("Update");

			try {
				TextView textViewStat = (TextView)findViewById(R.id.textViewStat);
				TextView isidata = (TextView)findViewById(R.id.tvTampil);
	    		
	    			isidata.setText("DPR REST CLIENT \n \n\n -CREDITS- \n \n Dedicated For : DPR RI \n \n Created By : KORI22 \n\n Design Layout By : Mauriq \n \n Support By : Fariza \n\n Thanks to : \n				POLITEKNIK TELKOM\n				POLITEKNIK UNJ\n				SETJEN DPR RI ");
	    		
	    		textViewStat.setText("Selesai.");
			}
			catch (Exception e) {
				TextView textViewStat = (TextView)findViewById(R.id.textViewStat);
	    		textViewStat.setText("Ex: " + e.toString());
			}
	    	
	    	
	    	
	    }
	
	//tombol Utama
	public void tombolBerita(View v) {
    	setContentView(R.layout.main);
    	this.displayBerita();
    }
    public void tombolAgenda(View v) {
    	this.displayAgenda();
    }
    public void tombolAnggota(View v) {
    	setContentView(R.layout.mainanggota);
    	this.displayAnggota();
		   	 
    }
    
    //tombol pelengkap
    public void tombolUpdate(View v) {
    	setContentView(R.layout.mainanggota);
    	this.update();
    }
    public void tombolKembaliAnggota(View v) {
    	setContentView(R.layout.mainanggota);
    	this.displayAnggota();
    }
    public void tombolback(View v) {
    	setContentView(R.layout.main);
    	this.displayBerita();
    }
    public void tombolKembaliBerita(View v) {
    	setContentView(R.layout.main);
    	this.displayBerita();
    }
    public void tombolKembaliAgenda(View v) {
    	setContentView(R.layout.main);
    	this.displayAgenda();
    }
    public void tombolCredits(View v) {
    	setContentView(R.layout.maincredits);
    	this.credits();
    }
   
        
 }
    

