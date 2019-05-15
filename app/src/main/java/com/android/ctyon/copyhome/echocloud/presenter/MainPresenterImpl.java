package com.android.ctyon.copyhome.echocloud.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.ctyon.copyhome.MyApplication;
import com.android.ctyon.copyhome.echocloud.model.Constant;
import com.android.ctyon.copyhome.echocloud.model.GsonInner;
import com.android.ctyon.copyhome.echocloud.model.MediaPlayCallback;
import com.android.ctyon.copyhome.echocloud.model.ResultMessage;
import com.android.ctyon.copyhome.echocloud.model.Type;
import com.android.ctyon.copyhome.echocloud.model.VoiceMessage;
import com.android.ctyon.copyhome.echocloud.utils.DataUtil;
import com.android.ctyon.copyhome.echocloud.utils.HttpUtil;
import com.android.ctyon.copyhome.echocloud.utils.MediaManager;
import com.android.ctyon.copyhome.echocloud.view.MainView;

import java.util.List;
import java.util.UUID;

public class MainPresenterImpl implements MainPresenter, HttpUtil.OnPostFailedCallback, MediaPlayCallback {

    private String nonce;
    private String devicesId;
    private String signature;

    private MainView mView;

    private MediaManager recordManager;

    private int readIndex;
    private List<ResultMessage.MessagesBean> messageList;
    private int messageCount;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mView.showResultMessage(msg.obj.toString());
                    break;
                case 1:
                    mView.showSendMessage(msg.obj.toString());
                    break;
            }
        }
    };

    public MainPresenterImpl(MainView view) {
        this.mView = view;
        mView.setPresenter(this);

        nonce = UUID.randomUUID().toString().replace("-", "");
        devicesId = DataUtil.getDevicesId();
        signature = DataUtil.getSignature(Constant.CHANNEL_UUID, devicesId, nonce, Constant.CLIENT_SECRET);

        recordManager = MyApplication.getInstance().getInstance().getRecordManager();
        recordManager.setMediaPlayerCallback(this);
    }

    @Override
    public void sendNormalMessage(final Type type, final String text) {
//        mView.resetText();
        new Thread() {
            @Override
            public void run() {
                VoiceMessage.Data data = new VoiceMessage.Data();
                switch (type) {
                    case HEART:
                        break;
                    case AI_TEXT:
                        Log.d("Lee", "text = " + text);
                        data.setText(text);
                        break;
                }
                String result = HttpUtil.postNormal(getMessage(type, data));
                if (null != result && !result.isEmpty()) {
//                    Message message = handler.obtainMessage(0);
//                    message.obj = result;
//                    handler.sendMessage(message);

                    doResult(result);
                }
            }
        }.start();
    }

    @Override
    public void sendStreamMessage(final Type type, String text) {
//        mView.resetText();
        new Thread() {
            @Override
            public void run() {
                byte[] dataByte = new byte[0];
                switch (type) {
                    case AI_VOICE:
                        dataByte = recordManager.getPcmFile();
                        break;
                }
                String result = HttpUtil.postStream(nonce, signature, dataByte, type);
                Log.e("Lee_log", "result : " + result);
                if (null != result && !result.isEmpty() && !"-1".equals(result)) {
//                    Message message = handler.obtainMessage(0);
//                    message.obj = result;
//                    handler.sendMessage(message);

                    doResult(result);
                }
            }
        }.start();
    }

    private void doResult(String result) {
        try {
            readIndex = 0;
            ResultMessage resultMessage = GsonInner.getInstance().fromJson(result, ResultMessage.class);
            messageCount = resultMessage.getCount();
            messageList = resultMessage.getMessages();
            doPlay();
        } catch (Exception e) {
            Log.e("Lee_log", "doResult ERROR : " + e);
        }
    }

    private void doPlay() {
        try {
            Log.d("Lee_log", "doPlay : " + readIndex);
            if (readIndex == messageCount) return;
            ResultMessage.MessagesBean.DataBean data = messageList.get(readIndex).getData();
            String url = data.getAudio_url();
            if (null != url && !url.isEmpty()) recordManager.playMusic(url);
            readIndex++;
        } catch (NullPointerException e) {
            Log.e("Lee_log", "doPlay ERROR : " + e);
        }

    }

    private String getMessage(Type type, VoiceMessage.Data messageData) {
        VoiceMessage voiceMessage = new VoiceMessage();
        VoiceMessage.Meta meta = new VoiceMessage.Meta(Constant.CHANNEL_UUID, devicesId);
        VoiceMessage.Message message = new VoiceMessage.Message(type.toString());

        message.setData(messageData);

        voiceMessage.setMeta(meta);
        voiceMessage.setMessage(message);
        voiceMessage.setNonce(nonce);
        voiceMessage.setSignature(signature);

        String sendData = GsonInner.getInstance().toJson(voiceMessage);

        Log.e("Lee", sendData);

        Message msg = handler.obtainMessage(1);
        msg.obj = sendData;
        handler.sendMessage(msg);

        return sendData;
    }

    @Override
    public void startRecord() {
        recordManager.startRecord();
//        mView.showSendMessage("正在录音.....");
    }

    @Override
    public void stopRecord() {
        recordManager.stopRecord();
    }

    @Override
    public void playVoice() {
//        recordManager.playInModeStream();
    }

    @Override
    public void stopVoice() {
        recordManager.stopPlayMusic();
    }


    @Override
    public void onError(String erroeMsg) {

    }

    @Override
    public void onPlayOver() {
        doPlay();
    }
}
