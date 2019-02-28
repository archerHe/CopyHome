package com.android.ctyon.copyhome.utils;


import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;


import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

/**
 * Created by Administrator on 2017/11/28.
 */

public class MediaConnectScanner implements MediaScannerConnection.MediaScannerConnectionClient{

    private Context context;
    private List<String> fileList = new ArrayList<>();
    private MediaScannerConnection mcScanner;
    ScannerListener listener;
    private String lastPath;
    public interface ScannerListener{
        void onScanFinish();
    }
    public MediaConnectScanner(Context context, List<String> fileList,ScannerListener listener){
        this.context = context;
        this.fileList = fileList;
        mcScanner = new MediaScannerConnection(context,this);
        mcScanner.connect();
        this.listener = listener;
        lastPath = this.fileList.get(this.fileList.size()-1);
    }
    @Override
    public void onMediaScannerConnected() {
        try {
            for(String filePath: fileList){
                mcScanner.scanFile(filePath,null);
            }
        }catch (ConcurrentModificationException ex){
            ex.printStackTrace();
            return;
        }
    }

    @Override
    public void onScanCompleted(String path, Uri uri) {
        if(lastPath.equals(path)){
            mcScanner.disconnect();
            mcScanner = null;
            listener.onScanFinish();
        }
    }
    public void closeConnect(){
        if(mcScanner != null)
            mcScanner.disconnect();
    }

}
