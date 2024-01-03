package io.github.astro.mantis.protocol;

/**
 * @Author WenBo Zhou
 * @Date 2023/12/14 19:24
 */
public interface Request extends Envelope {

    boolean isOneWay();
}
