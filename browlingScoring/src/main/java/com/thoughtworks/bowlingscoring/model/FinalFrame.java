package com.thoughtworks.bowlingscoring.model;

import com.thoughtworks.bowlingscoring.Constants;
import com.thoughtworks.bowlingscoring.exception.InvalidScoreCountException;
import com.thoughtworks.bowlingscoring.exception.InvalidScoreValueException;

import static com.thoughtworks.bowlingscoring.Constants.MAX_SCORE_VALUE;

public class FinalFrame extends GeneralFrame {

    private Integer finalBallScore;

    public FinalFrame(Integer frameIndex) {
        super(frameIndex);
    }

    public Integer getFinalBallScore() {
        return finalBallScore;
    }

    public void setFinalBallScore(Integer finalBallScore) {
        this.finalBallScore = finalBallScore;
    }

    @Override
    public boolean isStrike() {
        return false;
    }

    @Override
    public boolean isSpare() {
        return false;
    }

    @Override
    public Integer getNextFirstBall() {
        return getSecondBallScore();
    }

    @Override
    public Integer getNextSecondBall() {
        return getFinalBallScore();
    }

    @Override
    public GeneralFrame getNextFrame() {
        return null;
    }

    @Override
    public Integer getFrameScore() {
        return getFirstBallScore() + getSecondBallScore() + getFinalBallScore();
    }

    @Override
    public GeneralFrame addScore(Integer score) {
        if (score < Constants.MIN_SCORE_VALUE || score > MAX_SCORE_VALUE) {
            throw new InvalidScoreValueException("The value of the score for one ball is between 0 and 10.");
        }
        if (getFirstBallScore() == null) {
            setFirstBallScore(score);
        } else if (getSecondBallScore() == null) {
            setSecondBallScore(score);
        } else if (getFinalBallScore() == null) {
            setFinalBallScore(score);
        } else {
            throw new InvalidScoreCountException();
        }
        return this;
    }
}
