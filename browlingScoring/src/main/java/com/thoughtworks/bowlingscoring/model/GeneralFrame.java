package com.thoughtworks.bowlingscoring.model;

import com.thoughtworks.bowlingscoring.Constants;
import com.thoughtworks.bowlingscoring.exception.InvalidScoreCountException;
import com.thoughtworks.bowlingscoring.exception.InvalidScoreValueException;

import static com.thoughtworks.bowlingscoring.Constants.MAX_FRAME_COUNT_VALUE;
import static com.thoughtworks.bowlingscoring.Constants.MAX_SCORE_VALUE;
import static com.thoughtworks.bowlingscoring.Constants.MIN_SCORE_VALUE;

public class GeneralFrame {

    private Integer firstBallScore;

    private Integer secondBallScore;

    private GeneralFrame nextFrame;

    // value from 0
    private Integer frameIndex;

    public GeneralFrame getNextFrame() {
        if (nextFrame == null ) {
            if(frameIndex < MAX_FRAME_COUNT_VALUE - 1){
                this.nextFrame = new GeneralFrame(frameIndex + 1);
            } else if(frameIndex == MAX_FRAME_COUNT_VALUE - 1){
                this.nextFrame = new FinalFrame(frameIndex + 1);
            } else {
                throw new InvalidScoreCountException();
            }
        }
        return nextFrame;
    }

    public GeneralFrame(Integer frameIndex) {
        this.frameIndex = frameIndex;
    }

    public Integer getFirstBallScore() {
        return firstBallScore;
    }

    public void setFirstBallScore(Integer firstBallScore) {
        this.firstBallScore = firstBallScore;
    }

    public Integer getSecondBallScore() {
        return secondBallScore;
    }

    public void setSecondBallScore(Integer secondBallScore) {
        this.secondBallScore = secondBallScore;
    }

    public boolean isStrike() {
        return firstBallScore == MAX_SCORE_VALUE;
    }

    public boolean isSpare() {
        return firstBallScore + secondBallScore == MAX_SCORE_VALUE;
    }

    public GeneralFrame addScore(Integer score) {
        if (score < Constants.MIN_SCORE_VALUE || score > MAX_SCORE_VALUE) {
            throw new InvalidScoreValueException("The value of the score for one ball is between 0 and 10.");
        }
        if (this.firstBallScore == null) {
            this.firstBallScore = score;
            return this;
        }
        if (this.secondBallScore != null) {
            return this.getNextFrame().addScore(score);
        }
        if (this.isStrike()) {
            this.secondBallScore = MIN_SCORE_VALUE;
            return this.getNextFrame().addScore(score);
        }
        this.secondBallScore = score;
        if (firstBallScore + secondBallScore > MAX_SCORE_VALUE) {
            throw new InvalidScoreValueException("The total score for one frame can not greater than 10.");
        }
        return this;
    }


    public Integer getScoreSummary() {
        return firstBallScore + secondBallScore;
    }

    public Integer getNextFirstBall() {
        return getNextFrame().getFirstBallScore();
    }

    public Integer getNextSecondBall() {
        if (getNextFrame().getFirstBallScore() == MAX_SCORE_VALUE) {
            return getNextFrame().getNextFirstBall();
        } else {
            return getNextFrame().getSecondBallScore();
        }
    }

    public Integer getFrameScore() {
        if (isStrike()) {
            return getScoreSummary() + getNextFirstBall() + getNextSecondBall();
        }
        if (isSpare()) {
            return getScoreSummary() + getNextFirstBall();
        }
        return getScoreSummary();
    }
}
