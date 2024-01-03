package io.github.astro.mantis.code;

import io.github.astro.mantis.Request;
import io.github.astro.mantis.Response;
import io.github.astro.mantis.byteutils.ByteReader;
import io.github.astro.mantis.byteutils.ByteWriter;
import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.constant.KeyValues;
import io.github.astro.mantis.common.constant.Mode;
import io.github.astro.mantis.common.constant.ModeContainer;
import io.github.astro.mantis.common.exception.CodecException;
import io.github.astro.mantis.configuration.URL;

/**
 * @Author WenBo Zhou
 * @Date 2023/12/3 14:17
 */
public abstract class AbstractCodec implements Codec {

    protected Class<?> encodedClass;

    protected Class<?> decodedClass;

    public AbstractCodec(Class<?> encodeClass, Class<?> decodeClass) {
        this.encodedClass = encodeClass;
        this.decodedClass = decodeClass;
    }

    @Override
    public byte[] encode(Object message) throws CodecException {
        if (message instanceof Request request) {
            return encodeRequest(request);
        } else {
            Response response = (Response) message;
            return encodeResponse(response);
        }
    }

    @Override
    public Object decode(byte[] bytes) throws CodecException {
        ByteReader byteReader = ByteReader.newReader(bytes);
        byte envelopeType = byteReader.readByte();
        Mode mode = ModeContainer.getMode(Key.ENVELOPE, envelopeType);
        if(mode.name().equals(KeyValues.Envelope.REQUEST)){
            return decodeRequest(byteReader.readBytes(byteReader.readableBytes()));
        }else {
            return decodeResponse(byteReader.readBytes(byteReader.readableBytes()));
        }
    }

    private byte[] encodeRequest(Request request) {
        ByteWriter byteWriter = ByteWriter.newWriter();
        Mode requestMoe = ModeContainer.getMode(Key.ENVELOPE, KeyValues.Envelope.REQUEST);
        byteWriter.writeByte(requestMoe.type());
        byteWriter.writeLong(request.getId());
        byteWriter.writeBoolean(request.isOneway());
        String url = request.getUrl().toString();
        byteWriter.writeInt(url.length());
        byteWriter.writeCharSequence(url);
        byte[] message = encodeRequestMessage(request);
        byteWriter.writeBytes(message);
        return byteWriter.toBytes();
    }

    private Request decodeRequest(byte[] bytes) {
        ByteReader byteReader = ByteReader.newReader(bytes);
        long id = byteReader.readLong();
        boolean oneway = byteReader.readBoolean();
        int urlLength = byteReader.readInt();
        CharSequence urlStr = byteReader.readCharSequence(urlLength);
        URL url = URL.valueOf(String.valueOf(urlStr));
        Request request = new Request();
        request.setId(id);
        request.setOneway(oneway);
        request.setUrl(url);
        Object message = decodeRequestMessage(request, byteReader.readBytes(byteReader.readableBytes()));
        request.setMessage(message);
        return request;
    }

    private byte[] encodeResponse(Response response) {
        ByteWriter byteWriter = ByteWriter.newWriter();
        Mode responseMode = ModeContainer.getMode(Key.ENVELOPE, KeyValues.Envelope.RESPONSE);
        byteWriter.writeByte(responseMode.type());
        byteWriter.writeLong(response.getId());
        byteWriter.writeByte(response.getCode());
        String url = response.getUrl().toString();
        byteWriter.writeInt(url.length());
        byteWriter.writeCharSequence(url);
        byte[] message = encodeResponseMessage(response);
        byteWriter.writeBytes(message);
        return byteWriter.toBytes();
    }

    private Response decodeResponse(byte[] bytes) {
        ByteReader byteReader = ByteReader.newReader(bytes);
        long id = byteReader.readLong();
        byte code = byteReader.readByte();
        int urlLength = byteReader.readInt();
        CharSequence urlStr = byteReader.readCharSequence(urlLength);
        URL url = URL.valueOf(String.valueOf(urlStr));
        Response response = new Response();
        response.setId(id);
        response.setCode(code);
        response.setUrl(url);
        Object message = decodeResponseMessage(response, byteReader.readBytes(byteReader.readableBytes()));
        response.setMessage(message);
        return response;
    }


    protected abstract byte[] encodeRequestMessage(Request request);

    protected abstract Object decodeRequestMessage(Request request, byte[] messageBytes);

    protected abstract byte[] encodeResponseMessage(Response response);

    protected abstract Object decodeResponseMessage(Response response, byte[] messageBytes);

}
