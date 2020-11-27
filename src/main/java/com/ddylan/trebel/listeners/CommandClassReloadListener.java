package com.ddylan.trebel.listeners;

import org.zeroturnaround.javarebel.ClassEventListener;

public class CommandClassReloadListener implements ClassEventListener {

    @Override
    public void onClassEvent(int i, Class aClass) {
        if (aClass != null) {

        }
    }

    @Override
    public int priority() {
        return 1;
    }
}
