package io.github.astro.mantis.rpc.listener;

import io.github.astro.mantis.common.constant.Constant;
import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.event.EventListener;
import io.github.astro.mantis.transport.channel.Channel;
import io.github.astro.mantis.transport.event.HeartBeatEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Close idle connections according to config
 * {@link io.github.astro.mantis.configuration.config.ProtocolConfig#spareCloseTimes}
 * to reduce resource waste
 */
public class HeartBeatEventListener implements EventListener<HeartBeatEvent> {

    private static final Logger logger = LoggerFactory.getLogger(HeartBeatEventListener.class);

    @Override
    public void onEvent(HeartBeatEvent event) {
        Channel channel = event.getSource();
        URL url = (URL) channel.getAttribute(Key.URL);
        AtomicInteger readIdleRecord = (AtomicInteger) channel.getAttribute(Key.READER_IDLE_TIMES);
        AtomicInteger writeIdleRecord = (AtomicInteger) channel.getAttribute(Key.WRITE_IDLE_TIMES);
        AtomicInteger allIdlerRecord = (AtomicInteger) channel.getAttribute(Key.ALL_IDLE_TIMES);
        boolean isPrintLog = false;
        int spareCloseTimes = Constant.DEFAULT_SPARE_CLOSE_TIMES;
        if (url != null) {
            spareCloseTimes = url.getIntParameter(Key.SPARE_CLOSE_TIMES, Constant.DEFAULT_SPARE_CLOSE_TIMES);
            isPrintLog = url.getBooleanParameter(Key.HEARTBEAT_LOG_ENABLE, false);
        }
        if (isPrintLog) {
            logger.debug("Received Event({})", event.getClass().getSimpleName());
            logger.debug("readIdleRecord:{},writeIdleRecord:{},allIdlerRecord:{}", readIdleRecord, writeIdleRecord, allIdlerRecord);
        }
        if (allIdlerRecord != null) {
            int allIdleTimes = allIdlerRecord.incrementAndGet();
            if (readIdleRecord != null) {
                int readIdleTimes = readIdleRecord.incrementAndGet();
                if (readIdleTimes > spareCloseTimes && allIdleTimes > spareCloseTimes) {
                    channel.close();
                }
            }
            if (writeIdleRecord != null) {
                int writeIdleTimes = writeIdleRecord.incrementAndGet();
                if (writeIdleTimes > spareCloseTimes && allIdleTimes > spareCloseTimes) {
                    channel.close();
                }
            }
        }
    }

}
