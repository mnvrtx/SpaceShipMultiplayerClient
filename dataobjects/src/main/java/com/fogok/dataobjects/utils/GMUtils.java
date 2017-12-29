package com.fogok.dataobjects.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GMUtils {

    //    private ShapeRenderer shapeRenderer = new ShapeRenderer();
//        batch.activateEnd();
//        Gdx.gl20.glLineWidth(3f);
//        shapeRenderer.setProjectionMatrix(Main.getCamera().combined);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.rect(container.x, container.y, container.width, container.height);
//        shapeRenderer.activateEnd();
//        batch.begin();

//    private static Rectangle displayBounds;
    private static Random rnd;
//
//    public static Rectangle getDisplayBounds() {
//        if (displayBounds == null)
//            displayBounds = new Rectangle();
//        return displayBounds;
//    }

    public static Random getRnd() {
        if (rnd == null)
            rnd = new Random();
        return rnd;
    }

    public static float getDeg(float x1, float y1, float x2, float y2){
        return (float) (Math.atan2(y1 - y2, x1 - x2) / Math.PI * 180d) + 180f;
    }

    public static float getNextX(float dist, float degr){
        return (float) Math.cos(Math.toRadians((double) degr)) * dist;
    }

    public static float getNextY(float dist, float degr){
        return (float) Math.sin(Math.toRadians((double) degr)) * dist;
    }

    public static float getDist(float x1, float y1, float x2, float y2){
        return (float) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }

    public static float lerpDirection(float currentVal, float targetVal, float change)
    {
        if (Math.abs(targetVal - currentVal) < change * 2f)
            return targetVal;
        change = currentVal > targetVal ? -change : change;
        currentVal += Math.abs(targetVal - currentVal) < 180 ? change : -change;
        currentVal += currentVal > 360 ? -360 : currentVal < 0 ? 360 : 0;

        return currentVal;
    }

//    public static float lerpValue(float deltaTime, float targetVal, float startVal, float currentVal) {
//        float absDif = Math.abs(targetVal - startVal);
//        float change = (Math.min(deltaTime, NettyHandler.TIMEITERSSLEEP) / NettyHandler.TIMEITERSSLEEP) * absDif;
//        if (absDif < change * 2f)
//            return targetVal;
//        change = currentVal > targetVal ? -change : change;
//        currentVal += change;
//        return absDif != 0f ? currentVal : targetVal;
//    }

    public static float getRoundedVal(float nonRoundedVal){
        return (int)(nonRoundedVal * 1000) / 1000f;
    }

    public static Object[] reverse(Object[] arr) {
        List<Object> list = Arrays.asList(arr);
        Collections.reverse(list);
        return list.toArray();
    }

//    public static void main(String[] args) throws InterruptedException {
//        float start = -2, target = 2;
//        float current = start;
//        while (true) {
//            Thread.sleep(500);
//            current = lerpValue(start, target, current);
//            System.out.println(current);
//        }
//    }


//    private static Texture texture;
//
//    public static Texture generateBlankTexture(){
//        if (texture == null){
//            int div = 30;
//            Pixmap pixmap = new Pixmap(div, div, Pixmap.Format.RGB565);
//            pixmap.setColor(1f, 1f, 1f, 1f);
//            pixmap.fillRectangle(0, 0, div, div);
//            texture = new Texture(pixmap);
//            pixmap.dispose();
//        }
//        return texture;
//    }
//
//    public static void disposeBlankTexture(){
//        if (texture != null)
//            texture.dispose();
//    }


    public static float normalizeOneZero(float alpha){
        return normalizeOneCustomZero(alpha, 0f);
    }

    public static float normalizeOneCustomZero(float alpha, float zero){
        if (alpha < zero)
            alpha = zero;
        else if (alpha > 1f)
            alpha = 1f;
        return alpha;
    }
}
