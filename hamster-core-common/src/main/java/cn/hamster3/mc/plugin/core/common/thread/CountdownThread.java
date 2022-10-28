package cn.hamster3.mc.plugin.core.common.thread;

import cn.hamster3.mc.plugin.core.common.constant.CoreConstantObjects;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
public abstract class CountdownThread implements Runnable {
    private final long interval;
    private final long totalTicks;
    private int nowTicks;
    private ScheduledFuture<?> future;

    public CountdownThread(long interval, long totalTicks) {
        this.interval = interval;
        this.totalTicks = totalTicks;
        nowTicks = 0;
    }

    public void start() {
        start(interval);
    }

    public void start(long initialDelay) {
        future = CoreConstantObjects.SCHEDULED_EXECUTOR.scheduleWithFixedDelay(this, initialDelay, interval, TimeUnit.MILLISECONDS);
    }

    @Override
    public void run() {
        if (nowTicks == totalTicks) {
            try {
                onFinish();
            } catch (Exception e) {
                e.printStackTrace();
            }
            cancel();
            return;
        }
        try {
            onTick(nowTicks);
        } catch (Exception e) {
            e.printStackTrace();
        }
        nowTicks++;
    }

    /**
     * 计时器运行一个 tick
     *
     * @param tick 时间刻度，以 0 开始，到 interval-1 结束
     */
    protected abstract void onTick(int tick);

    protected abstract void onFinish();

    public void cancel() {
        future.cancel(false);
    }

    public long getInterval() {
        return interval;
    }

    public long getTotalTicks() {
        return totalTicks;
    }

    public int getNowTicks() {
        return nowTicks;
    }

    public void setNowTicks(int nowTicks) {
        this.nowTicks = nowTicks;
    }

    public ScheduledFuture<?> getFuture() {
        return future;
    }

    public void setFuture(ScheduledFuture<?> future) {
        this.future = future;
    }
}
