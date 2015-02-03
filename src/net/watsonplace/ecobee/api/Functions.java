package net.watsonplace.ecobee.api;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Functions {
	
	enum FunctionType { acknowledge, controlPlug, createVacation, deleteVacation,
		resetPreferences, resumeProgram, sendMessage, setHold, setOccupied }
	
	private class Function {
		@SuppressWarnings("unused")
		FunctionType type;
		@SuppressWarnings("unused")
		Map<String, String> params;
		
		private Function(FunctionType type, Map<String, String> params) {
			this.type = type;
			this.params = params;
		}
		
		private String toJson() {
			Gson gson = new GsonBuilder().create();
			return gson.toJson(this);
		}
	}
	
	private Selection selection;
	private Set<Function> functions = new HashSet<>();
	
	public Functions(Selection selection) {
		this.selection = selection;
	}
	
	public void add(FunctionType type, Map<String, String> params) {
		Function f = new Function(type, params);
		functions.add(f);
	}
	
	public String toJson() {
		int count = 0;
		
		StringBuilder sb = new StringBuilder("{ ");
		
		// Function objects
		sb.append("\"functions\": [ ");
		for (Function f : functions) {
			sb.append(f.toJson());
			if (++count < functions.size()) {
				sb.append(", ");
			}
		}
		sb.append(" ], ");
		
		// Select object
		sb.append(selection.toJson());
		
		sb.append(" }");
		return sb.toString();
	}
	
//	public static void main(String[] args) {
//		Functions functions = new Functions(new Selection(Selection.SelType.registered, ""));
//		Map<String, String> params = new HashMap<>();
//		params.put("holdHours", "4");
//		params.put("coolHoldTemp", "770");
//		params.put("heatHoldTemp", "680");
//		functions.add(Functions.FunctionType.SetHold, params);
//		System.out.println(functions.toJson());
//	}
	
}
