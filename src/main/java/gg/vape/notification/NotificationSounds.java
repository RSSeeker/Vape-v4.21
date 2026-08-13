package gg.vape.notification;

public class NotificationSounds {
    public static final SoundClip PARTY_INVITE;
    public static final SoundClip MESSAGE_RECEIVED;
    public static final SoundClip PING;

    private static String decodeUtf8(byte[] bytes) {
        int outputIndex = 0;
        int length = bytes.length;
        char[] characters = new char[length];
        for (int inputIndex = 0; inputIndex < length; ++inputIndex) {
            char decoded;
            int value = 0xFF & bytes[inputIndex];
            if (value < 192) {
                characters[outputIndex++] = (char)value;
                continue;
            }
            if (value < 224) {
                decoded = (char)((char)(value & 0x1F) << 6);
                value = bytes[++inputIndex];
                decoded = (char)(decoded | (char)(value & 0x3F));
                characters[outputIndex++] = decoded;
                continue;
            }
            if (inputIndex >= length - 2) continue;
            decoded = (char)((char)(value & 0xF) << 12);
            value = bytes[++inputIndex];
            decoded = (char)(decoded | (char)(value & 0x3F) << 6);
            value = bytes[++inputIndex];
            decoded = (char)(decoded | (char)(value & 0x3F));
            characters[outputIndex++] = decoded;
        }
        return new String(characters, 0, outputIndex);
    }

    static {
        try {
            String[] resourceNames = new String[]{"party_invite", "ping", "message_rec"};
            PING = new SoundClip(resourceNames[1]);
            PARTY_INVITE = new SoundClip(resourceNames[0]);
            MESSAGE_RECEIVED = new SoundClip(resourceNames[2]);
        }
        catch (Exception exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }
}
