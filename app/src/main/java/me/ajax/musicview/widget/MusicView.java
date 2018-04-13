package me.ajax.musicview.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import java.util.Random;

import static me.ajax.musicview.utils.GeometryUtils.polarX;
import static me.ajax.musicview.utils.GeometryUtils.polarY;

/**
 * Created by aj on 2018/4/2
 */

public class MusicView extends View {

    Paint circlePaint1, circlePaint2, circlePaint3,
            circlePaint4, circlePaint5, circlePaint6;
    Paint dotPaint1;
    Paint dotPaint2;
    Paint dotPaint3;
    Paint textPaint;
    Paint textPaint2;
    Paint dotPaintHeart1;
    Paint dotPaintHeart2;
    Paint dotPaintHeart3;
    Paint redCirclePaint;
    Paint musicOutLinePaint;

    float circleRadius1 = dp2Dx(150);
    float circleRadius2 = dp2Dx(130);
    float circleRadius3 = dp2Dx(100);
    float circleRadius4 = dp2Dx(75);
    float circleRadius5 = dp2Dx(60);
    float circleRadius6 = dp2Dx(15);
    RectF circle4 = new RectF(-circleRadius4, -circleRadius4, circleRadius4, circleRadius4);
    RectF circle5 = new RectF(-circleRadius5, -circleRadius5, circleRadius5, circleRadius5);

    float circleDotRadius1 = dp2Dx(15);
    float circleDotRadius2 = dp2Dx(13);
    float circleDotRadius3 = dp2Dx(18);

    float circleDot1X = polarX(circleRadius1, 30);
    float circleDot1Y = polarY(circleRadius1, 30);
    float circleDot2X = polarX(circleRadius2, -30);
    float circleDot2Y = polarY(circleRadius2, -30);
    float circleDot3X = polarX(circleRadius3, 80);
    float circleDot3Y = polarY(circleRadius3, 80);

    ValueAnimator animator1;
    ValueAnimator animator2;
    ValueAnimator animator3;
    ValueAnimator animator4;
    ValueAnimator animator5;
    ValueAnimator animator6;

    int animator3RepeatCount = 0;
    int animator6RepeatCount = 0;

    boolean isShowCircle6;
    boolean isShowCircle5;
    boolean isShowText;
    boolean isShowRedCircle = false;
    boolean isShowOutCircle = false;
    boolean isShowSingName = false;

    String text;
    Rect timeTextBounds = new Rect();

    Random random = new Random();

    public MusicView(Context context) {
        super(context);
        init();
    }

