package common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jcajce.provider.digest.SHA3.DigestSHA3;

public class HashingUtils {

	private static MessageDigest md_md5;
	private static DigestSHA3 md_sha3;

	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static String md5(String clearText) {
		try {
			return new String(Hex.encodeHex(md_md5.digest(clearText.getBytes("UTF-8"))));
		} catch (UnsupportedEncodingException e) {
			LOGGER.log(Level.SEVERE, "Can't md5 hash text: "+clearText, e);
			return null;
		} catch (NullPointerException nullPointExc) {
			if (md_md5 == null) {
				try {
					md_md5 = MessageDigest.getInstance("MD5");
					return new String(Hex.encodeHex(md_md5.digest(clearText.getBytes("UTF-8"))));
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
					LOGGER.log(Level.SEVERE, "Can't get instance of Message digest", nullPointExc);
				} catch (UnsupportedEncodingException encodeExc) {
					LOGGER.log(Level.SEVERE, "", encodeExc);
				}
			}
			return null;
		}
	}

	public static String keccak(String clearText, int attemptCounter) {
		try {
			md_sha3.update(clearText.getBytes("UTF-8"));
			return org.bouncycastle.util.encoders.Hex.toHexString(md_sha3.digest());
		} catch (UnsupportedEncodingException e) {
			LOGGER.log(Level.SEVERE, "", e);
		} catch (NullPointerException nullPointerExc) {
			if (attemptCounter > 5) {

				LOGGER.severe("hashing failed after "+attemptCounter+" attempts");
			} else {

				md_sha3 = new DigestSHA3(256);
				return keccak(clearText, attemptCounter++);
			}
		} catch (Exception e) {

			if (attemptCounter > 5) {

				LOGGER.log(Level.SEVERE, "hashing failed after "+attemptCounter+" attempts", e);
			} else {
				md_sha3 = new DigestSHA3(256);
				return keccak(clearText, attemptCounter++);
			}
		}
		LOGGER.severe("data ignored, unable to hash following text: "+clearText);
		return null;
	}

}