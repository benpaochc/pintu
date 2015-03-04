package com.example.pintu.view;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.R.color;
import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.pintu.R;
import com.example.pintu.util.ImagePiece;
import com.example.pintu.util.ImageSplitter;

public class GamePintuLayout extends RelativeLayout implements OnClickListener {

	private int mColumn = 3;
	
	private int mPadding;
	
	private int mMargin = 3;
	
	private ImageView[] mGamgePintuItems;
	
	private int mItemWidth;
	
	private int mWidth;
	
	private Bitmap mBitmap;
	
	private List<ImagePiece> mItemBitmaps;
	
	private boolean once;
	
	public GamePintuLayout(Context context) {
		this(context,null);
		// TODO Auto-generated constructor stub
	}
	
	public GamePintuLayout(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		// TODO Auto-generated constructor stub
	}
	
	public GamePintuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		
		init();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		mWidth = Math.min(getMeasuredHeight(), getMeasuredWidth());
	
		if (!once) {
			initBitmap();
			
			initItem();
			
			once = true;
		}
		
		setMeasuredDimension(mWidth, mWidth);
	}
	
	private void initBitmap() {
		// TODO Auto-generated method stub
		if (mBitmap == null) {
			mBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.a);
		}
		
	mItemBitmaps = ImageSplitter.splitImage(mBitmap, mColumn);
	
	Collections.sort(mItemBitmaps, new Comparator<ImagePiece>() {

		@Override
		public int compare(ImagePiece lhs, ImagePiece rhs) {
			// TODO Auto-generated method stub
			return Math.random() > 0.5? 1:-1;
		}
	});
	}

	private void initItem() {
		// TODO Auto-generated method stub
		mItemWidth = (mWidth - mPadding*2 - mMargin*(mColumn-1))/mColumn;
		
		mGamgePintuItems = new ImageView[mColumn *mColumn];
		
		for (int i = 0; i < mGamgePintuItems.length; i++) {
			ImageView item = new ImageView(getContext());
			item.setOnClickListener(this);
			
			item.setImageBitmap(mItemBitmaps.get(i).getBitmap());
			
			mGamgePintuItems[i] = item;
			
			item.setId(i+1);
			item.setTag(i+ "_" + mItemBitmaps.get(i).getIndex());
	
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(mItemWidth, mItemWidth);
			
			if ((i+1)%mColumn != 0) {
				lp.rightMargin = mMargin;
			}
			
			if (i%mColumn != 0) {
				lp.addRule(RelativeLayout.RIGHT_OF, mGamgePintuItems[i-1].getId());
			}
			
			if ((i+1)>mColumn) {
				lp.topMargin = mMargin;
				lp.addRule(RelativeLayout.BELOW, mGamgePintuItems[i-mColumn].getId());
			}
			
			addView(item, lp);
		}
	}
	private void init() {
		// TODO Auto-generated method stub
		mMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, 
				getResources().getDisplayMetrics());
		
		mPadding = min(getPaddingLeft(),getPaddingRight(),getPaddingTop(),getPaddingBottom());
	}

	private int min(int ...params) {
		// TODO Auto-generated method stub
		
		int min = params[0];
		for(int param:params){
			if (param < min) {
				min = param;
			}
		}
		return min;
	}

	
	private ImageView mFirstView;
	private ImageView mSecondView;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if (isAnim) {
			return;
		}
		
		if (mFirstView == v) {
			mFirstView.setColorFilter(null);
			mFirstView = null;
			return;
					
		}
		
		if (mFirstView == null) {
			mFirstView = (ImageView) v ;
			mFirstView.setColorFilter(Color.parseColor("#55000000"));
		}else {
			mSecondView = (ImageView) v;
			
			exChangeView();
		}
	}

	private RelativeLayout mAnimLayout;
	
	boolean isAnim = false;
	
	private void exChangeView() {
		// TODO Auto-generated method stub
		
		
		final String fisrtTag = (String) mFirstView.getTag();
		final String secondTag = (String) mSecondView.getTag();
		
		mFirstView.setColorFilter(null);
		
		setUpmAnimLayout();
		
		ImageView first = new ImageView(getContext());
		first.setImageBitmap(mItemBitmaps.get(getImageIdByTag(fisrtTag)).getBitmap());
		LayoutParams lp = new LayoutParams(mItemWidth, mItemWidth);
		lp.leftMargin = mFirstView.getLeft() - mPadding;
		lp.topMargin = mFirstView.getTop() - mPadding;
		first.setLayoutParams(lp);
		mAnimLayout.addView(first);
		
		
		ImageView second = new ImageView(getContext());
		second.setImageBitmap(mItemBitmaps.get(getImageIdByTag(secondTag)).getBitmap());
		LayoutParams lp2 = new LayoutParams(mItemWidth, mItemWidth);
		lp2.leftMargin = mSecondView.getLeft() - mPadding;
		lp2.topMargin = mSecondView.getTop() - mPadding;
		second.setLayoutParams(lp2);
		mAnimLayout.addView(second);
		
		TranslateAnimation animationFirst = new TranslateAnimation(0, mSecondView.getLeft() - mFirstView.getLeft(), 
				0, mSecondView.getTop() - mFirstView.getTop());
		animationFirst.setDuration(300);
		animationFirst.setFillAfter(true);
		first.startAnimation(animationFirst);
		
		TranslateAnimation animationSecond = new TranslateAnimation(0, -mSecondView.getLeft() +mFirstView.getLeft(), 
				0,- mSecondView.getTop() + mFirstView.getTop());
		animationSecond.setDuration(300);
		animationSecond.setFillAfter(true);
		second.startAnimation(animationSecond);
		
		animationFirst.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				mFirstView.setVisibility(View.INVISIBLE);
				mSecondView.setVisibility(View.INVISIBLE);
				
				isAnim = true;
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				mFirstView.setImageBitmap(mItemBitmaps.get(getImageIdByTag(fisrtTag)).getBitmap());
				mSecondView.setImageBitmap(mItemBitmaps.get(getImageIdByTag(secondTag)).getBitmap());
				
				mFirstView.setTag(secondTag);
				mSecondView.setTag(fisrtTag);
				
				mFirstView.setVisibility(View.VISIBLE);
				mSecondView.setVisibility(View.VISIBLE);
				
				mAnimLayout.removeAllViews();
				
				mFirstView = mSecondView = null;
				
				isAnim = false;
			}
		});
		
		
//		String[] firstParams = fisrtTag.split("_");
//		String[] secondParams = secondTag.split("_");
		
		
	}

	private int getImageIdByTag(String tag){
		
		String[] Params = tag.split("_");
		return Integer.parseInt(Params[0]);
		
	}
	
	private int getImageIdByIndex(String tag){
		
		String[] Params = tag.split("_");
		return Integer.parseInt(Params[1]);
		
	}
	
	
	
	/**
	 * 建立动画层
	 */
	private void setUpmAnimLayout() {
		// TODO Auto-generated method stub
		if (mAnimLayout == null) {
			 mAnimLayout = new RelativeLayout(getContext());
			 addView(mAnimLayout);
		}
		
		
	}

	

	

}
