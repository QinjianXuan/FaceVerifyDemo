/*--------------------------------------------------
 * Copyright (C) 2016 The Android SanDao Project
 *               http://www.sandaogroup.com
 * 创建时间：2016/6/24
 * 内容说明：
 *
 * 变更时间：
 * 变更说明：
 * -------------------------------------------------- */
package com.ebgsplatform.badouloanapp.faceiddemo.view.progressHUD;

/**
 * If a view implements this interface passed to the HUD as a custom view, its progress
 * can be updated by calling setMax() and setProgress() on the HUD.
 * This interface only provides convenience, how progress work depends on the view implementation.
 */
public interface Determinate {
    void setMax(int max);
    void setProgress(int progress);
}
