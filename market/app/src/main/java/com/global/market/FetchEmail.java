package com.global.market;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;

import java.util.List;
import java.util.StringTokenizer;

/**
 * This class uses the AccountManager to get the primary email address of the
 * current user.
 */
public class FetchEmail {

	static String getEmail(Context context) {
	    AccountManager accountManager = AccountManager.get(context);
	    String account = getEmailId(accountManager);
	    if (account == null) {
	      return null;
	    } else {
	      return account;
	    }
	}
	
	public static String getEmailId(AccountManager accountManager){
		String myEmailid=null;
		Account[] accounts = accountManager.getAccountsByType("com.google");
		try{			
			for(int i = 0;i<accounts.length;i++)
			{
				String emailId=accounts[i].toString();
			}
			myEmailid=accounts[0].name;
			
		}
		catch(ArrayIndexOutOfBoundsException ex)
		{
			//Log.d("No Email Id not Found in Device", myEmailid);
		}
		return myEmailid;
		
	}
	
	public	static String getAppVersion(Context context)
	{
		String ver = "";
		try {
			PackageInfo packinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			ver = packinfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ver;
	}
	
	public static int getAppCode(Context context)
	{
		int ver = 0;
		try {
			PackageInfo packinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			ver = packinfo.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ver;
	}
	
	public static boolean readFile(String weburls, String url)
	{
		StringTokenizer strTkn = new StringTokenizer(weburls, "^");
		boolean flag = false;
		while(strTkn.hasMoreTokens())
		{
			if(url.matches("(.*)"+strTkn.nextToken()+"(.*)"))
			{
				flag = true;
			}
		}
		return flag;
	}

	public static void shareToGMail(String[] email, String subject, String content, Context context) {
		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
		emailIntent.setType("text/plain");
		emailIntent.putExtra(Intent.EXTRA_TEXT, content);
		final PackageManager pm = context.getPackageManager();
		final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
		ResolveInfo best = null;
		for(final ResolveInfo info : matches)
			if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))
				best = info;
		if (best != null)
			emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
		context.startActivity(emailIntent);
	}
}