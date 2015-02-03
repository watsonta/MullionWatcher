package net.watsonplace.ecobee.api;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Page extends APIObject {
	public static String[] APIObjectIdentifier = new String[] { "page" };
	public static Type APIObjectType = new TypeToken<Page>(){}.getType();
	
	private int page;
	private int totalPages;
	private int pageSize;
	private int total;
	
	public int getPage() {
		return page;
	}
	
	public int getTotalPages() {
		return totalPages;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public int getTotal() {
		return total;
	}

	@Override
	public String[] getAPIObjectIdentifier() {
		return APIObjectIdentifier;
	}

	@Override
	public String toJson() {
		return super.toJson(APIObjectIdentifier[0]);
	}
	
}
