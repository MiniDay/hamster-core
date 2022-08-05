package cn.hamster3.core.common.thread;

import cn.hamster3.core.common.constant.ConstantObjects;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
public abstract class CountdownThread implements Runnable {
    private final long interval;
    private final long ticks;
    protected int tick;
    private ScheduledFuture<?> future;

    public CountdownThread(long interval, long ticks) {
        this.interval = interval;
        this.ticks = ticks;
        tick = 0;
    }

    public void start() {
        start(interval);
    }

    public void start(long initialDelay) {
        future = ConstantObjects.SCHEDULED_EXECUTOR.scheduleWithFixedDelay(this, initialDelay, interval, TimeUnit.MILLISECONDS);
    }

    @Override
    public void run() {
        if (tick == ticks) {
            try {
                onFinish();
            } catch (Exception e) {
                e.printStackTrace();
            }
            cancel();
            return;
        }
        try {
            onTick(tick);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tick++;
    }

    /**
     * 计时器运行一个 tick
     *
     * @param tick 时间刻度，以 0 开始，到 interval-1 结束
     */
    protected void onTick(int tick) {
    }

    public void cancel() {
        future.cancel(false);
    }

    protected abstract void onFinish();
}
