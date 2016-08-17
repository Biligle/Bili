package com.shanshan.housekeeper.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.shanshan.housekeeper.R;
import com.wgl.mvp.headerPicture.HeaderPicture;

import java.io.BufferedOutputStream;
import java.util.ArrayList;

/**
 * Created by wgl.
 */
public class GrideViewAdapter extends BaseAdapter {
    private ArrayList<String> data;
    private LayoutInflater inflater;

    public GrideViewAdapter(Context context,ArrayList<String> data){
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler = null;
        if(null == convertView){
            convertView = inflater.inflate(R.layout.grideview_item,parent,false);
            viewHodler = new ViewHodler();
            viewHodler.imageView = (ImageView) convertView.findViewById(R.id.iv1);
            convertView.setTag(viewHodler);
        }else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        if(null != data && data.size() > 0){
            try {
                Bitmap bitmap = HeaderPicture.compress(data.get(position),200.00);
                viewHodler.imageView.setImageBitmap(bitmap);
                if(!bitmap.isRecycled()){
                    bitmap.isRecycled();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }
    public class ViewHodler{
        private ImageView imageView;
    }
    }

