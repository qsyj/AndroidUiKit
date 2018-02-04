package com.wqlin.android.uikit.widget;

/**
 * Created by wqlin on 2018/2/4.
 */

public class ItemDecorationConfig {
    private int leftWidth;
    private int leftPaddingLeft;
    private int leftPaddingTop;
    private int leftPaddingRight;
    private int leftPaddingBottom;
    private int leftColor;
    private int topHeight;
    private int topPaddingLeft;
    private int topPaddingTop;
    private int topPaddingRight;
    private int topPaddingBottom;
    private int topColor;
    private int rightWidth;
    private int rightPaddingLeft;
    private int rightPaddingTop;
    private int rightPaddingRight;
    private int rightPaddingBottom;
    private int rightColor;
    private int bottomHeight;
    private int bottomPaddingLeft;
    private int bottomPaddingTop;
    private int bottomPaddingRight;
    private int bottomPaddingBottom;
    private int bottomColor;

    public ItemDecorationConfig setLeft(int leftWidth,int leftColor) {
        return setLeft(leftWidth,0,0,0,0,leftColor);
    }

    public ItemDecorationConfig setLeft(int leftWidth, int leftPaddingTop,int leftPaddingBottom ,int leftColor) {
        return setLeft(leftWidth,0,0,leftPaddingTop,leftPaddingBottom,leftColor);
    }

    public ItemDecorationConfig setLeft(int leftWidth, int leftPaddingLeft, int leftPaddingRight, int leftPaddinTop, int leftPaddinBottom, int leftColor) {
        this.leftWidth = leftWidth;
        this.leftPaddingLeft = leftPaddingLeft;
        this.leftPaddingRight = leftPaddingRight;
        this.leftPaddingTop = leftPaddinTop;
        this.leftPaddingBottom = leftPaddinBottom;
        this.leftColor = leftColor;
        return this;
    }

    public ItemDecorationConfig setRight(int rightWidth,int rightColor) {
        return setRight(rightWidth, 0, 0, rightColor);
    }

    public ItemDecorationConfig setRight(int rightWidth, int rightPaddingTop,int rightPaddingBottom , int rightColor) {
        return setRight(rightWidth, 0, 0, rightPaddingTop, rightPaddingBottom, rightColor);
    }

    public ItemDecorationConfig setRight(int rightWidth, int rightPaddingLeft, int rightPaddingRight,int rightPaddingTop,int rightPaddingBottom , int rightColor) {
        this.rightWidth = rightWidth;
        this.rightPaddingLeft = rightPaddingLeft;
        this.rightPaddingRight = rightPaddingRight;
        this.rightPaddingTop = rightPaddingTop;
        this.rightPaddingBottom = rightPaddingBottom;
        this.rightColor = rightColor;
        return this;
    }

    public ItemDecorationConfig setTop(int topHeight,  int topColor) {
        return setTop(topHeight, 0, 0,  topColor);
    }

    public ItemDecorationConfig setTop(int topHeight, int topPaddingLeft,int topPaddingRight , int topColor) {
        return setTop(topHeight, 0, 0, topPaddingLeft, topPaddingRight, topColor);
    }

    public ItemDecorationConfig setTop(int topHeight,int topPaddingTop,int topPaddingBottom ,int topPaddingLeft,int topPaddingRight , int topColor) {
        this.topHeight = topHeight;
        this.topPaddingTop = topPaddingTop;
        this.topPaddingBottom = topPaddingBottom;
        this.topPaddingLeft = topPaddingLeft;
        this.topPaddingRight = topPaddingRight;
        this.topColor = topColor;
        return this;
    }

    public ItemDecorationConfig setBottom(int bottomHeight, int bottomColor) {
        return setBottom(bottomHeight, 0, 0,bottomColor);
    }

    public ItemDecorationConfig setBottom(int bottomHeight, int bottomPaddingLeft,int bottomPaddingRight , int bottomColor) {
        return setBottom(bottomHeight, 0, 0, bottomPaddingLeft, bottomPaddingRight, bottomColor);
    }

    public ItemDecorationConfig setBottom(int bottomHeight,int bottomPaddingTop,int bottomPaddingBottom, int bottomPaddingLeft,int bottomPaddingRight , int bottomColor) {
        this.bottomHeight = bottomHeight;
        this.bottomPaddingTop = bottomPaddingTop;
        this.bottomPaddingBottom = bottomPaddingBottom;
        this.bottomPaddingLeft = bottomPaddingLeft;
        this.bottomPaddingRight = bottomPaddingRight;
        this.bottomColor = bottomColor;
        return this;
    }

    public int getLeftWidth() {
        return leftWidth;
    }

    public int getLeftPaddingLeft() {
        return leftPaddingLeft;
    }

    public int getLeftPaddingTop() {
        return leftPaddingTop;
    }

    public int getLeftPaddingRight() {
        return leftPaddingRight;
    }

    public int getLeftPaddingBottom() {
        return leftPaddingBottom;
    }

    public int getLeftColor() {
        return leftColor;
    }

    public int getTopHeight() {
        return topHeight;
    }

    public int getTopPaddingLeft() {
        return topPaddingLeft;
    }

    public int getTopPaddingTop() {
        return topPaddingTop;
    }

    public int getTopPaddingRight() {
        return topPaddingRight;
    }

    public int getTopPaddingBottom() {
        return topPaddingBottom;
    }

    public int getTopColor() {
        return topColor;
    }

    public int getRightWidth() {
        return rightWidth;
    }

    public int getRightPaddingLeft() {
        return rightPaddingLeft;
    }

    public int getRightPaddingTop() {
        return rightPaddingTop;
    }

    public int getRightPaddingRight() {
        return rightPaddingRight;
    }

    public int getRightPaddingBottom() {
        return rightPaddingBottom;
    }

    public int getRightColor() {
        return rightColor;
    }

    public int getBottomHeight() {
        return bottomHeight;
    }

    public int getBottomPaddingLeft() {
        return bottomPaddingLeft;
    }

    public int getBottomPaddingTop() {
        return bottomPaddingTop;
    }

    public int getBottomPaddingRight() {
        return bottomPaddingRight;
    }

    public int getBottomPaddingBottom() {
        return bottomPaddingBottom;
    }

    public int getBottomColor() {
        return bottomColor;
    }
}
