package com.shanshan.housekeeper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shanshan.housekeeper.Help.utils.LogCatUtil;
import com.shanshan.housekeeper.R;
import com.wgl.mvp.headerPicture.HeaderPicture;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by wgl.
 */
public class ListViewAdapter extends BaseAdapter{

    private ArrayList<String> data;
    private Context context;
    private LayoutInflater inflater;

    public ListViewAdapter(Context context,ArrayList<String> data){
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(null == convertView){
            convertView = inflater.inflate(R.layout.listview_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_list);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.tv_list);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setImageBitmap(HeaderPicture.compress(data.get(position),200.00));
        String path = new File(data.get(position)).getParentFile().getAbsolutePath();
        viewHolder.tv.setText(path.substring(path.lastIndexOf("/")+1));
        return convertView;
    }

    public class ViewHolder{
        private ImageView imageView;
        private TextView tv;
    }
}
