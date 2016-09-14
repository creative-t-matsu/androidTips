package com.notionassortment;

public class Easing {

    //currentTime:経過時間
    //beginningValue:初期値
    //changeInValue:変動値
    //duration:継続時間
    public static float OutBounce(float currentTime, float beginningValue, float changeInValue, float duration) {
        if ((currentTime /= duration) < (1f / 2.75f)) {
            return changeInValue * (7.5625f * currentTime * currentTime) + beginningValue;
        } else if (currentTime < (2f / 2.75f)) {
            return changeInValue * (7.5625f * (currentTime -= (1.5f / 2.75f)) * currentTime + 0.75f) + beginningValue;
        } else if (currentTime < (2.5f / 2.75f)) {
            return changeInValue * (7.5625f * (currentTime -= (2.25f / 2.75f)) * currentTime + 0.9375f) + beginningValue;
        } else {
            return changeInValue * (7.5625f * (currentTime -= (2.625f / 2.75f)) * currentTime + 0.984375f) + beginningValue;
        }
    }
}
