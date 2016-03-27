package com.example.flame.flame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends ActionBarActivity {
    private float tableWidth,tableHeight;
    private float preX,preY;
    //游戏是否结束
    private boolean isLose = false;

    Rope initRope = new Rope();
    //出现在屏幕上的绳子
    List<Rope> ropes = new ArrayList<Rope>();

    Flame flame ;
    int ropeMaxNumber;
    //瓦斯记录
    Gas gas = new Gas();
    //高度记录
    Altitude altitude =new Altitude();

    Matrix matrix = new Matrix();
    float degress = 0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉窗口标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //创建GameView组件
        //获取窗口管理器
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        //获得屏幕宽和高

        tableWidth = metrics.widthPixels;
        tableHeight = metrics.heightPixels;
        initRope.setPostionTop(tableWidth / 2, tableHeight * 3 / 4);

        ropes.add(initRope);
        ropeMaxNumber = (int) tableWidth / (int) Flame.BASEDFLAMEHIGHT;
        flame = new Flame();
        flame.setRope(initRope);
        flame.setAltitude(altitude);
        initRope.setHeight(flame.getFlameHeight());
        
        StatusView statusView = new StatusView(this);
        statusView.setMinimumWidth(480);
        statusView.setMinimumHeight(50);
        setContentView(statusView);
        final GameView gameView = new GameView(this);

        setContentView(gameView);

        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 0x123) {
                    gameView.invalidate();
                }
            }
        };

        gameView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v,MotionEvent event) {
                float x = event.getX();
                float y = event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        preX = x;
                        preY = y;
                       flame.onPress();
                        break;
                    case MotionEvent.ACTION_MOVE:
                         float changeX = x - preX;
                        if(flame.getPositionTopX() + changeX < flame.getPositionBottomX() + flame.getFlameHeight() && flame.getPositionTopX() + changeX > flame.getPositionBottomX() - flame.getFlameHeight()) {
                            flame.setPositionTop(flame.getPositionTopX() + changeX,(float)(flame.getPositionBottomY() + Math.sqrt(flame.getFlameHeight() * flame.getFlameHeight() - (flame.getPositionTopX() - flame.getPositionBottomX()) * (flame.getPositionTopX() - flame.getPositionBottomX()))));
                        }
                        else {
                            if(changeX > 0) {
                                flame.setPositionTop(flame.getPositionBottomX() + flame.getFlameHeight(), flame.getPositionBottomY());
                            }
                            else flame.setPositionTop(flame.getPositionBottomX() - flame.getFlameHeight(), flame.getPositionBottomY());
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        degress = 0;
                        flame.onUnPress();
                        break;

                }
                gameView.invalidate();
                return true;
            }
        });

        final Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            public void run() {
                for(int i = 0; i < ropes.size(); i++) {
                    Rope rope = ropes.get(i);
                    rope.setPostionTop(rope.getPositionTopX(), rope.getPositionTopY() + flame.getSpeed());
                    if(rope.getPositionTopY() > tableHeight) {
                        ropes.remove(i);
                    }
                }

                //发送消息，通知系统重绘组件
                handler.sendEmptyMessage(0x123);
            }
        }, 0, 100);






    }

    class GameView extends View {
        Paint paint = new Paint();

        public GameView(Context context) {
            super(context);
            setFocusable(true);



        }

        //重写View的onDraw方法，实现绘画
        public void onDraw(Canvas canvas) {


            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.RED);
            canvas.drawRect(0, 0, gas.getCurrentGas(), 30, paint);
           // canvas.drawText("高度："+altitude.getAltitude(), 0, 10,100,30,paint);


            while(ropes.size() <= ropeMaxNumber) {
               Rope rope = new Rope();
                rope.setHeight(flame.getFlameHeight());
                rope.setPostionTop(new Random().nextFloat() * tableWidth,0 - new Random().nextFloat() * tableHeight);

                ropes.add(rope);
            }

            paint.setStyle(Paint.Style.STROKE);
            //设置去锯齿
            paint.setAntiAlias(true);
            //如果游戏已经结束
            if (isLose) {
                paint.setColor(Color.RED);
                paint.setTextSize(40);
                canvas.drawText("游戏已结束", 50, 200, paint);

            }
            //如果游戏还未结束
            else {
                //
                Bitmap cacheBitmap = Bitmap.createBitmap((int)tableWidth, (int)tableHeight, Bitmap.Config.ARGB_8888);
                Canvas cacheCanvas = new Canvas();
                cacheCanvas.setBitmap(cacheBitmap);

                paint.setColor(Color.BLACK);

                for(int i = 0; i < ropes.size(); i++) {
                    Rope rope = ropes.get(i);
                    System.out.println("Flame.flamehight"+flame.getFlameHeight()+"rope.length:"+ rope.getLength());
                    paint.setStrokeWidth(rope.getSize());
                    canvas.drawCircle(rope.getPositionTopX(), rope.getPositionTopY(), 2f, paint);
                    canvas.drawCircle(rope.getPositionTopX(), rope.getPositionTopY() + rope.getLength(), 2f,paint);
                    canvas.drawLine(rope.getPositionTopX(),rope.getPositionTopY(),rope.getPositionTopX(),rope.getPositionTopY() + rope.getLength(), paint);
                }

                paint.setColor(Color.rgb(80, 80, 200));
                paint.setStrokeWidth(1);
                cacheCanvas.drawRect(flame.getPositionTopX(), flame.getPositionTopY(), flame.getPositionBottomX(), flame.getPositionBottomY(), paint);
                canvas.drawRect(flame.getPositionTopX(), flame.getPositionTopY(), flame.getPositionBottomX(), flame.getPositionBottomY(), paint);

            }
        }
    }


    class StatusView extends View {
        Paint paint;
        StatusView(Context context) {
            super(context);

        }

        public void onDraw(Canvas canvas){
            paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.RED);
            canvas.drawRect(0, 0, gas.getCurrentGas(), 30, paint);
            canvas.drawText("高度："+altitude.getAltitude(),200, 30,200,30,paint);
        }
    }
}
