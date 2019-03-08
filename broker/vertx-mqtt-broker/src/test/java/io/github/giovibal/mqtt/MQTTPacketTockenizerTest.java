package io.github.giovibal.mqtt;

import com.dqv.mqtt.MQTTPacketTokenizer;

/**
 * Created by giovanni on 11/11/16.
 */
public class MQTTPacketTockenizerTest {
    public static void main(String[] args) {
        MQTTPacketTokenizer tokenizer = new MQTTPacketTokenizer();
        tokenizer.registerListener(new MQTTPacketTokenizer.MqttTokenizerListener() {
            @Override
            public void onToken(byte[] token, boolean timeout) {
                System.out.println("Token = " + ConversionUtility.toHexString(token, ":"));
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        });

        // byte[] data = new byte[]{0x00, 0x01};
        // byte[] data = new byte[]{0x00, 0x7f};
        // byte[] data = new byte[]{0x00, (byte)0x80, (byte)0x01};
        // byte[] data = new byte[]{0x00, (byte)0xFF, (byte)0x7f};
        // byte[] data = new byte[]{0x00, (byte)0x80, (byte)0x80, (byte)0x01};
        // byte[] data = new byte[]{0x00, (byte)0xFF, (byte)0xFF, (byte)0x7f};
        // byte[] data = new byte[]{0x00, (byte)0x80, (byte)0x80, (byte)0x80, (byte)0x01};
        // byte[] data = new byte[]{ 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x7f };

        // byte[] data = new byte[] { 0x00, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x01 };
        // byte[] data = new byte[] { 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x7f };

        {
            // byte[] data = new byte[]{0x00, 0x01, 0x30};

            byte[] data = new byte[]{0x00, 0x02, 0x30, 0x38};

            System.out.println("Processing " + ConversionUtility.toHexString(data, ":") + "...");
            MQTTPacketTokenizer.MqttTokenizerState state = tokenizer.process(data);
            System.out.println("...State = " + state);
            tokenizer.debugState();
            System.out.println();
        }
        {
            {
                byte[] data = new byte[]{0x00};
                System.out.println("Processing " + ConversionUtility.toHexString(data, ":") + "...");
                MQTTPacketTokenizer.MqttTokenizerState state = tokenizer.process(data);
                System.out.println("...State = " + state);
                tokenizer.debugState();
                System.out.println();
            }
            {
                byte[] data = new byte[]{0x05};
                System.out.println("Processing " + ConversionUtility.toHexString(data, ":") + "...");
                MQTTPacketTokenizer.MqttTokenizerState state = tokenizer.process(data);
                System.out.println("...State = " + state);
                tokenizer.debugState();
                System.out.println();
            }
            {
                byte[] data = new byte[]{0x30, 0x37, 0x38};
                System.out.println("Processing " + ConversionUtility.toHexString(data, ":") + "...");
                MQTTPacketTokenizer.MqttTokenizerState state = tokenizer.process(data);
                System.out.println("...State = " + state);
                tokenizer.debugState();
                System.out.println();
            }
            {
                byte[] data = new byte[]{0x34, 0x36, 0x00, 0x03};
                System.out.println("Processing " + ConversionUtility.toHexString(data, ":") + "...");
                MQTTPacketTokenizer.MqttTokenizerState state = tokenizer.process(data);
                System.out.println("...State = " + state);
                tokenizer.debugState();
                System.out.println();
            }
            {
                byte[] data = new byte[]{0x30, 0x37, 0x38};
                System.out.println("Processing " + ConversionUtility.toHexString(data, ":") + "...");
                MQTTPacketTokenizer.MqttTokenizerState state = tokenizer.process(data);
                System.out.println("...State = " + state);
                tokenizer.debugState();
                System.out.println();
            }

            {

                byte[] data = new byte[]{ 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x7f };

                System.out.println("Processing " + ConversionUtility.toHexString(data, ":") + "...");
                MQTTPacketTokenizer.MqttTokenizerState state = tokenizer.process(data);
                System.out.println("...State = " + state);
                tokenizer.debugState();
                System.out.println();
            }
        }
    }


    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String toHexString(byte[] bytes, int length, int offset, String separator) {
        char[] hexChars = new char[bytes.length * (2 + separator.length()) - separator.length()];
        char[] separatorChars = separator.toCharArray();
        int v;
        int step = 2 + separatorChars.length;

        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;

            hexChars[j * step] = hexArray[v >>> 4];
            hexChars[j * step + 1] = hexArray[v & 0x0F];

            if (bytes.length - j > 1) {
                for (int s = 0; s < separatorChars.length; s++) {
                    hexChars[j * step + 2 + s] = separatorChars[s];
                }
            }
        }

        return new String(hexChars);
    }

    public static String toHexString(byte[] aByteArray, String hexSeparator) {
        return toHexString(aByteArray, 0, aByteArray.length, hexSeparator);
    }
}
