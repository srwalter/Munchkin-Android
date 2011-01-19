package com.slinkman.munchkin.android.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import android.os.Environment;
import android.util.Log;

public class GearWriter {

	ArrayList<String> armorList;
	ArrayList<Integer> bonusList;
	final static String startLine = "<gearlist>";
	final static String endLine = "</gearlist>";

	public GearWriter(ArrayList<String> armor, ArrayList<Integer> bonus) {
		armorList = armor;
		bonusList = bonus;
	}

	public void startWrite() {
		try {
			FileWriter writer = new FileWriter(new File(Environment
					.getExternalStorageDirectory(), "gear_list.xml"));
			writer.write("<gearlist>" + '\n');
			for (int i = 0; i < bonusList.size(); i++) {
				String totalString = "<gear type='" + armorList.get(i)
						+ "' bonus='" + bonusList.get(i) + "'/>" + '\n';
				writer.write(totalString.toCharArray());
				Log.i("Writer", "Wrote: " + totalString );
			}
			writer.write("</gearlist>");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
