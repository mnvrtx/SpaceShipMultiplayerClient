package com.fogok.spaceships.utils;

/**
 * OVERMY.NET - Make your device live! *
 * <p/>
 * Games: http://play.google.com/store/apps/developer?id=OVERMY
 *
 * @author Andrey Mikheev (cb)
 */


import com.badlogic.gdx.math.Interpolation;


public class FloatAnimator {

    public float current = 0.0f;
    private float from = 0.0f;
    private float to   = 0.0f;

    private float time          = 0.0f;
    private float animationTime = 1.0f;

    private int counter;

    private Interpolation interp = null;

    private boolean needToUpdate = true;

    public FloatAnimator() {
        this( 0.0f, 1.0f, 0.35f, Interpolation.fade );
    }

    public FloatAnimator( final float from, final float to, final float time ) {
        this( from, to, time, Interpolation.fade );
    }

    public FloatAnimator( final float from, final float to, final float time, final Interpolation interp ) {
        setFrom( from );
        setTo( to );
        setAnimationTime( time );
        setInterpolation( interp );
        resetTime();
        counter = 0;
    }

    public static final int CLAW_KNOCK_PRESET = 0;
    private boolean isClawKnockPreset = false;
    public void setPreset(int preset){
        switch (preset){
            case CLAW_KNOCK_PRESET:
                isClawKnockPreset = true;
                break;
        }
    }

    public FloatAnimator setFrom( final float from ) {
        current = from;
        this.from = from;
        return this;
    }

    public FloatAnimator setTo( final float to ) {
        this.to = to;
        return this;
    }

    public FloatAnimator setAnimationTime( final float time ) {
        animationTime = time;
        return this;
    }

    public FloatAnimator setInterpolation( final Interpolation interp ) {
        this.interp = interp;
        return this;
    }

    public Interpolation getInterpolation() {
        return interp;
    }

    public void freeze(){
        needToUpdate = false;
    }

    public FloatAnimator resetTime() {
        time = 0.0f;
        needToUpdate = true;
        current = from;


        if (isClawKnockPreset){
            if (counter % 2 == 0)
                interp = Interpolation.fade;
            else
                interp = Interpolation.fade;
        }

        return this;
    }



    public void update( final float delta ) {

        if ( !isNeedToUpdate() ) { return; }

        time += delta;

        current = interp.apply( from, to, com.fogok.dataobjects.utils.GMUtils.normalizeOneZero(time / animationTime));

        // Если "отведённое время" минус "прошедшее время" всё ещё больше нуля, то апдейтим
        if ( animationTime - time > 0 ) { needToUpdate = true; }
        else { needToUpdate = false; }
    }

    /**
     * Постоянное повторение петли из FROM в TO, и из TO в FROM
     * */
    public boolean updateLoop( final float delta ) {

        boolean response = false;
        if (!isNeedToUpdate()) {
            float ffrom = from;
            from = to;
            to = ffrom;
            current = from;
            isRevers = !isRevers;
            counter++;
            resetTime();
            response = true;
        } else {
            current = interp.apply( from, to, com.fogok.dataobjects.utils.GMUtils.normalizeOneZero(time / animationTime ));
            time += delta;
        }


        if ( animationTime - time > 0 ) { needToUpdate = true; }
        else { needToUpdate = false; }
        return response;
    }

    private boolean isRevers;

    public boolean isRevers() {
        return isRevers;
    }

    public boolean isCicleEnded(int cicles){
        if (counter >= cicles){
            counter = 0;
            return true;
        }
        return false;
    }

    public boolean isCicleEndedAndNotResetCounter(int cicles){
        if (counter >= cicles){
            return true;
        }
        return false;
    }

    public void resetCounter(){
        counter = 0;
    }

    public boolean isNeedToUpdate() {
        return needToUpdate;
    }

    public void setNeedToUpdate(boolean needToUpdate) {
        this.needToUpdate = needToUpdate;
    }

    public float getCurrentTime(){
        return com.fogok.dataobjects.utils.GMUtils.normalizeOneZero(time / animationTime);
    }

    public FloatAnimator setTime(float time) {
        this.time = time * animationTime;
        return this;
    }

    public float getTo() {
        return to;
    }

    public float getFrom() {
        return from;
    }

    public FloatAnimator fromCurrent() {
        from = current;
        return this;
    }

    public void setCurrentFrom() {
        this.current = from;
    }

    @Override
    public String toString() {
        return "from: " + from + "; to: " + to + "; current: " + current + ";";
    }
}
