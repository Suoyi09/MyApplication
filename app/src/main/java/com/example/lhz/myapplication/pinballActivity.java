package com.example.lhz.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class pinballActivity extends Activity {
    //随机数。每次进入游戏初始不一样
    Random rand = new Random();
    private double xyRate = rand.nextDouble() - 0.5; //一个-0.5~0.5的比率,控制小球的方向
    //屏幕宽，高
    private int tablewidth;
    private int tableheight;
    //球拍的水平位置，垂直位置，宽度，高度
    private int racketx = rand.nextInt(200);//***每次进入游戏得到随机球拍初始位置
    private int rackety ;
    private int RACKET_WIDTH = 200;
    private int RACKET_HEIGHT = 30;
    //球的大小，x和Y坐标
    private int BALL_SIZE = 16;
    private int ballx = rand.nextInt(200) + 20;//***每次进入游戏得到随机小球初始位置
    private int bally = rand.nextInt(10) + 20;//***
    //球的纵向速度,横向速度
    private int yspeed = 40;
    private int xspped = (int) (yspeed*xyRate*2); //***每次进入游戏得到一个随机的横向速度
    //游戏是否结束的标志
    private boolean isLose = false;
    //球拍的旧xy坐标
    int oldx;
    int oldy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final GameView gameView = new GameView(this);
        setContentView(gameView);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);//因为已经没有标题了，加上该代码会出错
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);


        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        tablewidth = metrics.widthPixels;
        tableheight = metrics.heightPixels;
        Log.i("mydate" , tablewidth +"  " + tableheight);
        rackety = tableheight - 80;
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1){
                    gameView.invalidate();
                }
            }
        };

        gameView.setOnKeyListener(new View.OnKeyListener() { //按键
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (event.getKeyCode()){
                    case KeyEvent.KEYCODE_A:
                        if (racketx > 0) racketx -= 10;
                        break;
                    case KeyEvent.KEYCODE_D:
                        if (racketx < tablewidth - RACKET_WIDTH) racketx += 10;
                        break;
                }
                gameView.invalidate();//控制球拍左右移动

                return true;
            }
        });

        gameView.setOnTouchListener(new View.OnTouchListener() { //触摸
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int nowx = (int) event.getX();
                //int nowy = (int) event.getY();
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        oldx = nowx;
                        //oldy = nowy;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (nowx > oldx){ //向右移动
                            racketx += 10;
                        } else if (nowx < oldx) {//向左移动
                            racketx -= 10;
                        }
                        oldx = nowx;
                        break;
                    case MotionEvent.ACTION_UP:
                        oldx = nowx;
                        break;
                }
                gameView.invalidate();
                return true;
            }
        });

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //小球碰到游戏界面左右边框时
                if (ballx <= 0 || ballx >= tablewidth - BALL_SIZE){
                    xspped = -xspped; //横向速度反向
                }
                //如果小球的高度超出了球拍的位置，且横向不在球拍的范围内，则GAME OVER
                if (bally >= rackety - BALL_SIZE && (ballx < racketx || ballx > racketx+RACKET_WIDTH)){
                    timer.cancel();
                    isLose = true; //游戏结束标志
                } //如果小球在拍的范围内或碰到顶部，小球反弹
                else if (bally <= 0 || (bally >= rackety-BALL_SIZE && ballx > racketx && ballx < racketx + RACKET_WIDTH)){
                    yspeed = -yspeed; //纵向速度反向
                }
                //小球坐标不停的改变
                bally += yspeed;
                ballx += xspped;
                handler.sendEmptyMessage(1);

            }
        } , 0 , 100); //每0.1秒执行刷新

    }

    class GameView extends View{
        Paint paint = new Paint();

        public GameView(Context context) {
            super(context);
            setFocusable(true);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);
            if (isLose){
                paint.setColor(Color.RED);
                paint.setTextSize(40);
                canvas.drawText("GAME OVER" , tablewidth/2-100 , 200 , paint);
            } else {
                paint.setColor(Color.BLACK);
                canvas.drawCircle(ballx , bally , BALL_SIZE , paint); //绘制小球
                canvas.drawRect(racketx , rackety , racketx + RACKET_WIDTH , rackety + RACKET_HEIGHT , paint);//绘制球拍
            }

        }

    }


}