    public MusicView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MusicView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {

        //画笔
        circlePaint1 = new Paint();
        circlePaint1.setColor(0xFFFFFFFF);
        circlePaint1.setStrokeWidth(dp2Dx(1));
        circlePaint1.setStyle(Paint.Style.STROKE);
        circlePaint2 = new Paint(circlePaint1);
        circlePaint3 = new Paint(circlePaint1);
        circlePaint4 = new Paint(circlePaint1);
        circlePaint5 = new Paint(circlePaint1);
        circlePaint6 = new Paint(circlePaint1);

        circlePaint4.setStrokeWidth(dp2Dx(5));
        circlePaint4.setColor(0xFFFFFFFF);
        circlePaint5.setColor(0xFFFFFFFF);
        circlePaint5.setStrokeWidth(dp2Dx(3));
        circlePaint6.setColor(0xFFFFFFFF);
        circlePaint6.setStyle(Paint.Style.FILL);

        dotPaint1 = new Paint();
        dotPaint1.setColor(0xFFFF00FF);
        dotPaint1.setStyle(Paint.Style.FILL);
        dotPaint2 = new Paint(dotPaint1);
        dotPaint3 = new Paint(dotPaint1);

        dotPaintHeart1 = new Paint();
        dotPaintHeart1.setColor(0xff003CBD);
        dotPaintHeart2 = new Paint();
        dotPaintHeart2.setColor(Color.WHITE);
        dotPaintHeart3 = new Paint();
        dotPaintHeart3.setColor(0xffB00404);

        textPaint = new Paint();
        textPaint.setColor(0xFFFF0000);
        textPaint.setTextSize(dp2Dx(24));
        textPaint2 = new Paint();
        textPaint2.setColor(Color.WHITE);
        textPaint2.setTextSize(dp2Dx(24));
        textPaint2.setTextAlign(Paint.Align.CENTER);

        redCirclePaint = new Paint();
        redCirclePaint.setStyle(Paint.Style.STROKE);
        redCirclePaint.setColor(0xFF702929);
        redCirclePaint.setStrokeWidth(dp2Dx(1));

        musicOutLinePaint = new Paint();
        musicOutLinePaint.setStyle(Paint.Style.STROKE);
        musicOutLinePaint.setColor(0x77FFFFFF);
        musicOutLinePaint.setStrokeWidth(dp2Dx(1));

        circlePaint1.setShader(new RadialGradient(circleDot1X, circleDot1Y, circleRadius1 * 2,
                0xff003CBD, 0xFF595050, Shader.TileMode.CLAMP));
        circlePaint2.setShader(new RadialGradient(circleDot2X, circleDot2Y, circleRadius2 * 2,
                0xffFFFFFF, 0xFF595050, Shader.TileMode.CLAMP));
        circlePaint3.setShader(new RadialGradient(circleDot3X, circleDot3Y, circleRadius3 * 2,
                0xffB00404, 0xFF595050, Shader.TileMode.CLAMP));
        circlePaint4.setShader(new RadialGradient(0, -circleRadius4, circleRadius4 * 2,
                0xff000000, 0xffFFFFFF, Shader.TileMode.CLAMP));
        circlePaint4.setShadowLayer(dp2Dx(5), 0, 0, Color.WHITE);
        setLayerType(LAYER_TYPE_SOFTWARE, circlePaint4);

        dotPaint1.setShader(new RadialGradient(circleDot1X, circleDot1Y, circleDotRadius1,
                0xff0000FF, 0x00FFFFFF, Shader.TileMode.CLAMP));
        dotPaint2.setShader(new RadialGradient(circleDot2X, circleDot2Y, circleDotRadius2,
                0xffFFFFFF, 0x00FFFFFF, Shader.TileMode.CLAMP));
        dotPaint3.setShader(new RadialGradient(circleDot3X, circleDot3Y, circleDotRadius3,
                0xffFF0000, 0x00FFFFFF, Shader.TileMode.CLAMP));

        initAnimator();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resetValues();
                cancelAllAnimator();

                if (animator1 != null) {
                    animator1.start();
                }
                if (animator2 != null) {
                    animator2.setStartDelay(1000);
                    animator2.start();
                }
            }
        });
        post(new Runnable() {
            @Override
            public void run() {
                performClick();
            }
        });
    }

    void cancelAllAnimator() {

        if (animator1 != null) animator1.cancel();
        if (animator2 != null) animator2.cancel();
        if (animator3 != null) animator3.cancel();
        if (animator4 != null) animator4.cancel();
        if (animator5 != null) animator5.cancel();
        if (animator6 != null) animator6.cancel();
    }

    public void resetValues() {

        circleRadius4 = dp2Dx(75);
        circleRadius5 = dp2Dx(60);
        circleRadius6 = dp2Dx(15);
        circle4.left = -circleRadius4;
        circle4.top = -circleRadius4;
        circle4.right = circleRadius4;
        circle4.bottom = circleRadius4;
        circle5.left = -circleRadius5;
        circle5.top = -circleRadius5;
        circle5.right = circleRadius5;
        circle5.bottom = circleRadius5;


        animator3RepeatCount = 0;
        animator6RepeatCount = 0;

        isShowCircle6 = true;
        isShowCircle5 = true;
        isShowOutCircle = true;
        isShowText = false;
        isShowRedCircle = false;
        isShowSingName = false;

        text = "";
    }

    public void initAnimator() {

        LinearInterpolator linearInterpolator = new LinearInterpolator();
        AccelerateInterpolator accelerateInterpolator = new AccelerateInterpolator();


        animator1 = ValueAnimator.ofFloat(0, 360);
        animator1.setDuration(30000);
        animator1.setRepeatCount(Integer.MAX_VALUE - 1);
        animator1.setInterpolator(linearInterpolator);
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });

        animator2 = ValueAnimator.ofInt(0, -dp2Dx(50), dp2Dx(20), 0);
        animator2.setDuration(500);
        animator2.setInterpolator(accelerateInterpolator);
        animator2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                circlePaint5.setColor(0xFFFFFFFF);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                circlePaint5.setColor(0xFFFF0000);
                isShowCircle6 = false;
                text = "ERROR";
                isShowText = true;
                if (animator3 != null) {
                    animator3.cancel();
                    animator3.start();
                }
            }
        });
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });


        animator3 = ValueAnimator.ofFloat(0, 2, 0);
        animator3.setDuration(800);
        animator3.setRepeatCount(1);
        animator3.setInterpolator(accelerateInterpolator);
        animator3.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationRepeat(Animator animation) {
                animator3.pause();
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (animator3 != null && animator3.isPaused()) {
                            animator3.resume();
                            animator3RepeatCount++;
                        }
                    }
                }, 500);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (animator4 != null) {
                    animator4.cancel();
                    animator4.setStartDelay(800);
                    animator4.start();
                }
            }
        });
        animator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (animator3RepeatCount == 1 && animation.getAnimatedFraction() > 0.5F) {
                    text = "STOP";
                    circlePaint5.setColor(0xFFFFFFFF);
                    textPaint.setColor(0xFFFFFFFF);
                }
                invalidate();
            }
        });

        animator4 = ValueAnimator.ofFloat(1, 0.3F);
        animator4.setDuration(500);
        animator4.setInterpolator(linearInterpolator);
        animator4.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                isShowCircle5 = false;
                isShowOutCircle = false;
                animator1.end();
                animator5.start();
                animator6.start();
            }
        });
        animator4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });

        animator5 = ValueAnimator.ofFloat(0, 1);
        animator5.setDuration(50000);
        animator5.setRepeatCount(Integer.MAX_VALUE - 1);
        animator5.setInterpolator(linearInterpolator);
        animator5.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            long second;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (System.currentTimeMillis() - second > 300) {

                    refreshMusicPoints();
                    invalidate();
                    second = System.currentTimeMillis();
                }
            }
        });

        animator6 = ValueAnimator.ofInt(dp2Dx(100), 0, 0);
        animator6.setDuration(2000);
        animator6.setInterpolator(new DecelerateInterpolator());
        animator6.setRepeatCount(2);
        animator6.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                isShowSingName = true;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                animator6RepeatCount++;
            }

        });
        animator6.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int mWidth = getWidth();
        int mHeight = getHeight();

        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);

        //外部圆
        drawOutCircle(canvas);

        //绘制中心圆点
        drawCenterCircle(canvas);

        //绘制内圆
        drawInnerCircle(canvas);

        //音乐律动
        drawMusicMove(canvas);

        //绘制文本
        drawText(canvas);

        canvas.restore();
    }

    void drawText(Canvas canvas) {

        if (!isShowSingName) return;

        String text;
        String desc;
        if (animator6RepeatCount == 0) {
            text = "Tobu";
            desc = "Noir! Noir!";
        } else if (animator6RepeatCount == 1) {
            text = "天堂";
            desc = "腾格尔";
        } else {
            text = "AJ";
            desc = "I am ajax";
        }

        canvas.save();
        canvas.clipRect(-circleRadius4, -circleRadius4, circleRadius4, circleRadius4);

        int animatedValue = (int) animator6.getAnimatedValue();

        textPaint2.setTextSize(dp2Dx(32));
        canvas.drawText(text, 0, animatedValue, textPaint2);
        textPaint2.setTextSize(dp2Dx(16));
        canvas.drawText(desc, 0, dp2Dx(30) + animatedValue, textPaint2);

        canvas.restore();
    }

    void drawMusicMove(Canvas canvas) {

        if (!animator5.isRunning()) return;
        for (int i = 0; i < 120; i++) {

            int point = musicPoints[i];
            double angle = i * 3;

            //此处应该用 path 的！

            canvas.drawLine(polarX(circleRadius1 - point, angle), polarY(circleRadius1 - point, angle),
                    polarX(circleRadius1 + point, angle), polarY(circleRadius1 + point, angle), musicOutLinePaint);

            canvas.drawLine(polarX(circleRadius3 - point, angle), polarY(circleRadius3 - point, angle),
                    polarX(circleRadius3 - point + dp2Dx(2), angle), polarY(circleRadius3 - point + dp2Dx(2), angle), musicOutLinePaint);

            canvas.drawLine(polarX(circleRadius3 + point, angle), polarY(circleRadius3 + point, angle),
                    polarX(circleRadius3 + point + dp2Dx(2), angle), polarY(circleRadius3 + point + dp2Dx(2), angle), musicOutLinePaint);
        }

    }

    int musicPoints[] = new int[120];

    void refreshMusicPoints() {

        int musicSpecialPoints[] = new int[7];

        for (int i = 0; i < musicSpecialPoints.length; i++) {
            musicSpecialPoints[i] = random.nextInt(120);
        }
        for (int i = 0; i < musicPoints.length; i++) {

            int offset = 0;
            for (int j = 0; j < musicSpecialPoints.length; j++) {
                if (Math.abs(musicSpecialPoints[j] - i) < 3) {
                    offset = dp2Dx(4 + random.nextInt(8));
                    break;
                }
            }
            if (offset == 0) {
                offset = dp2Dx(2 + random.nextInt(3));
            }

            musicPoints[i] = offset;
        }
    }

    //运动圆点
    void drawOutCircle(Canvas canvas) {

        if (!isShowOutCircle) return;

        float circleRadiusScale1 = 1;
        float circleRadiusScale2 = 1;
        float circleRadiusScale3 = 1;
        circlePaint3.setAlpha(255);
        if (animator2.isRunning()) {
            float fraction = animator2.getAnimatedFraction();
            if (fraction < 0.5f) {
                circlePaint3.setAlpha((int) (255 - 255 * fraction));
            } else {
                fraction = 1F - fraction;
                circlePaint3.setAlpha(0);
            }
            circleRadiusScale1 = 1 + 0.5F * fraction;
            circleRadiusScale2 = 1 + 0.7F * fraction;
            circleRadiusScale3 = 1 + 0.3F * fraction;
        }
        if (animator3.isRunning()) {
            float fraction = animator3.getAnimatedFraction();
            if (animator3.getAnimatedFraction() < 0.5f) {
                circlePaint3.setAlpha(0);
            } else {
                fraction = 1F - fraction;
                circlePaint3.setAlpha((int) (255 - 255 * fraction));
            }


            //第一次动画不需要显示红线,第二次动画外元不需要变化
            if (animator3RepeatCount == 0) {
                circlePaint3.setAlpha(0);

                circleRadiusScale1 = 1 - 0.5F * fraction;
                circleRadiusScale2 = 1 - 0.5F * fraction;
                circleRadiusScale3 = 1 - 0.5F * fraction;

                if (animator3.getAnimatedFraction() > 0.5f) {
                    isShowRedCircle = true;
                }
            } else {
                circleRadiusScale1 = 1 + 0.5F * fraction;
                circleRadiusScale2 = 1 + 0.7F * fraction;
                circleRadiusScale3 = 1 + 0.3F * fraction;

                if (animator3.getAnimatedFraction() > 0.5f) {
                    isShowRedCircle = false;
                }
            }
        }

        if (animator4.isRunning()) {
            float fraction = animator4.getAnimatedFraction();
            fraction *= 2;
            circleRadiusScale1 = 1 * (1 + fraction);
            circleRadiusScale2 = 1 * (1 + fraction);
            circleRadiusScale3 = 1 * (1 + fraction);
        }

        canvas.save();

        canvas.rotate((float) animator1.getAnimatedValue(), 0, 0);

        //圆1
        canvas.save();
        canvas.scale(circleRadiusScale1, circleRadiusScale1, 0, 0);
        if (isShowRedCircle) {
            canvas.drawCircle(0, 0, circleRadius1, redCirclePaint);
        } else {
            canvas.drawCircle(0, 0, circleRadius1, circlePaint1);
            canvas.drawCircle(circleDot1X, circleDot1Y, circleDotRadius1 / 3, dotPaintHeart1);
            canvas.drawCircle(circleDot1X, circleDot1Y, circleDotRadius1, dotPaint1);
        }
        canvas.restore();

        //圆2
        canvas.save();
        canvas.scale(circleRadiusScale2, circleRadiusScale2, 0, 0);

        if (isShowRedCircle) {
            canvas.drawCircle(0, 0, circleRadius2, redCirclePaint);
        } else {
            canvas.drawCircle(0, 0, circleRadius2, circlePaint2);
            canvas.drawCircle(circleDot2X, circleDot2Y, circleDotRadius2 / 3, dotPaintHeart2);
            canvas.drawCircle(circleDot2X, circleDot2Y, circleDotRadius2, dotPaint2);
        }
        canvas.restore();
        canvas.restore();


        canvas.save();
        canvas.rotate(-(float) animator1.getAnimatedValue(), 0, 0);

        //圆3
        canvas.save();
        canvas.scale(circleRadiusScale3, circleRadiusScale3, 0, 0);

        if (isShowRedCircle) {
            canvas.drawCircle(0, 0, circleRadius3, redCirclePaint);
        } else {
            canvas.drawCircle(0, 0, circleRadius3, circlePaint3);
            if (circlePaint3.getAlpha() > 0) {
                canvas.drawCircle(circleDot3X, circleDot3Y, circleDotRadius3 / 3, dotPaintHeart3);
                canvas.drawCircle(circleDot3X, circleDot3Y, circleDotRadius3, dotPaint3);
            }
        }
        canvas.restore();

        //恢复
        canvas.restore();
    }

    //绘制中心圆点
    void drawCenterCircle(Canvas canvas) {

        if (!isShowCircle6) return;
        if (animator2.isRunning()) {
            float fraction = animator2.getAnimatedFraction();
            float left = -circleRadius4 - circleRadius6 / 2;
            float right = circleRadius4 - circleRadius6 / 2;
            if (fraction < 0.5f) {
                fraction *= 2;
                canvas.drawCircle(left * fraction, 0, circleRadius6 * (1 + fraction / 10), circlePaint6);
            } else {
                fraction = (fraction - 0.5f) * 2;
                canvas.drawCircle(left + (right - left) * fraction, 0, circleRadius6 * (1 - fraction), circlePaint6);

                if (fraction > 0.5) {
                    text = "ERROR";
                    textPaint.setColor(0xFFFF0000);
                    canvas.save();
                    canvas.clipRect(-timeTextBounds.width() / 2f, -timeTextBounds.height(),
                            -timeTextBounds.width() / 2f + timeTextBounds.width() * fraction, timeTextBounds.height());
                    textPaint.getTextBounds(text, 0, text.length(), timeTextBounds);
                    canvas.drawText(text, -timeTextBounds.width() / 2f, timeTextBounds.height() / 2f, textPaint);
                    canvas.restore();
                }
            }
        } else {
            canvas.drawCircle(0, 0, circleRadius6, circlePaint6);
        }

    }

    //绘制内圆
    void drawInnerCircle(Canvas canvas) {

        canvas.save();

        circle4.left = -circleRadius4;
        circle4.right = circleRadius4;
        if (animator3.isRunning()) {//转动
            canvas.scale(1 - (float) animator3.getAnimatedValue(), 1, 0, 0);
            float fraction = animator3.getAnimatedFraction();

            if (fraction < 0.25F) {
                fraction *= 4;
                circle4.left = -circleRadius4 - circleRadius4 * fraction;
            } else if (fraction < 0.5F) {
                fraction = 0.25f - (fraction - 0.25F);
                fraction *= 4;
                circle4.right = circleRadius4 - circleRadius4 * fraction;
            }
        }
        canvas.drawOval(circle4, circlePaint4);

        if (isShowCircle5) {
            if (animator4.isRunning()) {
                canvas.scale((float) animator4.getAnimatedValue(), (float) animator4.getAnimatedValue(), 0, 0);
            }

            circle5.left = -circleRadius5;
            circle5.right = circleRadius5;
            if (animator2.isRunning()) {

                circle5.left = -circleRadius5 + (int) animator2.getAnimatedValue();
                circle5.right = circleRadius5 + (int) animator2.getAnimatedValue();
            }

            canvas.drawOval(circle5, circlePaint5);
            if (isShowText && !TextUtils.isEmpty(text)) {
                textPaint.getTextBounds(text, 0, text.length(), timeTextBounds);
                canvas.drawText(text, -timeTextBounds.width() / 2f, timeTextBounds.height() / 2f, textPaint);
            }
        }

        canvas.restore();
    }

    int dp2Dx(int dp) {
        return (int) (getResources().getDisplayMetrics().density * dp);
    }

    void l(Object o) {
        Log.e("######", o.toString());
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimAndRemoveCallbacks();
    }

    private void stopAnimAndRemoveCallbacks() {

        if (animator1 != null) animator1.end();
        if (animator2 != null) animator2.end();
        if (animator3 != null) animator3.end();
        if (animator4 != null) animator4.end();
        if (animator5 != null) animator5.end();
        if (animator6 != null) animator6.end();

        Handler handler = this.getHandler();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}
