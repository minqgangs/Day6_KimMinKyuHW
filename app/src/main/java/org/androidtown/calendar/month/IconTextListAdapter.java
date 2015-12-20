package org.androidtown.calendar.month;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * 어댑터 클래스 정의
 * 
 * @author Mike
 *
 */
public class IconTextListAdapter extends BaseAdapter {

	private Context mContext;
	TextView textview;
	Document doc = null;
	LinearLayout layout = null;
	int day = 0;

	private List<IconTextItem> mItems = new ArrayList<IconTextItem>();

	public IconTextListAdapter(Context context) {
		mContext = context;
	}

	public void addItem(IconTextItem it) {
		mItems.add(it);
	}

	public void setListItems(List<IconTextItem> lit) {
		mItems = lit;
	}

	public int getCount() {
		return mItems.size();
	}

	public Object getItem(int position) {
		return mItems.get(position);
	}

	public boolean areAllItemsSelectable() {
		return false;
	}

	public boolean isSelectable(int position) {
		try {
			return mItems.get(position).isSelectable();
		} catch (IndexOutOfBoundsException ex) {
			return false;
		}
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		final IconTextView itemView;
		if (convertView == null) {
			itemView = new IconTextView(mContext, mItems.get(position));
		} else {
			itemView = (IconTextView) convertView;
			
			itemView.setIcon(mItems.get(position).getIcon());
			itemView.setText(0, mItems.get(position).getData(0));
			itemView.setText(1, mItems.get(position).getData(1));
		}

		Button btnDel = (Button)itemView.findViewById(R.id.btnDel);

		btnDel.setOnClickListener(new View.OnClickListener() {
				@Override
			public void onClick(View v) {

				Log.d("index","position :" + position);

				removeItem(position);
			}
		});

		return itemView;
	}



	public void removeItem(int position)
	{
		Log.d("index",Integer.toString(position));
		mItems.remove(position);
		this.notifyDataSetChanged();
	}

	public Object getItemDay(int position) {
		return mItems.get(position).getData(0);
	}
	/*
	public void onWeather2(View view){

		GetXMLTask task = new GetXMLTask();
		task.execute("http://www.kma.go.kr/wid/queryDFS.jsp?gridx=61&gridy=125");

	}

	//private inner class extending AsyncTask
	private class GetXMLTask extends AsyncTask<String, Void, Document> {

		@Override
		protected Document doInBackground(String... urls) {
			URL url;
			try {
				url = new URL(urls[0]);
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder(); //XML문서 빌더 객체를 생성
				doc = db.parse(new InputSource(url.openStream())); //XML문서를 파싱한다.
				doc.getDocumentElement().normalize();

			} catch (Exception e) {
				Toast.makeText(mContext, "Parsing Error", Toast.LENGTH_SHORT).show();
			}
			return doc;
		}

		@Override
		public void onPostExecute(Document doc) {

			String s = "";

			//data태그가 있는 노드를 찾아서 리스트 형태로 만들어서 반환
			NodeList nodeList = doc.getElementsByTagName("data");
			//data 태그를 가지는 노드를 찾음, 계층적인 노드 구조를 반환

			//for(int i = 0; i< nodeList.getLength(); i++){

			//날씨 데이터를 추출
			//s += "" +i + ": 날씨 정보: ";
			s += day + "일 " + "날씨 정보: ";
			Node node = nodeList.item(1); //data엘리먼트 노드
			Element fstElmnt = (Element) node;
			NodeList nameList  = fstElmnt.getElementsByTagName("temp");
			Element nameElement = (Element) nameList.item(0);
			nameList = nameElement.getChildNodes();
			s += "온도 = "+ ((Node) nameList.item(0)).getNodeValue() +" ,";


			NodeList websiteList = fstElmnt.getElementsByTagName("wfKor");
			//<wfKor>맑음</wfKor> =====> <wfKor> 태그의 첫번째 자식노드는 TextNode 이고 TextNode의 값은 맑음
			s += "날씨 = "+  websiteList.item(0).getChildNodes().item(0).getNodeValue() +"\n";

			// }

			// textview.setText(s);
			Toast.makeText(mContext, s, Toast.LENGTH_LONG).show();
			super.onPostExecute(doc);
		}
	}//end inner class - GetXMLTask
	*/
}
