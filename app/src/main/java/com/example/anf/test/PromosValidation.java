package com.example.anf.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.example.anf.PromosAct;
import com.example.anf.R;
import com.example.anf.adapter.PromoAdapter;
import com.example.anf.entity.PromoData;
import com.example.anf.util.GsonUtil;

@SuppressWarnings("rawtypes")
public class PromosValidation extends ActivityInstrumentationTestCase2 {

	private String userName;
	PromosAct promosAct;
	GridView promosGV;
	View child0;
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public PromosValidation(){
		super("com.example.anf", PromosAct.class);
	}
	
   protected void setUp() throws Exception {
       super.setUp();
       
       promosAct = (PromosAct) getActivity();
       userName = (String) promosAct.getTitle();

       promosGV = (GridView) promosAct.findViewById(R.id.promosGV);
       promosGV.getChildAt(0);
   }
   
   public void testPromoSelect() throws Exception {
       assertNotNull(promosAct);
       userName = trimUsername(userName);
       assertNotSame("", userName);
       
       PromoAdapter promoAdapter = (PromoAdapter) promosGV.getAdapter();
       if(promoAdapter.getCount() < 1){
    	   RelativeLayout mainLayout = (RelativeLayout) promosAct.findViewById(R.id.mainLyt);
    	   assertNotNull(mainLayout);
       } else {
    	   child0.performClick();
       }
   }
   
   public void testPromoLoadData() throws Exception {
	   //new DownloadPromosListTask(promosAct, promosAct).execute();
	   promosAct.onResume();
	   System.out.println(GsonUtil.objectToGsonString(promosAct.getPromosList()));
   }
   
   @UiThreadTest
   public void testPromoBuildRows() throws Exception {
	   String jSonStr = "{\"promotions\":[{\"button\":{\"target\":\"Pringle\",\"title\":\"Surf\"},\"description\":\"AllBrands\",\"footer\":\"Team\",\"title\":\"Cup\",\"image\":\"http://en.kosmosmalabares.com/wp-content/uploads/2012/01/Bombin_rojo_1_web.jpg\",\"id\":1},{\"button\":{\"target\":\"Stern\",\"title\":\"Runt\"},\"description\":\"Crown\",\"footer\":\"Fur\",\"title\":\"part\",\"image\":\"http://mlm-s1-p.mlstatic.com/10-sombreros-copa-bombin-para-fiesta-batucada-animacion-13381-MLM3058883047_082012-O.jpg\",\"id\":2}]}";
	   PromoData promoData = (PromoData) GsonUtil.gsonStringToObject(jSonStr, new PromoData());
	   promosAct.updatePromosList(promoData.getPromotions());
	   
	   int count = promosGV.getAdapter().getCount();
	   
	   assertNotSame(0, count);
   }
   
   String trimUsername(String userName){
	   String user = userName.substring(7).trim();
	   return user;
   }
   
   
}
