package com.example.listviewdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "tag";
    private ListView lv_content;
    private ArrayList<String> mList;
    private List<View> listViews;
    private CusAdapter adapter;
    private LayoutInflater inflater;
    private int[] imgs = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    private OkHttpClient client;
    private ArrayList<Bitmap> bitmapList;
    private MyPagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bitmapList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            File file = new File(Environment.getExternalStorageDirectory() + "/spic/pic" + i + ".png");

//            Log.i(TAG, "initData: " + file.getPath());

            Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/spic/pic" + i + ".png");
            bitmapList.add(bitmap);

        }

        initViews();
        initData();
    }

    private void initData() {
//        loadImage();
        inflater = getLayoutInflater();

        mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mList.add("hello : " + i);
        }




//        listViews = new ArrayList<>();
//        for (int i = 0; i < imgs.length; i++) {
//            View view = inflater.inflate(R.layout.viewpager_item, null);
//            TextView title = (TextView) view.findViewById(R.id.view_title);
//            title.setText("头像");
//            ImageView iv = (ImageView) view.findViewById(R.id.view_image);
//            iv.setBackgroundResource(imgs[i]);
//            listViews.add(view);
//        }

        adapter = new CusAdapter();
        lv_content.setAdapter(adapter);
    }

    private void loadImage() {
        //创建okHttpClient对象
        client = new OkHttpClient();

        for (int i = 0; i < GlobalUrl.ImageUrl.length; i++) {

            //创建一个Request
            final Request request = new Request.Builder()
                    .url(GlobalUrl.ImageUrl[i])
                    .build();
            //new call
            Call call = client.newCall(request);
            //请求加入调度
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                }
            });
        }
    }

    private void initViews() {
        lv_content = (ListView) findViewById(R.id.lv_content);

    }

    class CusAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
//
//        @Override
//        public int getViewTypeCount() {
//            return super.getViewTypeCount();
//        }
//
//        @Override
//        public int getItemViewType(int position) {
//
////            if (position == 2) {
////                return 1;
////            }
//
//            return super.getItemViewType(position);
//        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
//
//            int type = getItemViewType(position);
//            TextView textView = new TextView(MainActivity.this);
//            if (0 == type) {
//                textView.setText(mList.get(position));
//            } else if (1 == type) {
//                textView.setText("i am special");
//
//                View inflate = inflater.inflate(R.layout.viewpager, null);
//                ViewPager vp_view = (ViewPager) inflate.findViewById(R.id.vp_view);
//
//                vp_view.setAdapter(new ViewPagerAdapter(listViews));
//
//                return inflate;
//            }

            View inflate = inflater.inflate(R.layout.search_result_list, null);
            ViewPager viewPager = (ViewPager) inflate.findViewById(R.id.iv_recommend_detail);
//            Glide.with(MainActivity.this).load(GlobalUrl.ImageUrl[0]).into(iv_recommend);

            myPagerAdapter = new MyPagerAdapter(bitmapList);
            viewPager.setAdapter(myPagerAdapter);
            viewPager.setCurrentItem(bitmapList.size() * 10000);

            return inflate;
        }
    }


    class MyPagerAdapter extends PagerAdapter {

        private ArrayList<Bitmap> list;

        public MyPagerAdapter(ArrayList<Bitmap> bitmapList) {
            this.list = bitmapList;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView imageView = new ImageView(MainActivity.this);
            imageView.setImageBitmap(bitmapList.get(position % bitmapList.size()));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}