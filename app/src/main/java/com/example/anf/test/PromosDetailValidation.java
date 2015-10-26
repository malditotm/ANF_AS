package com.example.anf.test;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.View;
import android.widget.Button;

import com.example.anf.PromoDetailAct;
import com.example.anf.R;
import com.example.anf.entity.Promotion;
import com.example.anf.util.Constants;

@SuppressWarnings("rawtypes")
public class PromosDetailValidation extends ActivityInstrumentationTestCase2 {

	PromoDetailAct promoDetailAct;
	Promotion promo;
	String promoLinkURL;
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public PromosDetailValidation(){
		super("com.example.anf", PromoDetailAct.class);
	}
	
   protected void setUp() throws Exception {
       super.setUp();

       //promoDetailAct = (PromoDetailAct) getActivity();
       //Intent intent = promoDetailAct.getIntent();
       Intent intent = new Intent();
       intent.setClassName("com.UI", "com.UI.PromoDetailAct");
       intent.putExtra(Constants.KEY_PROMOS_SELECTED_PROMO, "1");
       setActivityIntent(intent);
       promoDetailAct = (PromoDetailAct) getActivity();

       promo = promoDetailAct.getPromo();
       promoLinkURL = promoDetailAct.getPromoLinkURL();
   }
   
   public void testPromoLoad() throws Exception {
       assertNotNull(promoDetailAct);
       assertNotNull(promo);
       assertNotNull(promoLinkURL);
   }
   
   @UiThreadTest
   public void testPromoFollowLink() throws Exception {
	   promoDetailAct.followLinkOnClick(promoDetailAct.findViewById(R.id.promoLinkBtn));
	   
	   View webLyt = promoDetailAct.getWebLyt();
	   assertNotNull(webLyt);
   }
   
   String trimUsername(String userName){
	   String user = userName.substring(7).trim();
	   return user;
   }
}
